package com.androidx.mediastorefile.parser.base

import android.database.Cursor
import android.provider.MediaStore
import com.androidx.mediastorefile.item.MediaFile
import com.androidx.mediastorefile.parser.utils.ColumnNameIndexMap
import com.androidx.mediastorefile.parser.utils.CursorMapper
import com.androidx.mediastorefile.parser.utils.indexOf
import com.androidx.mediastorefile.parser.utils.ICursorParser
import com.androidx.mediastorefile.parser.utils.mapOfNullable

class MediaCursorParserApi1 : ICursorParser<MediaFile>() {
    override fun parseColumnIndex(cursor: Cursor): ColumnNameIndexMap {
        val _id_column = cursor.indexOf(MediaStore.MediaColumns._ID)
        val _count_column = cursor.indexOf(MediaStore.MediaColumns._COUNT)
        val _data_column = cursor.indexOf(MediaStore.MediaColumns.DATA)
        val _size_column = cursor.indexOf(MediaStore.MediaColumns.SIZE)
        val _display_name_column = cursor.indexOf(MediaStore.MediaColumns.DISPLAY_NAME)
        val date_added_column = cursor.indexOf(MediaStore.MediaColumns.DATE_ADDED)
        val date_modified_column = cursor.indexOf(MediaStore.MediaColumns.DATE_MODIFIED)
        val mime_type_column = cursor.indexOf(MediaStore.MediaColumns.MIME_TYPE)
        val width_column = cursor.indexOf(MediaStore.MediaColumns.WIDTH)
        val height_column = cursor.indexOf(MediaStore.MediaColumns.HEIGHT)
        val title_column = cursor.indexOf(MediaStore.MediaColumns.TITLE)
        return mapOfNullable(
            _id_column,
            _count_column,
            _data_column,
            _size_column,
            _display_name_column,
            date_added_column,
            date_modified_column,
            mime_type_column,
            width_column,
            height_column,
            title_column,
        )
    }

    override fun setFieldValue(cursor: Cursor, data: MediaFile) {
        // 从cursor中读取各个字段的值
        val id = cursor.getLongOr(MediaStore.MediaColumns._ID)
        val count = cursor.getIntOr(MediaStore.MediaColumns._COUNT)
        val _data = cursor.getStringOrNull(MediaStore.MediaColumns.DATA)
        val size = cursor.getLongOr(MediaStore.MediaColumns.SIZE)
        val display_name = cursor.getStringOrNull(MediaStore.MediaColumns.DISPLAY_NAME)
        val date_added = cursor.getLongOr(MediaStore.MediaColumns.DATE_ADDED)
        val date_modified = cursor.getLongOr(MediaStore.MediaColumns.DATE_MODIFIED)
        val mime_type = cursor.getStringOrNull(MediaStore.MediaColumns.MIME_TYPE)
        val width = cursor.getIntOr(MediaStore.MediaColumns.WIDTH)
        val height = cursor.getIntOr(MediaStore.MediaColumns.HEIGHT)
        val title = cursor.getStringOrNull(MediaStore.MediaColumns.TITLE)
        // 创建MediaFakeFile实例
        data.apply {
            this._id = id
            this._count = count
            this._data = _data
            this._size = size
            this._display_name = display_name
            this.date_added = date_added
            this.date_modified = date_modified
            this.mime_type = mime_type
            this.width = width
            this.height = height
            this.title = title
        }
    }

}

val basicMediaStoreMapper
    get() = CursorMapper(::MediaFile) +
            MediaCursorParserApi1() +
            MediaCursorParserApi29() +
            MediaCursorParserApi30()
