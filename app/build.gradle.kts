plugins {
    id("com.android.application")
}

android {
    namespace = "com.iptv.iptv2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.iptv.iptv2"
        minSdk = 26
        targetSdk = 34
        versionCode = 4
        versionName = "1.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField ("String", "LIVE_TV_M3U_URL", "\"https://tvnow.best/api/list/couch0723@gmail.com/67745443/m3u8/livetv\"")
        buildConfigField ("String", "MOVIES_M3U_URL", "\"https://tvnow.best/api/list/couch0723@gmail.com/67745443/m3u8/movies\"")
        buildConfigField ("String", "SHOWS_M3U_URL", "\"https://tvnow.best/api/list/couch0723@gmail.com/67745443/m3u8/tvshows\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField ("String", "LIVE_TV_M3U_URL", "\"https://tvnow.best/api/list/couch0723@gmail.com/67745443/m3u8/livetv\"")
            buildConfigField ("String", "MOVIES_M3U_URL", "\"https://tvnow.best/api/list/couch0723@gmail.com/67745443/m3u8/movies\"")
            buildConfigField ("String", "SHOWS_M3U_URL", "\"https://tvnow.best/api/list/couch0723@gmail.com/67745443/m3u8/tvshows\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        buildConfig = true
    }
}



dependencies {
    val leanback_version = "1.2.0-alpha04"
    implementation("androidx.leanback:leanback:$leanback_version")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("com.google.code.gson:gson:2.11.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0")






}