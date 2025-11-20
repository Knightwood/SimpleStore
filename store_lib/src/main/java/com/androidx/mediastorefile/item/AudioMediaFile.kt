package com.androidx.mediastorefile.item


/**
 * 音频媒体文件数据类，基于 [android.provider.MediaStore.Audio.AudioColumns] 接口定义（仅包含 AudioColumns 特有字段）
 */
data class AudioMediaFile(
    /**
     * 从 TITLE 计算出的非人类可读密钥，用于搜索、排序和分组
     *
     * @see android.provider.MediaStore.Audio#keyFor(String)
     * @see android.provider.MediaStore.Audio.AudioColumns.TITLE_KEY
     * @deprecated 这些密钥是使用 [java.util.Locale.ROOT] 生成的，这意味着它们不反映特定语言环境的排序偏好。
     *   要应用特定语言环境的排序偏好，请使用 [android.content.ContentResolver.QUERY_ARG_SQL_SORT_ORDER] 配合
     * {@code COLLATE LOCALIZED}，或使用 [android.content.ContentResolver.QUERY_ARG_SORT_LOCALE]。
     * @see android.provider.MediaStore.Audio.AudioColumns.TITLE_KEY
     */
    @Deprecated("Locale-specific sorting not supported")
    val titleKey: String? = null,

    /**
     * 应恢复播放的位置（以毫秒为单位）
     *
     * @see android.provider.MediaStore.Audio.AudioColumns.BOOKMARK
     */
    val bookmark: Long? = null,

    /**
     * 创建音频文件的艺术家 ID（如果有）
     *
     * @see android.provider.MediaStore.Audio.AudioColumns.ARTIST_ID
     */
    val artistId: Long? = null,

    /**
     * 专辑的艺术家信息
     * @hide
     * @see android.provider.MediaStore.Audio.AudioColumns.ALBUM_ARTIST
     */
    val albumArtist: String? = null,

    /**
     * 从 ARTIST 计算出的非人类可读密钥，用于搜索、排序和分组
     * @see android.provider.MediaStore.Audio#keyFor(String)
     * @deprecated 这些密钥是使用 [java.util.Locale.ROOT] 生成的，这意味着它们不反映特定语言环境的排序偏好。
     * 要应用特定语言环境的排序偏好，请使用 [android.content.ContentResolver.QUERY_ARG_SQL_SORT_ORDER] 配合
     * {@code COLLATE LOCALIZED}，或使用 [android.content.ContentResolver.QUERY_ARG_SORT_LOCALE]。
     * @see android.provider.MediaStore.Audio.AudioColumns.ARTIST_KEY
     */
    @Deprecated("Locale-specific sorting not supported")
    val artistKey: String? = null,

    /**
     * 音频文件所属专辑的 ID（如果有）
     *
     * @see android.provider.MediaStore.Audio.AudioColumns.ALBUM_ID
     */
    val albumId: Long? = null,


    /**
     * 从 ALBUM 计算出的非人类可读密钥，用于搜索、排序和分组
     *
     * @see android.provider.MediaStore.Audio#keyFor(String)
     * @see android.provider.MediaStore.Audio.AudioColumns.ALBUM_KEY
     * @deprecated 这些密钥是使用 [java.util.Locale.ROOT] 生成的，这意味着它们不反映特定语言环境的排序偏好。
     *    要应用特定语言环境的排序偏好，请使用 [ContentResolver.QUERY_ARG_SQL_SORT_ORDER] 配合
     *    {@code COLLATE LOCALIZED}，或使用
     *    [ContentResolver.QUERY_ARG_SORT_LOCALE]。
     */
    @Deprecated("Locale-specific sorting not supported")
    val album_key: String? = null,

    /**
     * 此歌曲在专辑中的曲目号（如果有）。 该数字编码了曲目号和光盘号。对于多光盘套装，第一张光盘上的曲目将是 1xxx， 第二张光盘上的曲目将是
     * 2xxx，以此类推。
     *
     * @see android.provider.MediaStore.Audio.AudioColumns.TRACK
     */
    val track: Int? = null,


    /**
     * 如果音频文件是音乐则为非零值
     *
     * @see android.provider.MediaStore.Audio.AudioColumns.IS_MUSIC
     */
    val isMusic: Boolean? = null,

    /**
     * 如果音频文件是播客则为非零值
     *
     * @see android.provider.MediaStore.Audio.AudioColumns.IS_PODCAST
     */
    val isPodcast: Boolean? = null,

    /**
     * 如果音频文件可能是铃声则为非零值
     *
     * @see android.provider.MediaStore.Audio.AudioColumns.IS_RINGTONE
     */
    val isRingtone: Boolean? = null,

    /**
     * 如果音频文件可能是闹钟则为非零值
     *
     * @see android.provider.MediaStore.Audio.AudioColumns.IS_ALARM
     */
    val isAlarm: Boolean? = null,

    /**
     * 如果音频文件可能是通知声音则为非零值
     *
     * @see android.provider.MediaStore.Audio.AudioColumns.IS_NOTIFICATION
     */
    val isNotification: Boolean? = null,

    /**
     * 如果音频文件是音频书则为非零值
     *
     * @see android.provider.MediaStore.Audio.AudioColumns.IS_AUDIOBOOK
     */
    val isAudiobook: Boolean? = null,

    /**
     * 如果音频文件是录音应用录制的语音录音则为非零值
     *
     * @see android.provider.MediaStore.Audio.AudioColumns.IS_RECORDING
     */
    val isRecording: Boolean? = null,

    /**
     * 音频文件所属流派的 ID（如果有）
     *
     * @see android.provider.MediaStore.Audio.AudioColumns.GENRE_ID
     */
    val genreId: Long? = null,

    /**
     * 从 GENRE 计算出的非人类可读密钥，用于搜索、排序和分组
     *
     * @see android.provider.MediaStore.Audio#keyFor(String)
     * @see android.provider.MediaStore.Audio.AudioColumns.GENRE_KEY
     * @deprecated 这些密钥是使用 [java.util.Locale.ROOT] 生成的，这意味着它们不反映特定语言环境的排序偏好。
     *    要应用特定语言环境的排序偏好，请使用 [ContentResolver.QUERY_ARG_SQL_SORT_ORDER] 配合
     *    {@code COLLATE LOCALIZED}，或使用
     *    [ContentResolver.QUERY_ARG_SORT_LOCALE]。
     */
    @Deprecated("Locale-specific sorting not supported")
    val genreKey: String? = null,

    /**
     * 本地化标题的资源 URI（如果有）
     * 符合以下模式：
     * - Scheme: [ContentResolver.SCHEME_ANDROID_RESOURCE]
     * - Authority: 铃声标题提供程序的包名
     * - First Path Segment: 资源类型（必须是 "string"）
     * - Second Path Segment: 标题的资源 ID
     *
     * @see android.provider.MediaStore.Audio.AudioColumns.TITLE_RESOURCE_URI
     */
    val titleResourceUri: String? = null,

    /**
     * 每个音频采样的位数（如果可用）
     *
     * @see android.provider.MediaStore.Audio.AudioColumns.BITS_PER_SAMPLE
     */
    val bitsPerSample: Int? = null,

    /**
     * 采样率（Hz，如果可用）
     *
     * @see android.provider.MediaStore.Audio.AudioColumns.SAMPLERATE
     */
    val samplerate: Int? = null,

    private val property: IMediaFile,
) : IMediaFile by property

