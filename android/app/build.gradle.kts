// 앱 모듈 빌드 설정
// 핵심 아이디어: 레포 루트의 index.html(계측기 5종 통합 페이지)을 빌드할 때마다 앱 안으로 복사한다.
// 그래서 웹 위젯을 고치고 build.js로 index.html을 다시 만들면, 앱도 다음 빌드부터 자동으로 최신이 된다.

plugins {
    id("com.android.application")
}

// ── 버전 정보 ─────────────────────────────────────────────
// 깃허브 Actions가 빌드할 때 환경변수로 넘겨준다. 내 PC에서 빌드하면 기본값(1, 1.0)을 쓴다.
// versionCode: 플레이스토어가 "새 버전인지" 판단하는 정수(올릴 때마다 커져야 함)
// versionName: 사용자에게 보이는 버전 글자(예: 1.0.0)
val appVersionCode = (System.getenv("APP_VERSION_CODE") ?: "1").toInt()
val appVersionName = System.getenv("APP_VERSION_NAME") ?: "1.0"

// ── 서명 정보(플레이스토어 업로드용) ─────────────────────────
// 깃허브 Secrets에 키스토어를 등록해 두면 환경변수로 들어온다. 없으면 서명 없이(디버그용만) 빌드한다.
val keystorePath = System.getenv("ANDROID_KEYSTORE_PATH")
val hasReleaseKey = !keystorePath.isNullOrBlank() && file(keystorePath).exists()

android {
    namespace = "io.github.kghdac.gaugetrainer"
    compileSdk = 36

    defaultConfig {
        applicationId = "io.github.kghdac.gaugetrainer"   // 플레이스토어에서 앱을 구분하는 고유 이름(한 번 올리면 바꿀 수 없음)
        minSdk = 26                                        // 안드로이드 8.0 이상에서 설치 가능
        targetSdk = 36                                     // 플레이스토어가 요구하는 최신 기준에 맞춤
        versionCode = appVersionCode
        versionName = appVersionName
    }

    signingConfigs {
        if (hasReleaseKey) {
            create("release") {
                storeFile = file(keystorePath!!)
                storePassword = System.getenv("ANDROID_KEYSTORE_PASSWORD")
                keyAlias = System.getenv("ANDROID_KEY_ALIAS")
                keyPassword = System.getenv("ANDROID_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false   // 코드가 작아서 압축(난독화)은 하지 않는다
            if (hasReleaseKey) signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // 아래 syncWebAssets 작업이 만든 폴더를 앱의 assets(앱 안에 그대로 들어가는 파일)로 추가
    sourceSets["main"].assets.srcDir(layout.buildDirectory.dir("generated/webassets"))
}

// ── 웹 파일 복사 작업 ────────────────────────────────────────
// 레포 루트(android 폴더의 한 단계 위)의 index.html 을 앱 assets/www/ 로 복사한다.
// index.html 하나에 계측기 5종과 로고 이미지가 모두 들어 있어 이 파일만 있으면 된다.
val syncWebAssets by tasks.registering(Copy::class) {
    from(rootDir.parentFile) {
        include("index.html")
    }
    into(layout.buildDirectory.dir("generated/webassets/www"))
}

// 앱을 빌드하기 전에 항상 웹 파일을 먼저 복사한다
tasks.named("preBuild") {
    dependsOn(syncWebAssets)
}
