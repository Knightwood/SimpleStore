package com.androidx.mediastorefile.parser.utils

import android.database.Cursor
import android.os.Build
import android.util.Log

/**
 * 2025-11-20 昨天花了一天时间写下基础功能，今天上午又进行了优化，于是这些代码越来越复杂。
 * 上午优化完开始测试功能，排查索引映射map空白问题有点找不清调用关系，感觉有点hold不住了。
 * 倒不是我一开始就能写下这么复杂，逻辑上有很绕的代码，当前版本是我写下一个简单版本之后不断迭代的结果。
 * 优化的时候觉得这里可以合并，那里形式上一样可以合并，改来改去就这样了，起始初始版本比现在简单多了。
 * 或许过几天我就会忘记现在关于这套代码的设计思路、使用方式甚至代码细节，开始觉得我是怎么写下这些晦涩或者垃圾的代码来着。
 * 不过不重要，现在能明白，能实现功能就很好了。
 */
interface ICursorMapper0<DATA> {
    /**
     * 解析列与索引关系
     */
    fun initColumnIndexMap(cursor: Cursor)

    /**
     * 对查询出来的数据表中某行映射到数据类字段
     *
     * @param cursor Cursor 已经移动到某一行了，直接取出某些列的数据设置给数据类即可
     */
    fun mapOneRowFrom(cursor: Cursor, data: DATA): DATA

    /**
     * 1. 调用[initColumnIndexMap]生成列名与索引映射
     * 2. 遍历cursor，将查询得到每一行数据映射到实体类字段
     * 3. 返回结果数据集合
     *
     * @param cursor Cursor
     * @param dataCreator 生成用于映射表结构的数据类
     * @return 映射结果数据集合
     */
    fun parse(cursor: Cursor, dataCreator: () -> DATA): MutableList<DATA> {
        val result: MutableList<DATA> = mutableListOf()
        if (cursor.isLast) {
            Log.d(TAG, "parse: 没数据了")
        }
        Log.d(TAG, "parse 数据量: ${cursor.count}")
        initColumnIndexMap(cursor)
        while (cursor.moveToNext()) {
            result.add(mapOneRowFrom(cursor, dataCreator()))
        }
        return result
    }
}

/**
 * 实际上要么使用[AggregationCursorMapper]、要么使用[CursorMapper]，
 * 用户不可能用到[CursorSampleMapper]，因此，写了这么一个接口，统一解析方式。
 */
interface ICursorMapper<DATA> : ICursorMapper0<DATA> {
    /**
     * 对查询出来的数据表中某行映射到数据类字段
     *
     * @param cursor Cursor 已经移动到某一行了，直接取出某些列的数据设置给数据类即可
     */
    fun mapOneRowFrom(cursor: Cursor): DATA

    /**
     * 1. 调用[initColumnIndexMap]生成列名与索引映射
     * 2. 遍历cursor，将查询得到每一行数据映射到实体类字段
     * 3. 返回结果数据集合
     *
     * 实现类需要持有一个“数据类创建工厂”，此工厂实现创建数据类实例。 此方法实现直接调用父级[parse]即可
     *
     * @param cursor Cursor
     * @return 映射结果数据集合
     */
    fun parse(cursor: Cursor): MutableList<DATA>
}

/**
 * 1. 将获取列名与索引的对应关系
 * 2. 将每一行的查询结果映射到实体类字段 以上这些工作都会由[cursorParsers]完成
 */
open class CursorSampleMapper<DATA>() : ICursorMapper0<DATA> {
    internal val cursorParsers = mutableListOf<ICursorParser<DATA>>()

    /**
     * 添加小于当前设备api level的解析器 如果解析器的api level大于当前设备api level，无法正常工作，忽略它
     */
    open operator fun plus(other: ICursorParser<DATA>): CursorSampleMapper<DATA> {
        if (Build.VERSION.SDK_INT >= other.apiLevel) {
            cursorParsers.add(other)
        }
        return this
    }

    /**
     * 解析列与索引关系
     */
    override fun initColumnIndexMap(cursor: Cursor) {
        cursorParsers.forEach {//生成列名与索引的映射关系
            it.initColumnIndexMap(cursor)
        }
    }

    /**
     * 针对数据表每行数据，将列对应的数据设置到实体类字段
     *
     * @param cursor Cursor
     * @param data DATA
     */
    override fun mapOneRowFrom(cursor: Cursor, data: DATA): DATA {
        //遍历所有解析器，填充数据
        cursorParsers.forEach {
            it.setFieldValue(cursor, data)
        }
        return data
    }
}

/**
 * @param dataCreator 用于生成用于映射表结构的数据类，要求字段都是var修饰
 */
class CursorMapper<DATA>(val dataCreator: () -> DATA) :
    ICursorMapper<DATA>,
    CursorSampleMapper<DATA>() {

    /**
     * 添加小于当前设备api level的解析器 如果解析器的api level大于当前设备api level，无法正常工作，忽略它
     */
    override operator fun plus(other: ICursorParser<DATA>): CursorMapper<DATA> {
        super.plus(other)
        return this
    }

    /**
     * 对查询出来的数据表中某行映射到数据类字段
     */
    override fun mapOneRowFrom(cursor: Cursor): DATA {
        return mapOneRowFrom(cursor, dataCreator())
    }

    override fun parse(cursor: Cursor): MutableList<DATA> {
        return parse(cursor, dataCreator)
    }

}

/**
 * 聚合查询，映射多个数据类 考虑如下类：
 *
 * ```
 * class DownloadMediaFile(
 *     var download_uri: String? = null,
 *     var referer_uri: String? = null,
 *     val property: FakeMediaFile
 * ) : FakeMediaFile by property
 *
 * // 若生成实例，需要：
 * val instance = DownloadMediaFile(MediaFile())
 * // 一次查询中，需要将某些列映射到MediaFile数据类，另一些字段映射到DownloadMediaFile数据类。
 * // 表示成代码就是：
 * while (cursor.moveToNext()) {
 *  val base = MediaFile(id = cursor.getLong(_id_column))
 *  val download = DownloadMediaFile(base, cursor.getString(download_uri))
 * }
 * ```
 *
 * 如上示例我们知道，在一次查询中，需要将每行sql数据生成MediaFile类以及DownloadMediaFile类。
 *
 * 借助此类，可以在查询中映射到多个数据类
 *
 * 使用方式：
 *
 * ```
 * /**
 *  * 只映射独属于DownloadMediaFile的字段
 *  * 这个mapper用于将部分字段映射到DownloadMediaFile数据类
 *  */
 * fun newDownloadMediaCursorBaseMapper(): CursorSampleMapper<DownloadMediaFile> =
 *     CursorSampleMapper<DownloadMediaFile>() +
 *             DownloadMediaCursorParserApi1() +
 *             DownloadMediaCursorParserApi29()
 *
 * fun newDownloadMediaCursorMapper(): AggregationCursorMapper<DownloadMediaFile> {
 *     // 基础信息映射器
 *    //这个mapper用于查询列名与索引映射以及将部分字段映射到MediaFile数据类
 *     val baseMapper = newBasicMediaStoreMapper()
 *     return AggregationCursorMapper<DownloadMediaFile>(
 *         mapper = newDownloadMediaCursorBaseMapper(),
 *         dataCreator = { cursor ->//创建最终返回的数据类，查询到的每行数据都会调用一次这个方法
 *             DownloadMediaFile(
 *                 property = baseMapper.mapOneRowFrom(cursor)//映射部分字段到MediaFile数据类, 这里使用的映射器要与下面1处添加的是同一个实例
 *             )
 *         }
 *     ) + baseMapper//1.
 * }
 *
 * ```
 *
 * @param mapper 用于映射最终返回数据类
 * @param dataCreator 利用每行sql数据映射到基础类，并创建最终返回的数据类
 */
class AggregationCursorMapper<DATA>(
    val mapper: ICursorMapper0<DATA>,
    val dataCreator: (Cursor) -> DATA,
) : ICursorMapper<DATA> {
    val mappers = mutableListOf<ICursorMapper0<*>>()

    /**
     * 添加其他映射
     */
    operator fun plus(other: ICursorMapper0<*>): AggregationCursorMapper<DATA> {
        mappers.add(other)
        return this
    }

    override fun initColumnIndexMap(cursor: Cursor) {
//        Log.d(TAG, "开始映射列索引关系")
        mapper.initColumnIndexMap(cursor)
        for (mapper in this.mappers) {
            mapper.initColumnIndexMap(cursor)
        }
    }

    override fun mapOneRowFrom(cursor: Cursor, data: DATA): DATA {
        mapper.mapOneRowFrom(cursor, data)
        return data
    }

    /**
     * 对查询出来的数据表中某行映射到数据类字段
     */
    override fun mapOneRowFrom(cursor: Cursor): DATA {
        return this.mapOneRowFrom(cursor, dataCreator(cursor))
    }

    /**
     * @param cursor Cursor
     * @return 映射结果
     */
    override fun parse(
        cursor: Cursor,
    ): MutableList<DATA> {
        return parse(cursor, { dataCreator(cursor) })
    }
}

private const val TAG = "CursorMapper"
