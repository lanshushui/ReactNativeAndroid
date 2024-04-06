pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            // 从node_modules/jsc-android/dist/org 复制来的依赖库
            setUrl( "$rootDir/repo")
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "ReactNativeAndroid"
include(":app")
