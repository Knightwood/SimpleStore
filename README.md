# 用途

* 简化了下`SAF`访问框架的使用

* 添加了一点点`MediaStore`的简便方法

# 如何使用

## 初始化

```
//这个是存储的帮助类
private lateinit var xHelper:StorageXHelper
//saf帮助类
private lateinit var helper: SafHelper.Helper
//mediastore的帮助类
private lateinit var mediaStoreHelper: MediaStoreHelper.Helper

//初始化
xHelper = StoreX.with(this)
helper=xHelper.safHelper
mdiaStoreHelper = xHelper.mediaStoreHelper
```

## SAF部分

1. 在`FragmentActivity`或`Fragment`中初始化一个`helper`对象。（不一定非要在`onCreate`之类的地方初始化）

```
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
		val helper: SAFHelper.Helper = StoreX.with(this).safHelper
	}
```

2. 调用`helper`中的方法

```
//申请一个目录的读写权限
helper.requestOneFolder {uri-> //被用户选择的目录uri
    //调用savePerms后，可以把uri存储起来
}

//保存目录的读写权限
helper.savePerms(uri)


//写文件
 helper.writeFile(testFileUri) { outputStream ->
    val str = "Storage Access Framework Example\n"
    outputStream.use { oStream ->
    	oStream.write(str.toByteArray(StandardCharsets.UTF_8))
         }
       }

//读文件
 helper.readFile(testFileUri) {
   val bytes = it.readBytes()
   makeToast(bytes.toString())
 }
                    
//选择文件
helper.selectFile {
	testFileUri = it
	Log.d(tag, "被选择文件：$it")
}
```

## MediaStore部分

1. 在`FragmentActivity`或`Fragment`中初始化一个`mediaStoreHelper`对象。（不一定非要在`onCreate`之类的地方初始化）

```
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mediaStoreHelper: MediaStoreHelper.Helper = 
                               StoreX.with(this).mediaStoreHelper

}
```

2. 调用`mediaStoreHelper`中的方法，例如：

   把一张图片存进图库里

```
  val bitmap =
      (resources.getDrawable(R.drawable.shotcut1, null) as BitmapDrawable)
      .bitmap
      mediaStoreHelper.newPhoto("测试图片", MimeTypeConsts.png) {
          writeFileFromUri (it) { os ->
          bitmap.compress(Bitmap.CompressFormat.PNG, 85, os)
             }
   }
```



* 更多用法暂时没时间写了，在app module下有示例代码。

