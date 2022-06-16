import ProjectProperties.LOCAL_MAVEN_REPO
import BuildVersion.buildVersionName
import ProjectProperties.projectGroup

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  kotlin("multiplatform")
  id("com.android.library")
  `maven-publish`
}

version = buildVersionName
group = projectGroup

