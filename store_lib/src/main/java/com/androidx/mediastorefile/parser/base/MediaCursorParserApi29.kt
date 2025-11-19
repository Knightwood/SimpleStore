package com.androidx.mediastorefile.parser.base

import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import com.androidx.mediastorefile.item.MediaFile
import com.androidx.mediastorefile.parser.utils.ColumnNameIndexMap
import com.androidx.mediastorefile.parser.utils.ICursorParser
import com.androidx.mediastorefile.parser.utils.indexOf
import com.androidx.mediastorefile.parser.utils.mapOfNullable

class MediaCursorParserApi29 : ICursorParser<MediaFile>(
    apiLevel = Build.VERSION_CODES.Q
) {
    override fun parseColumnIndex(cursor: Cursor): ColumnNameIndexMap {
        val bucket_display_name_column = cursor.indexOf(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME)
        val bucket_id_column = cursor.indexOf(MediaStore.MediaColumns.BUCKET_ID)
        val date_expires_column = cursor.indexOf(MediaStore.MediaColumns.DATE_EXPIRES)
        val datetaken_column = cursor.indexOf(MediaStore.MediaColumns.DATE_TAKEN)
        val document_id_column = cursor.indexOf(MediaStore.MediaColumns.DOCUMENT_ID)
        val duration_column = cursor.indexOf(MediaStore.MediaColumns.DURATION)
        val instance_id_column = cursor.indexOf(MediaStore.MediaColumns.INSTANCE_ID)
        val is_pending_column = cursor.indexOf(MediaStore.MediaColumns.IS_PENDING)
        val orientation_column = cursor.indexOf(MediaStore.MediaColumns.ORIENTATION)
        val original_document_id_column = cursor.indexOf(MediaStore.MediaColumns.ORIGINAL_DOCUMENT_ID)
        val owner_package_name_column = cursor.indexOf(MediaStore.MediaColumns.OWNER_PACKAGE_NAME)
        val relative_path_column = cursor.indexOf(MediaStore.MediaColumns.RELATIVE_PATH)
        val volume_name_column = cursor.indexOf(MediaStore.MediaColumns.VOLUME_NAME)
        return mapOfNullable(
            bucket_id_column,
            bucket_display_name_column,
            date_expires_column,
            datetaken_column,
            document_id_column,
            duration_column,
            instance_id_column,
            is_pending_column,
            orientation_column,
            original_document_id_column,
            owner_package_name_column,
            relative_path_column,
            volume_name_column
        )
    }

    override fun setFieldValue(cursor: Cursor, data: MediaFile) {
        val datetaken = cursor.getLongOr(MediaStore.MediaColumns.DATE_TAKEN)
        val is_pending = cursor.getIntOr(MediaStore.MediaColumns.IS_PENDING)
        val date_expires = cursor.getLongOr(MediaStore.MediaColumns.DATE_EXPIRES)
        val owner_package_name = cursor.getStringOrNull(MediaStore.MediaColumns.OWNER_PACKAGE_NAME)
        val relative_path = cursor.getStringOrNull(MediaStore.MediaColumns.RELATIVE_PATH)
        val bucket_id = cursor.getIntOr(MediaStore.MediaColumns.BUCKET_ID)
        val bucket_display_name = cursor.getStringOrNull(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME)
        val document_id = cursor.getStringOrNull(MediaStore.MediaColumns.DOCUMENT_ID)
        val instance_id = cursor.getStringOrNull(MediaStore.MediaColumns.INSTANCE_ID)
        val original_document_id = cursor.getStringOrNull(MediaStore.MediaColumns.ORIGINAL_DOCUMENT_ID)
        val orientation = cursor.getIntOr(MediaStore.MediaColumns.ORIENTATION)
        val volume_name = cursor.getStringOrNull(MediaStore.MediaColumns.VOLUME_NAME)
        val duration = cursor.getLongOr(MediaStore.MediaColumns.DURATION)

        data.apply {
            this.datetaken = datetaken
            this.is_pending = is_pending
            this.date_expires = date_expires
            this.owner_package_name = owner_package_name
            this.relative_path = relative_path
            this.bucket_id = bucket_id
            this.bucket_display_name = bucket_display_name
            this.document_id = document_id
            this.instance_id = instance_id
            this.original_document_id = original_document_id
            this.orientation = orientation
            this.volume_name = volume_name
            this.duration = duration
        }
    }
}
