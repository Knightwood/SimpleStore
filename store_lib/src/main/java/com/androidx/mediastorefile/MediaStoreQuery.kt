package com.androidx.mediastorefile

import android.content.Context
import android.provider.MediaStore
import com.androidx.mediastorefile.item.DownloadMediaFile
import com.androidx.mediastorefile.parser.download.downloadMediaCursorMapper

object MediaStoreQuery {
    // 查询下载目录下的所有文件
    fun queryAllFilesInDownloads(context: Context): List<DownloadMediaFile> {
        var files: List<DownloadMediaFile> = emptyList()

        val projection = arrayOf(
            MediaStore.Downloads._ID,
            MediaStore.Downloads.DISPLAY_NAME,
            MediaStore.Downloads.SIZE,
            MediaStore.Downloads.DATE_ADDED,
            MediaStore.Downloads.MIME_TYPE,
            MediaStore.Downloads.RELATIVE_PATH
        )

        val sortOrder = "${MediaStore.Downloads.DATE_ADDED} DESC"

        context.contentResolver.query(
            MediaStore.Downloads.EXTERNAL_CONTENT_URI,
            projection,
            null, // 查询条件
            null, // 查询参数
            sortOrder
        )?.use { cursor ->
            files = downloadMediaCursorMapper.parse(cursor)
        }
        return files
    }
}
