package com.kiylx.store_lib.mediastore

import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.kiylx.store_lib.kit.noNullUriResult

class MediaStoreFragment : Fragment(), MediaStoreMethod {
    /**
     * @param relativePath 相对图片文件夹的相对路径
     * 例如 传入test，会存入 storage/emulated/0/Pictures/test/ 文件夹
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun newPhoto(
        name: String,
        mime: String,
        relativePath: String,
        block: noNullUriResult,
    ) {
        if ((relativePath.isNotEmpty())) {
            genPic(name, "${Environment.DIRECTORY_DCIM}/$relativePath", mime, block)
        } else {
            genPic(name, Environment.DIRECTORY_DCIM, mime, block)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun newPicture(
        name: String,
        relativePath: String,
        mime: String,
        block: noNullUriResult,
    ) {
        if ((relativePath.isNotEmpty())) {
            genPic(name, "${Environment.DIRECTORY_PICTURES}/$relativePath", mime, block)
        } else {
            genPic(name, Environment.DIRECTORY_PICTURES, mime, block)
        }
    }

    /**
     * @param relativePath 相对图片文件夹的相对路径
     * 例如 传入test，会产生 storage/emulated/0/Pictures/test/
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun genPic(
        name: String,
        path: String,
        mime: String,
        block: noNullUriResult,
    ) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.RELATIVE_PATH, path)
            put(MediaStore.Images.ImageColumns.DISPLAY_NAME, name)
            put(MediaStore.Images.ImageColumns.MIME_TYPE, mime)
        }
        val contentResolver = requireActivity().contentResolver
        // 通过 ContentResolver 在指定的公共目录下按照指定的 ContentValues 创建文件，会返回文件的 content uri（类似这样的地址 content://media/external/images/media/102）
        val uri: Uri? =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            block(uri)
        }else{
            throw Exception("底层内容提供程序返回null或崩溃")
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun newDownloadFile(
        name: String,
        path: String,
        mime: String,
        block: noNullUriResult,
    ) {
        if (path.isNotEmpty()) {
            genDownloadFile(name, Environment.DIRECTORY_DOWNLOADS + path, mime, block)
        } else {
            genDownloadFile(name, Environment.DIRECTORY_DOWNLOADS, mime, block)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun genDownloadFile(
        name: String,
        path: String,
        mime: String,
        block: noNullUriResult,
    ) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.RELATIVE_PATH, path)
            put(MediaStore.Downloads.DISPLAY_NAME, name)
            put(MediaStore.Downloads.MIME_TYPE, mime)
        }
        val contentResolver = requireActivity().contentResolver
        // 通过 ContentResolver 在指定的公共目录下按照指定的 ContentValues 创建文件，会返回文件的 content uri（类似这样的地址 content://media/external/images/media/102）
        val uri: Uri? =
            contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            block(uri)
        }else{
            throw Exception("底层内容提供程序返回null或崩溃")
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun newMovieFile(
        name: String,
        path: String,
        mime: String,
        block: noNullUriResult,
    ) {
        if (path.isNotEmpty()) {
            genMovieFile(name, Environment.DIRECTORY_MOVIES + path, mime, block)
        } else {
            genMovieFile(name, Environment.DIRECTORY_MOVIES, mime, block)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun genMovieFile(
        name: String,
        path: String,
        mime: String,
        block: noNullUriResult,
    ) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Video.Media.RELATIVE_PATH, path)
            put(MediaStore.Video.Media.DISPLAY_NAME, name)
            put(MediaStore.Video.Media.MIME_TYPE, mime)
        }
        val contentResolver = requireActivity().contentResolver
        // 通过 ContentResolver 在指定的公共目录下按照指定的 ContentValues 创建文件，会返回文件的 content uri（类似这样的地址 content://media/external/images/media/102）
        val uri: Uri? =
            contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            block(uri)
        }else{
            throw Exception("底层内容提供程序返回null或崩溃")
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun newMusicFile(
        name: String,
        path: String,
        mime: String,
        block: noNullUriResult,
    ) {
        if (path.isNotEmpty()) {
            genMusicFile(name, Environment.DIRECTORY_MUSIC + path, mime, block)
        } else {
            genMusicFile(name, Environment.DIRECTORY_MUSIC, mime, block)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun genMusicFile(
        name: String,
        path: String,
        mime: String,
        block: noNullUriResult,
    ) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Audio.Media.RELATIVE_PATH, path)
            put(MediaStore.Audio.Media.DISPLAY_NAME, name)
            put(MediaStore.Audio.Media.MIME_TYPE, mime)
        }
        val contentResolver = requireActivity().contentResolver
        // 通过 ContentResolver 在指定的公共目录下按照指定的 ContentValues 创建文件，会返回文件的 content uri（类似这样的地址 content://media/external/images/media/102）
        val uri: Uri? =
            contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            block(uri)
        }else{
            throw Exception("底层内容提供程序返回null或崩溃")
        }
    }


}