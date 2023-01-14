package com.manager.labo.utils

import java.io.File
import java.util.function.Consumer

class ClassScanner(private val path: String) {
    fun <T> getSubTypes(superType: Class<T>?): Set<Class<out T>> {
        val classes: MutableSet<Class<out T>> = HashSet()
        find(path).forEach(Consumer { aClass: Class<*> ->
            try {
                val subClass = aClass.asSubclass(superType)
                classes.add(subClass)
            } catch (ignore: ClassCastException) {
            }
        })
        return classes
    }

    fun find(scannedPackage: String): List<Class<*>> {
        val scannedPath = scannedPackage.replace(DOT, SLASH)
        val scannedUrl = Thread.currentThread().contextClassLoader.getResource(scannedPath)
            ?: throw IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage))
        val scannedDir = File(scannedUrl.file)
        val classes: MutableList<Class<*>> = ArrayList()
        for (file in scannedDir.listFiles()!!) {
            classes.addAll(find(file, scannedPackage))
        }
        return classes
    }

    private fun find(file: File, scannedPackage: String): List<Class<*>> {
        val classes: MutableList<Class<*>> = ArrayList()
        val resource = scannedPackage + DOT + file.name
        if (file.isDirectory) {
            for (child in file.listFiles()!!) {
                classes.addAll(find(child, resource))
            }
        } else if (resource.endsWith(CLASS_SUFFIX)) {
            val endIndex = resource.length - CLASS_SUFFIX.length
            val className = resource.substring(0, endIndex)
            try {
                classes.add(Class.forName(className))
            } catch (ignore: ClassNotFoundException) {
            }
        }
        return classes
    }

    companion object {
        private const val DOT = '.'
        private const val SLASH = '/'
        private const val CLASS_SUFFIX = ".class"
        private const val BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?"
    }
}
