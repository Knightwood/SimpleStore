package com.androidx.mediastorefile.item

/**
 * 普通文件类，不区分视频、音频、图像的无差别文件描述
 *
 * [android.provider.MediaStore.Files.FileColumns]
 *
 * @constructor Create empty NoMediaFile
 * @property property
 */
data class IndiscriminateFile(
    /**
     * [android.provider.MediaStore.Files.FileColumns.PARENT]
     */
    val parent: Int,

    /**
     * [android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE]
     */
    val media_type: Int,

    private val property: IMediaFile,
) : IMediaFile by property
