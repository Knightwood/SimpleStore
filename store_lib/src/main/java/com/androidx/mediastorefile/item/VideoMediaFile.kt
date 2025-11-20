package com.androidx.mediastorefile.item

/**
 * 视频媒体文件数据类，包含从MediaStore查询到的所有视频相关字段
 */
data class VideoMediaFile(
    /**
     * 视频描述
     *
     * @see android.provider.MediaStore.Video.VideoColumns.DESCRIPTION
     */
    val description: String? = null,

    /**
     * 私有标志
     *
     * @see android.provider.MediaStore.Video.VideoColumns.IS_PRIVATE
     */
    val isprivate: Int = 0,

    /**
     * 标签
     *
     * @see android.provider.MediaStore.Video.VideoColumns.TAGS
     */
    val tags: String? = null,

    /**
     * 分类
     *
     * @see android.provider.MediaStore.Video.VideoColumns.CATEGORY
     */
    val category: String? = null,

    /**
     * 语言
     *
     * @see android.provider.MediaStore.Video.VideoColumns.LANGUAGE
     */
    val language: String? = null,

    /**
     * 纬度（已废弃）
     *
     * location details are no longer indexed for privacy reasons, and this
     * value is now always null. You can still manually obtain location
     * metadata using MediaMetadataRetriever.METADATA_KEY_LOCATION.
     *
     * @see android.provider.MediaStore.Video.VideoColumns.LATITUDE
     */
    @Deprecated("位置详情不再被索引")
    val latitude: Float? = null,

    /**
     * 经度（已废弃）
     *
     * location details are no longer indexed for privacy reasons, and this
     * value is now always null. You can still manually obtain location
     * metadata using MediaMetadataRetriever.METADATA_KEY_LOCATION.
     *
     * @see android.provider.MediaStore.Video.VideoColumns.LONGITUDE
     */
    @Deprecated("位置详情不再被索引")
    val longitude: Float? = null,

    /**
     * 缩略图ID（已废弃）
     *
     * @see android.provider.MediaStore.Video.VideoColumns.MINI_THUMB_MAGIC
     */
    @Deprecated("所有缩略图应通过MediaStore.Images.Thumbnails.getThumbnail获取")
    val mini_thumb_magic: Long = 0,

    /**
     * 书签位置（毫秒）
     *
     * @see android.provider.MediaStore.Video.VideoColumns.BOOKMARK
     */
    val bookmark: Long = 0,

    /**
     * 颜色标准
     *
     * @see android.provider.MediaStore.Video.VideoColumns.COLOR_STANDARD
     */
    val color_standard: Int = 0,

    /**
     * 颜色传输
     *
     * @see android.provider.MediaStore.Video.VideoColumns.COLOR_TRANSFER
     */
    val color_transfer: Int = 0,

    /**
     * 颜色范围
     *
     * @see android.provider.MediaStore.Video.VideoColumns.COLOR_RANGE
     */
    val color_range: Int = 0,
    private val property: IMediaFile
) : IMediaFile by property

