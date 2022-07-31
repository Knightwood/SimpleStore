package com.kiylx.simplestore

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.documentfile.provider.DocumentFile
import com.google.android.material.button.MaterialButton
import com.kiylx.store_lib.StoreX
import com.kiylx.store_lib.kit.ext.makeToast
import com.kiylx.store_lib.kit.MimeTypeConsts
import com.kiylx.store_lib.kit.ext.writeFileFromUri
import com.kiylx.store_lib.mediastore.MediaStoreHelper
import com.kiylx.store_lib.saf.SAFHelper
import java.nio.charset.StandardCharsets

//AndroidManifest.xml中声明：android:hasFragileUserData="true"，卸载应用会有提示是否保留 APP数据。
//默认应用卸载时App-specific目录下的数据被删除，但用户可以选择保留。

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var helper: SAFHelper.Helper
    private lateinit var mediaStoreHelper: MediaStoreHelper.Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        helper = StoreX.initSaf(this)
        mediaStoreHelper = StoreX.initMediaStore(this)

        findViewById<MaterialButton>(R.id.request_prem).setOnClickListener(this)
        findViewById<MaterialButton>(R.id.save_prem).setOnClickListener(this)
        findViewById<MaterialButton>(R.id.create_file).setOnClickListener(this)
        findViewById<MaterialButton>(R.id.delete_file).setOnClickListener(this)
        findViewById<MaterialButton>(R.id.write_file).setOnClickListener(this)
        findViewById<MaterialButton>(R.id.read_file).setOnClickListener(this)
        findViewById<MaterialButton>(R.id.select_file).setOnClickListener(this)
        findViewById<MaterialButton>(R.id.newPic).setOnClickListener(this)

    }

    //申请的文件夹权限的uri
    var treeUri: Uri? = null
        set(value) {
            field = value
            value?.let { saveFileTree(it) }
        }
        get() {
            if (field == null) {
                val tmp = getFileTree()
                return Uri.parse(tmp)
            } else {
                return field
            }
        }

    //测试读写的文件，被建立在treeUri表示的文件夹下
    var testFileUri: Uri = Uri.EMPTY

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onClick(p0: View?) {
        p0?.let { view ->
            when (view.id) {
                R.id.request_prem -> {
                    helper.requestOneFolder { tree_ ->
                        //拿到了被授予读写权限的路径，使用helper.savePerms(tree)保存权限后，可以自己存起来下次使用
                        treeUri = tree_
                        DocumentFile.fromTreeUri(this@MainActivity, tree_)?.let { df ->
                            //使用DocumentFile创建文件
                            df.createFile(MimeTypeConsts.txt, "测试saf框架")?.let {
                                helper.writeFile(it.uri) { outputStream ->
                                    val str = "DocumentFile测试\n"
                                    outputStream.use { oStream ->
                                        //获取DocumentFile的uri，然后写入东西
                                        oStream.write(str.toByteArray(StandardCharsets.UTF_8))
                                    }
                                }
                            }
                        }
                    }
                }
                R.id.save_prem -> {
                    treeUri?.let { it1 -> helper.savePerms(it1) }
                }
                R.id.create_file -> {
                    helper.createFile("测试文件", MimeTypeConsts.txt, treeUri.toString()) {
                        testFileUri = it
                        Log.d(tag, "$testFileUri")
                    }
                }
                R.id.delete_file -> {
                    if (testFileUri == Uri.EMPTY) {
                        makeToast("没有文件可删除")
                    } else
                        helper.deleteFile(testFileUri) {
                            if (it) {
                                testFileUri = Uri.EMPTY
                                makeToast("文件删除成功")
                            }
                        }
                }
                R.id.write_file -> {
                    if (testFileUri == Uri.EMPTY) {
                        makeToast("没有文件可写入")
                    } else
                        helper.writeFile(testFileUri) { outputStream ->
                            val str = "Storage Access Framework Example\n"
                            outputStream.use { oStream ->
                                oStream.write(str.toByteArray(StandardCharsets.UTF_8))
                            }
                        }
                }
                R.id.read_file -> {
                    if (testFileUri == Uri.EMPTY) {
                        makeToast("没有文件可读取")
                    } else
                        helper.readFile(testFileUri) {
                            val bytes = it.readBytes()
                            makeToast(bytes.toString())
                        }
                }
                R.id.select_file -> {
                    helper.selectFile {
                        testFileUri = it
                        Log.d(tag, "被选择文件：$it")
                    }
                }
                R.id.newPic -> {
                    val bitmap =
                        (resources.getDrawable(R.drawable.shotcut1, null) as BitmapDrawable)
                            .bitmap
                    mediaStoreHelper.newPhoto("测试图片", MimeTypeConsts.png) {
                        writeFileFromUri (it) { os ->
                            bitmap.compress(Bitmap.CompressFormat.PNG, 85, os)
                        }
                    }
                }
                else -> {}
            }
        }
    }

    companion object {
        const val tag = "MainActivity"
    }
}