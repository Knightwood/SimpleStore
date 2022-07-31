@file:Suppress("unused")

package com.kiylx.store_lib

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.kiylx.store_lib.mediastore.MediaStoreHelper
import com.kiylx.store_lib.saf.SAFHelper

class StoreX {
    companion object {
        @JvmStatic
        fun initSaf(fragmentActivity: FragmentActivity): SAFHelper.Helper {
            return SAFHelper(fragmentActivity).helper
        }

        @JvmStatic
        fun initSaf(fragment: Fragment): SAFHelper.Helper {
            return SAFHelper(fragment).helper
        }

        @JvmStatic
        fun initMediaStore(fragmentActivity: FragmentActivity): MediaStoreHelper.Helper {
            return MediaStoreHelper(fragmentActivity).helper
        }

        @JvmStatic
        fun initMediaStore(fragment: Fragment): MediaStoreHelper.Helper {
            return MediaStoreHelper(fragment).helper
        }
    }
}