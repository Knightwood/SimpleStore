package com.kiylx.simplestore

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.preference.PreferenceManager
import androidx.fragment.app.FragmentActivity
import com.kiylx.simplestore.Prefs.Companion.fileTree

fun getPrefs(context: Context): SharedPreferences? =
    PreferenceManager.getDefaultSharedPreferences(context)

fun FragmentActivity.saveFileTree(uri: Uri) {
    getPrefs(this)?.edit()?.putString(fileTree, uri.toString())?.apply()
}

fun FragmentActivity.getFileTree(): String? {
    return getPrefs(this)?.getString(fileTree, "")
}

class Prefs {

    companion object {
        /**
         * 拿到的被授予权限的目录uri
         */
        const val fileTree = "file_tree"
    }
}