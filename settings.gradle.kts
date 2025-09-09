pluginManagement {
    repositories {
        google()   // ✅ Required for Play Services + Firebase
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()   // ✅ Required
        mavenCentral()
    }
}


rootProject.name = "LifeShield"
include(":app")
 