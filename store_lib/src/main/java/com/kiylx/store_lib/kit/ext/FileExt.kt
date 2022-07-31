package com.kiylx.store_lib.kit.ext

import android.net.Uri
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import androidx.fragment.app.FragmentActivity
import com.kiylx.store_lib.kit.input
import com.kiylx.store_lib.kit.output
import java.text.SimpleDateFormat
import java.util.*


    fun generateFileName(): String {
        val fileName = SimpleDateFormat("yyyyMMddhhmmss", Locale.CHINA).format(Date())
        return fileName
    }

    fun Uri.openFD(
        fragmentActivity: FragmentActivity,
        uri: Uri,
        mode: String,
        cancellationSignal: CancellationSignal? = null,
        block: (pfd: ParcelFileDescriptor?) -> Unit
    ) {
        val f: ParcelFileDescriptor? = fragmentActivity.contentResolver.openFileDescriptor(
            uri,
            mode,
            cancellationSignal
        )
        block(f)
    }

    fun FragmentActivity.readFileFromUri(uri: Uri, input: input) {
        val ins = contentResolver.openInputStream(uri)
        ins.runSafelyNoNull(input)
    }

    fun FragmentActivity.writeFileFromUri(uri: Uri, output: output) {
        val ons = contentResolver.openOutputStream(uri)
        ons.runSafelyNoNull(output)
    }
