package com.kiylx.store_lib.saf

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

class SAFHelper {
    private var activity: FragmentActivity
    private var fragment: Fragment? = null

    constructor(fragment: Fragment) {
        this.fragment = fragment
        this.activity = fragment.requireActivity()
    }

    constructor(fragmentActivity: FragmentActivity) {
        this.activity = fragmentActivity
    }

    /**
     * 在Activity中获取 FragmentManager，如果在Fragment中，则获取 ChildFragmentManager。
     */
    private val fragmentManager: FragmentManager
        get() {
            return fragment?.childFragmentManager ?: activity.supportFragmentManager
        }

    private val invisibleFragment: SafImplFragment
        get() {
            val existed = fragmentManager.findFragmentByTag(FRAGMENT_TAG)
            if (existed != null) {
                return existed as SafImplFragment
            } else {
                val invisibleFragment = SafImplFragment()
                fragmentManager.beginTransaction()
                    .add(invisibleFragment, FRAGMENT_TAG)
                    .commitNowAllowingStateLoss()
                return invisibleFragment
            }
        }

    /**
     * 使用此实例操作文件
     */
    val helper: Helper by lazy {
        Helper(invisibleFragment)
    }

    /**
     * 所有的文件操作，全部委托给[invisibleFragment]
     * 使用委托的方式，隐藏掉fragment，只向外界提供接口的实现
     */
    inner class Helper(private val invisibleFragment: SafImplFragment) :
        FileMethod by invisibleFragment

    companion object {
        /**
         * TAG of InvisibleFragment to find and create.
         */
        private const val FRAGMENT_TAG = "SafInvisibleFragment"
    }
}
