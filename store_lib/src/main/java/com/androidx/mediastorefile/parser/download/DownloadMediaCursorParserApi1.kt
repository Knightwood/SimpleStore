package com.androidx.mediastorefile.parser.download

import android.database.Cursor
import com.androidx.mediastorefile.parser.utils.ColumnNameIndexMap
import com.androidx.mediastorefile.item.DownloadMediaFile
import com.androidx.mediastorefile.parser.base.basicMediaStoreMapper
import com.androidx.mediastorefile.parser.utils.AggregationCursorMapper
import com.androidx.mediastorefile.parser.utils.CursorSampleMapper
import com.androidx.mediastorefile.parser.utils.ICursorParser

class DownloadMediaCursorParserApi1 : ICursorParser<DownloadMediaFile>() {

    override fun parseColumnIndex(cursor: Cursor): ColumnNameIndexMap {
        return mapOf()
    }

    override fun setFieldValue(cursor: Cursor, data: DownloadMediaFile) {}

}

val downloadMediaCursorMapper = AggregationCursorMapper<DownloadMediaFile>(
    mapper = CursorSampleMapper<DownloadMediaFile>() +
            DownloadMediaCursorParserApi1() +
            DownloadMediaCursorParserApi29(),
    dataCreator = { cursor ->
        DownloadMediaFile(
            property = basicMediaStoreMapper.mapOneRowFrom(cursor)
        )
    }
) + basicMediaStoreMapper
