package com.androidx.mediastorefile.parser.download

import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import com.androidx.mediastorefile.item.DownloadMediaFile
import com.androidx.mediastorefile.parser.utils.ColumnNameIndexMap
import com.androidx.mediastorefile.parser.utils.ICursorParser
import com.androidx.mediastorefile.parser.utils.indexOf
import com.androidx.mediastorefile.parser.utils.mapOfNullable

class DownloadMediaCursorParserApi29 : ICursorParser<DownloadMediaFile>(
    Build.VERSION_CODES.Q
) {

    override fun parseColumnIndex(cursor: Cursor): ColumnNameIndexMap {
        return mapOfNullable(
            cursor.indexOf(MediaStore.DownloadColumns.DOWNLOAD_URI),
            cursor.indexOf(MediaStore.DownloadColumns.REFERER_URI),
        )
    }

    override fun setFieldValue(cursor: Cursor, data: DownloadMediaFile) {
        val download_uri = cursor.getStringOrNull(MediaStore.DownloadColumns.DOWNLOAD_URI)
        val referer_uri = cursor.getStringOrNull(MediaStore.DownloadColumns.REFERER_URI)
        data.apply {
            this.download_uri = download_uri
            this.referer_uri = referer_uri
        }
    }
}
