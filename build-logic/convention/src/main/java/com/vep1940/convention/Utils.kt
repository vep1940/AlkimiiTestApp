package com.vep1940.convention

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun VersionCatalog.getLibrary(libraryAlias: String) =
    findLibrary(libraryAlias).get()

internal fun VersionCatalog.getVersion(versionAlias: String) =
    findVersion(versionAlias).get().requiredVersion

internal fun VersionCatalog.getPluginId(pluginAlias: String) =
    findPlugin(pluginAlias).get().get().pluginId

internal fun getJavaVersion() = JavaVersion.VERSION_21