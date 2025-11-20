package com.androidx.mediastorefile

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.androidx.mediastorefile.item.DownloadMediaFile
import com.androidx.mediastorefile.item.MediaFile
import com.androidx.mediastorefile.parser.base.newBasicMediaStoreMapper
import com.androidx.mediastorefile.parser.download.newDownloadMediaCursorMapper


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
            MediaStore.Downloads.RELATIVE_PATH,
        )

        val sortOrder = null// "${MediaStore.Downloads.DATE_ADDED} DESC"
        val selection = null// "${MediaStore.Downloads.DISPLAY_NAME} LIKE ?"
        val selectionArgs = null// arrayOf("%.apk")

        context.contentResolver.query(
//            MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL),
            MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL),
//            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
            projection,
            selection,//查询条件
            selectionArgs,//查询参数
            sortOrder
        )?.use { cursor ->
//            files = newDownloadMediaCursorMapper().parse(cursor)
            files = cursor.parse()
        }
//        Log.d(TAG,"查询完成")
        return files
    }

//    fun scan(context: Context) {
//        context.run {
//            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
//            val contentUri = Uri.fromFile(yourNewFile)
//            mediaScanIntent.setData(contentUri)
//            sendBroadcast(mediaScanIntent)
//        }
//    }
}

private const val TAG = "MediaStoreQuery"

inline fun <reified T> Cursor.parse(): List<T> {
    return when (T::class) {
        DownloadMediaFile::class -> {
            newDownloadMediaCursorMapper().parse(this)
        }

        MediaFile::class -> {
            newBasicMediaStoreMapper().parse(this)
        }

        else -> {
            emptyList()
        }
    } as List<T>
}
