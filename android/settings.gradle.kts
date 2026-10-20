// 안드로이드 앱 프로젝트 설정 파일
// - 어디서 빌드 도구(플러그인)와 라이브러리를 내려받을지 정한다.
// - 이 프로젝트에는 "app" 모듈 하나만 있다.

pluginManagement {
    repositories {
        google()            // 안드로이드 빌드 플러그인(AGP)이 있는 구글 저장소
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GaugeTrainer"
include(":app")
