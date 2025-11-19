package com.androidx.mediastorefile.parser.base

import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import com.androidx.mediastorefile.item.MediaFile
import com.androidx.mediastorefile.parser.utils.ColumnNameIndexMap
import com.androidx.mediastorefile.parser.utils.indexOf
import com.androidx.mediastorefile.parser.utils.ICursorParser
import com.androidx.mediastorefile.parser.utils.mapOfNullable

class MediaCursorParserApi30 : ICursorParser<MediaFile>(
    apiLevel = Build.VERSION_CODES.R
) {
    override fun parseColumnIndex(cursor: Cursor): ColumnNameIndexMap {
        val album_column = cursor.indexOf(MediaStore.MediaColumns.ALBUM)
        val album_artist_column = cursor.indexOf(MediaStore.MediaColumns.ALBUM_ARTIST)
        val artist_column = cursor.indexOf(MediaStore.MediaColumns.ARTIST)
        val author_column = cursor.indexOf(MediaStore.MediaColumns.AUTHOR)
        val bitrate_column = cursor.indexOf(MediaStore.MediaColumns.BITRATE)
        val capture_framerate_column = cursor.indexOf(MediaStore.MediaColumns.CAPTURE_FRAMERATE)
        val cd_track_number_column = cursor.indexOf(MediaStore.MediaColumns.CD_TRACK_NUMBER)
        val compilation_column = cursor.indexOf(MediaStore.MediaColumns.COMPILATION)
        val composer_column = cursor.indexOf(MediaStore.MediaColumns.COMPOSER)
        val disc_number_column = cursor.indexOf(MediaStore.MediaColumns.DISC_NUMBER)
        val generation_added_column = cursor.indexOf(MediaStore.MediaColumns.GENERATION_ADDED)
        val generation_modified_column = cursor.indexOf(MediaStore.MediaColumns.GENERATION_MODIFIED)
        val genre_column = cursor.indexOf(MediaStore.MediaColumns.GENRE)
        val is_download_column = cursor.indexOf(MediaStore.MediaColumns.IS_DOWNLOAD)
        val is_drm_column = cursor.indexOf(MediaStore.MediaColumns.IS_DRM)
        val is_favorite_column = cursor.indexOf(MediaStore.MediaColumns.IS_FAVORITE)
        val is_trashed_column = cursor.indexOf(MediaStore.MediaColumns.IS_TRASHED)
        val num_tracks_column = cursor.indexOf(MediaStore.MediaColumns.NUM_TRACKS)
        val resolution_column = cursor.indexOf(MediaStore.MediaColumns.RESOLUTION)
        val writer_column = cursor.indexOf(MediaStore.MediaColumns.WRITER)
        val xmp_column = cursor.indexOf(MediaStore.MediaColumns.XMP)
        val year_column = cursor.indexOf(MediaStore.MediaColumns.YEAR)

        return mapOfNullable(
            album_column,
            album_artist_column,
            artist_column,
            author_column,
            bitrate_column,
            capture_framerate_column,
            cd_track_number_column,
            compilation_column,
            composer_column,
            disc_number_column,
            generation_added_column,
            generation_modified_column,
            genre_column,
            is_download_column,
            is_drm_column,
            is_favorite_column,
            is_trashed_column,
            num_tracks_column,
            resolution_column,
            writer_column,
            xmp_column,
            year_column
        )
    }

    override fun setFieldValue(cursor: Cursor, data: MediaFile) {
        val is_drm = cursor.getIntOr(MediaStore.MediaColumns.IS_DRM)
        val is_trashed = cursor.getIntOr(MediaStore.MediaColumns.IS_TRASHED)
        val resolution = cursor.getStringOrNull(MediaStore.MediaColumns.RESOLUTION)
        val is_favorite = cursor.getIntOr(MediaStore.MediaColumns.IS_FAVORITE)
        val is_download = cursor.getIntOr(MediaStore.MediaColumns.IS_DOWNLOAD)
        val generation_added = cursor.getIntOr(MediaStore.MediaColumns.GENERATION_ADDED)
        val generation_modified = cursor.getIntOr(MediaStore.MediaColumns.GENERATION_MODIFIED)
        val xmp = cursor.getBlobOrNull(MediaStore.MediaColumns.XMP)
        val cd_track_number = cursor.getStringOrNull(MediaStore.MediaColumns.CD_TRACK_NUMBER)
        val album = cursor.getStringOrNull(MediaStore.MediaColumns.ALBUM)
        val artist = cursor.getStringOrNull(MediaStore.MediaColumns.ARTIST)
        val author = cursor.getStringOrNull(MediaStore.MediaColumns.AUTHOR)
        val composer = cursor.getStringOrNull(MediaStore.MediaColumns.COMPOSER)
        val genre = cursor.getStringOrNull(MediaStore.MediaColumns.GENRE)
        val year = cursor.getIntOr(MediaStore.MediaColumns.YEAR)
        val num_tracks = cursor.getIntOr(MediaStore.MediaColumns.NUM_TRACKS)
        val writer = cursor.getStringOrNull(MediaStore.MediaColumns.WRITER)
        val album_artist = cursor.getStringOrNull(MediaStore.MediaColumns.ALBUM_ARTIST)
        val disc_number = cursor.getStringOrNull(MediaStore.MediaColumns.DISC_NUMBER)
        val compilation = cursor.getStringOrNull(MediaStore.MediaColumns.COMPILATION)
        val bitrate = cursor.getIntOr(MediaStore.MediaColumns.BITRATE)
        val capture_framerate = cursor.getFloatOr(MediaStore.MediaColumns.CAPTURE_FRAMERATE)
        data.apply {
            this.is_drm = is_drm
            this.is_trashed = is_trashed
            this.resolution = resolution
            this.is_favorite = is_favorite
            this.is_download = is_download
            this.generation_added = generation_added
            this.generation_modified = generation_modified
            this.xmp = xmp
            this.cd_track_number = cd_track_number
            this.album = album
            this.artist = artist
            this.author = author
            this.composer = composer
            this.genre = genre
            this.year = year
            this.num_tracks = num_tracks
            this.album_artist = album_artist
            this.disc_number = disc_number
            this.compilation = compilation
            this.bitrate = bitrate
            this.capture_framerate = capture_framerate
            this.writer = writer
        }
    }

}
