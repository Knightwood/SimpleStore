package com.androidx.mediastorefile.parser.utils

import android.database.Cursor
import android.os.Build

/**
 * 实际上要么使用[AggregationCursorMapper]、要么使用[CursorMapper]，
 * 用户不可能用到[CursorSampleMapper]，因此，写了这么一个接口，统一解析方式。
 */
interface ICursorMapper<DATA> {
    fun parse(cursor: Cursor): MutableList<DATA>
}

/**
 * 1. 将获取列名与索引的对应关系
 * 2. 将每一行的查询结果映射到实体类字段 以上这些工作都会由[cursorParsers]完成
 */
open class CursorSampleMapper<DATA>() {
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
    internal fun initColumnIndexMap(cursor: Cursor) {
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
    internal fun mapOneRowFrom(cursor: Cursor, data: DATA): DATA {
        //遍历所有解析器，填充数据
        cursorParsers.forEach {
            it.setFieldValue(cursor, data)
        }
        return data
    }

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
        initColumnIndexMap(cursor)
        while (cursor.moveToNext()) {
            result.add(mapOneRowFrom(cursor, dataCreator()))
        }
        return result
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

    internal fun mapOneRowFrom(cursor: Cursor): DATA {
        return mapOneRowFrom(cursor, dataCreator())
    }

    override fun parse(cursor: Cursor): MutableList<DATA> {
        return super.parse(cursor, dataCreator)
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
 * val downloadMediaCursorMapper = AggregationCursorMapper<DownloadMediaFile>(
 *     mapper = CursorSampleMapper<DownloadMediaFile>() +
 *             DownloadMediaCursorParserApi1() +
 *             DownloadMediaCursorParserApi29(), //这个mapper用于将部分字段映射到DownloadMediaFile数据类
 *     dataCreator = { cursor -> //创建最终返回的数据类，查询到的每行数据都会调用一次这个方法
 *         DownloadMediaFile(
 *             property = basicMediaStoreMapper.mapOneRowFrom(cursor)//映射部分字段到MediaFile数据类
 *         )
 *     }
 * ) + basicMediaStoreMapper//这个mapper用于查询列名与索引映射以及将部分字段映射到MediaFile数据类
 *
 * ```
 *
 * @param mapper 用于映射最终返回数据类
 * @param dataCreator 利用每行sql数据映射到基础类，并创建最终返回的数据类
 */
class AggregationCursorMapper<DATA>(
    val mapper: CursorSampleMapper<DATA>,
    val dataCreator: (Cursor) -> DATA,
) : ICursorMapper<DATA> {
    val mappers = mutableListOf<CursorSampleMapper<*>>()

    /**
     * 添加其他映射
     */
    operator fun plus(other: CursorSampleMapper<*>): AggregationCursorMapper<DATA> {
        mappers.add(other)
        return this
    }

    private fun List<CursorSampleMapper<*>>.initColumnIndexMap(cursor: Cursor) {
        for (mapper in this) {
            mapper.initColumnIndexMap(cursor)
        }
    }

    /**
     * @param cursor Cursor
     * @return 映射结果
     */
    override fun parse(
        cursor: Cursor,
    ): MutableList<DATA> {
        val result: MutableList<DATA> = mutableListOf()
        mappers.initColumnIndexMap(cursor)
        while (cursor.moveToNext()) {
            result.add(mapper.mapOneRowFrom(cursor, dataCreator(cursor)))
        }
        return result
    }
}
