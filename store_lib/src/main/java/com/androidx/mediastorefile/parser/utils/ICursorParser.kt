package com.androidx.mediastorefile.parser.utils

import android.database.Cursor
import android.os.Build
import android.provider.MediaStore

/**
 * 1. 在某个api level下解析列对应的索引，并将索引保存在map中
 * 2. 数据表中每行数据，按照列名设置给传入的数据类实例字段
 */
abstract class ICursorParser<T>(
    val apiLevel: Int = Build.VERSION_CODES.BASE,
) {
    protected val map: ColumnNameIndexMutableMap = mutableMapOf()

    fun initColumnIndexMap(cursor: Cursor) {
        map.clear()
        map.putAll(parseColumnIndex(cursor))
    }

    /**
     * 解析列与索引的映射关系
     */
    internal abstract fun parseColumnIndex(cursor: Cursor): ColumnNameIndexMap

    /**
     * 针对数据表每行数据，将列对应的数据设置到实体类字段
     */
    abstract fun setFieldValue(cursor: Cursor, data: T)

    //<editor-fold desc="Cursor扩展">
    protected fun Cursor.getStringOrNull(columnName: String): String? {
        val columnIndex = map.valueOf(columnName)
        if (columnIndex == -1) return null
        return getStringOrNull(columnIndex)
    }

    protected fun Cursor.getLongOrNull(columnName: String): Long? {
        val columnIndex = map.valueOf(columnName)
        if (columnIndex == -1) return null
        return getLongOrNull(columnIndex)
    }

    protected fun Cursor.getIntOrNull(columnName: String): Int? {
        val columnIndex = map.valueOf(columnName)
        if (columnIndex == -1) return null
        return getIntOrNull(columnIndex)
    }

    protected fun Cursor.getShortOrNull(columnName: String): Short? {
        val columnIndex = map.valueOf(columnName)
        if (columnIndex == -1) return null
        return getShortOrNull(columnIndex)
    }

    protected fun Cursor.getFloatOrNull(columnName: String): Float? {
        val columnIndex = map.valueOf(columnName)
        if (columnIndex == -1) return null
        return getFloatOrNull(columnIndex)
    }

    protected fun Cursor.getDoubleOrNull(columnName: String): Double? {
        val columnIndex = map.valueOf(columnName)
        if (columnIndex == -1) return null
        return getDoubleOrNull(columnIndex)
    }

    protected fun Cursor.getBooleanOrNull(columnName: String): Boolean? {
        val columnIndex = map.valueOf(columnName)
        if (columnIndex == -1) return null
        return getBooleanOrNull(columnIndex)
    }

    protected fun Cursor.getBlobOrNull(columnName: String): ByteArray? {
        val columnIndex = map.valueOf(columnName)
        if (columnIndex == -1) return null
        return getBlobOrNull(columnIndex)
    }

    //</editor-fold>
    //<editor-fold desc="带默认值的扩展">
    protected fun Cursor.getStringOr(columnName: String, defaultValue: String): String {
        val columnIndex = map.valueOf(columnName)
        if (columnIndex == -1) return defaultValue
        return getStringOrNull(columnIndex) ?: defaultValue
    }

    protected fun Cursor.getLongOr(columnName: String, defaultValue: Long=0L): Long {
        val columnIndex = map.valueOf(columnName)
        if (columnIndex == -1) return defaultValue
        return getLongOrNull(columnIndex) ?: defaultValue
    }

    protected fun Cursor.getIntOr(columnName: String, defaultValue: Int=0): Int {
        val columnIndex = map.valueOf(columnName)
        if (columnIndex == -1) return defaultValue
        return getIntOrNull(columnIndex) ?: defaultValue
    }

    protected fun Cursor.getShortOr(columnName: String, defaultValue: Short): Short {
        val columnIndex = map.valueOf(columnName)
        if (columnIndex == -1) return defaultValue
        return getShortOrNull(columnIndex) ?: defaultValue
    }

    protected fun Cursor.getFloatOr(columnName: String, defaultValue: Float= 0.0f): Float {
        val columnIndex = map.valueOf(columnName)
        if (columnIndex == -1) return defaultValue
        return getFloatOrNull(columnIndex) ?: defaultValue
    }

    protected fun Cursor.getDoubleOr(columnName: String, defaultValue: Double): Double {
        val columnIndex = map.valueOf(columnName)
        if (columnIndex == -1) return defaultValue
        return getDoubleOrNull(columnIndex) ?: defaultValue
    }

    protected fun Cursor.getBooleanOr(columnName: String, defaultValue: Boolean): Boolean {
        val columnIndex = map.valueOf(columnName)
        if (columnIndex == -1) return defaultValue
        return getBooleanOrNull(columnIndex) ?: defaultValue
    }

    protected fun Cursor.getBlobOr(columnName: String, defaultValue: ByteArray): ByteArray {
        val columnIndex = map.valueOf(columnName)
        if (columnIndex == -1) return defaultValue
        return getBlobOrNull(columnIndex) ?: defaultValue
    }
    //</editor-fold>
}


/**
 * 列名索引映射关系 Map<列名，索引>
 */
typealias ColumnNameIndexMap = Map<String, Int>
typealias ColumnNameIndexMutableMap = MutableMap<String, Int>

/**
 * 根据列名解析索引，返回pair<列名，索引>
 *
 * @param name columnName 例如 [MediaStore.DownloadColumns.DOWNLOAD_URI]
 * @return column name and column index
 */
fun Cursor.indexOf(name: String): Pair<String, Int>? {
    val index = getColumnIndex(name)
    if (index == -1) return null
    return name to index
}

/**
 * 根据传入的列名获取列索引，如果列不存在则抛出异常
 *
 * @param name columnName
 */
fun ColumnNameIndexMap.valueOf(name: String): Int {
    return this[name] ?: -1
}

public fun <K, V> mapOfNullable(vararg pairs: kotlin.Pair<K, V>?): kotlin.collections.Map<K, V> {
    val map = mutableMapOf<K, V>()
    pairs.forEach { item ->
        if (item != null) {
            map[item.first] = item.second
        }
    }
    return map
}

//<editor-fold desc="Cursor扩展">

fun Cursor.getStringOrNull(columnIndex: Int): String? {
    return if (isNull(columnIndex)) null else getString(columnIndex)
}

fun Cursor.getLongOrNull(columnIndex: Int): Long? {
    return if (isNull(columnIndex)) null else getLong(columnIndex)
}

fun Cursor.getIntOrNull(columnIndex: Int): Int? {
    return if (isNull(columnIndex)) null else getInt(columnIndex)
}

fun Cursor.getShortOrNull(columnIndex: Int): Short? {
    return if (isNull(columnIndex)) null else getShort(columnIndex)
}

fun Cursor.getFloatOrNull(columnIndex: Int): Float? {
    return if (isNull(columnIndex)) null else getFloat(columnIndex)
}

fun Cursor.getDoubleOrNull(columnIndex: Int): Double? {
    return if (isNull(columnIndex)) null else getDouble(columnIndex)
}

fun Cursor.getBooleanOrNull(columnIndex: Int): Boolean? {
    return if (isNull(columnIndex)) null else getInt(columnIndex) != 0
}

fun Cursor.getBlobOrNull(columnIndex: Int): ByteArray? {
    return if (isNull(columnIndex)) null else getBlob(columnIndex)
}


//</editor-fold>
