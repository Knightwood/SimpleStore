package com.androidx.mediastorefile.item

interface FakeMediaFile {
    //<editor-fold desc="api 1">

    /**
     * [android.provider.MediaStore.MediaColumns._ID]
     */
    val _id: Long

    /**
     * [android.provider.MediaStore.MediaColumns._COUNT]
     */
    val _count: Int?

    /**
     * [android.provider.MediaStore.MediaColumns.DATA]
     */
    val _data: String?

    /**
     * [android.provider.MediaStore.MediaColumns.SIZE]
     */
    val _size: Long

    /**
     * [android.provider.MediaStore.MediaColumns.DISPLAY_NAME]
     */
    val _display_name: String?

    /**
     * [android.provider.MediaStore.MediaColumns.DATE_ADDED]
     */
    val date_added: Long

    /**
     * [android.provider.MediaStore.MediaColumns.DATE_MODIFIED]
     */
    val date_modified: Long

    /**
     * [android.provider.MediaStore.MediaColumns.MIME_TYPE]
     */
    val mime_type: String?

    /**
     * [android.provider.MediaStore.MediaColumns.WIDTH]
     */
    val width: Int

    /**
     * [android.provider.MediaStore.MediaColumns.HEIGHT]
     */
    val height: Int

    /**
     * [android.provider.MediaStore.MediaColumns.TITLE]
     */
    val title: String?

    //</editor-fold>

    //<editor-fold desc="api 29">
    /**
     * [android.provider.MediaStore.MediaColumns.DATE_TAKEN]
     */
    val datetaken: Long

    /**
     * [android.provider.MediaStore.MediaColumns.IS_PENDING]
     */
    val is_pending: Int

    /**
     * [android.provider.MediaStore.MediaColumns.DATE_EXPIRES]
     */
    val date_expires: Long

    /**
     * [android.provider.MediaStore.MediaColumns.OWNER_PACKAGE_NAME]
     */
    val owner_package_name: String?

    /**
     * [android.provider.MediaStore.MediaColumns.RELATIVE_PATH]
     */
    val relative_path: String?

    /**
     * [android.provider.MediaStore.MediaColumns.BUCKET_ID]
     */
    val bucket_id: Int

    /**
     * [android.provider.MediaStore.MediaColumns.BUCKET_DISPLAY_NAME]
     */
    val bucket_display_name: String?

    /**
     * [android.provider.MediaStore.MediaColumns.DOCUMENT_ID]
     */
    val document_id: String?

    /**
     * [android.provider.MediaStore.MediaColumns.INSTANCE_ID]
     */
    val instance_id: String?

    /**
     * [android.provider.MediaStore.MediaColumns.ORIGINAL_DOCUMENT_ID]
     */
    val original_document_id: String?

    /**
     * [android.provider.MediaStore.MediaColumns.ORIENTATION]
     */
    val orientation: Int

    /**
     * [android.provider.MediaStore.MediaColumns.VOLUME_NAME]
     */
    val volume_name: String?

    /**
     * [android.provider.MediaStore.MediaColumns.DURATION]
     */
    val duration: Long
    //</editor-fold>

    //<editor-fold desc="api 30">

    /**
     * [android.provider.MediaStore.MediaColumns.IS_DRM]
     */
    val is_drm: Int

    /**
     * [android.provider.MediaStore.MediaColumns.IS_TRASHED]
     */
    val is_trashed: Int

    /**
     * [android.provider.MediaStore.MediaColumns.RESOLUTION]
     */
    val resolution: String?

    /**
     * [android.provider.MediaStore.MediaColumns.GROUP_ID]
     */
    @Deprecated("deprecated")
    val group_id: Int

    /**
     * [android.provider.MediaStore.MediaColumns.IS_FAVORITE]
     */
    val is_favorite: Int

    /**
     * [android.provider.MediaStore.MediaColumns.IS_DOWNLOAD]
     */
    val is_download: Int

    /**
     * [android.provider.MediaStore.MediaColumns.GENERATION_ADDED]
     */
    val generation_added: Int

    /**
     * [android.provider.MediaStore.MediaColumns.GENERATION_MODIFIED]
     */
    val generation_modified: Int

    /**
     * [android.provider.MediaStore.MediaColumns.XMP]
     */
    val xmp: ByteArray?

    /**
     * [android.provider.MediaStore.MediaColumns.CD_TRACK_NUMBER]
     */
    val cd_track_number: String?

    /**
     * [android.provider.MediaStore.MediaColumns.ALBUM]
     */
    val album: String?

    /**
     * [android.provider.MediaStore.MediaColumns.ARTIST]
     */
    val artist: String?

    /**
     * [android.provider.MediaStore.MediaColumns.AUTHOR]
     */
    val author: String?

    /**
     * [android.provider.MediaStore.MediaColumns.COMPOSER]
     */
    val composer: String?

    /**
     * [android.provider.MediaStore.MediaColumns.GENRE]
     */
    val genre: String?

    /**
     * [android.provider.MediaStore.MediaColumns.YEAR]
     */
    val year: Int

    /**
     * [android.provider.MediaStore.MediaColumns.NUM_TRACKS]
     */
    val num_tracks: Int

    /**
     * [android.provider.MediaStore.MediaColumns.WRITER]
     */
    val writer: String?

    /**
     * [android.provider.MediaStore.MediaColumns.ALBUM_ARTIST]
     */
    val album_artist: String?

    /**
     * [android.provider.MediaStore.MediaColumns.DISC_NUMBER]
     */
    val disc_number: String?

    /**
     * [android.provider.MediaStore.MediaColumns.COMPILATION]
     */
    val compilation: String?

    /**
     * [android.provider.MediaStore.MediaColumns.BITRATE]
     */
    val bitrate: Int

    /**
     * [android.provider.MediaStore.MediaColumns.CAPTURE_FRAMERATE]
     */
    val capture_framerate: Float
    //</editor-fold>
}
