pluginManagement {
    repositories {
        // 1) Узкая Google-копия — только то, что обычно нужно Android-проекту.
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        // 2) Полная Google-репа как fallback для редких плагинов.
        google()
        // Стандартные центральные репозитории
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

rootProject.name = "Midterm"
include(":app")
