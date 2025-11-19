plugins {
    val agp="8.10.0"
    val kotlin = "2.2.10"
    id("com.android.application") version agp apply false
    id ("com.android.library") version agp apply false
    id ("org.jetbrains.kotlin.android") version kotlin apply false
}

ext {
    this["version"] = "1.3.1"
    this["abi"] = listOf("arm64-v8a") //listOf("armeabi", "armeabi-v7a", "arm64-v8a")
}
