plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-parcelize")
    id("kotlin-kapt")
}


android {
    namespace = "com.tangbuan.wanandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tangbuan.wanandroid"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }


}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
//    implementation(libs.androidx.library)
    implementation(libs.androidx.databinding.runtime)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // 下拉刷新依赖
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    // recyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    // 轮播图
    implementation("io.github.youth5201314:banner:2.2.2")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    // recycleView的万能适配器
    implementation("io.github.cymchad:BaseRecyclerViewAdapterHelper:3.0.7")
    // 使用viewModelScope协程
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    // 网络请求Retrofit2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // retrofit网络解析
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // 替代SharePreference的mmkv
    implementation("com.tencent:mmkv:1.2.13")
    // material风格功能强大的Dialog
    implementation("com.afollestad.material-dialogs:core:3.3.0")
    implementation("com.afollestad.material-dialogs:lifecycle:3.3.0")
    implementation("com.afollestad.material-dialogs:bottomsheets:3.3.0")
    // okhttp3 缓存
    implementation("com.github.franmontiel:PersistentCookieJar:v1.0.1")
    // 更强大的webView
    implementation("com.github.Justson.AgentWeb:agentweb-core:v4.1.9-androidx")
}