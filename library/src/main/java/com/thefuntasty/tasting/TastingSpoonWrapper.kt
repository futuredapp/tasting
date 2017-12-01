package com.thefuntasty.tasting

import android.os.Build
import android.os.Environment
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object TastingSpoonWrapper {

    private val TEST_CASE_CLASS_JUNIT_3 = "android.test.InstrumentationTestCase"
    private val TEST_CASE_METHOD_JUNIT_3 = "runMethod"
    private val TEST_CASE_CLASS_JUNIT_4 = "org.junit.runners.model.FrameworkMethod$1"
    private val TEST_CASE_METHOD_JUNIT_4 = "runReflectiveCall"
    private val TEST_CASE_CLASS_CUCUMBER_JVM = "cucumber.runtime.model.CucumberFeature"
    private val TEST_CASE_METHOD_CUCUMBER_JVM = "run"
    private val MARSHMALLOW_API_LEVEL = 23

    fun getScreenshotDirectory(screenshotTitle: String): File {
        val directory = File(Environment.getExternalStorageDirectory().path + "/app_spoon-screenshots")

        val testClass = findTestClassTraceElement(Thread.currentThread().stackTrace)
        val className = testClass.className.replace("[^A-Za-z0-9._-]".toRegex(), "_")
        val methodName = testClass.methodName

        val dirClass = File(directory, className)
        val dirMethod = File(dirClass, methodName)
        createDir(dirMethod)

        val currentTimeMillis = System.currentTimeMillis()
        val sdf = SimpleDateFormat("dd-MM-yyyy-HH-mm-ss", Locale.getDefault())
        val date = Date(currentTimeMillis)

        val screenshotName = sdf.format(date) + "_" + screenshotTitle + ".png"
        return File(dirMethod, screenshotName)
    }

    private fun createDir(dir: File) {
        val parent = dir.parentFile
        if (!parent.exists()) {
            createDir(parent)
        }
        if (!dir.exists() && !dir.mkdirs()) {
            Log.e("TASTING", "Unable to create screenshot directory")
        }
    }

    private fun findTestClassTraceElement(trace: Array<StackTraceElement>): StackTraceElement {
        for (i in trace.indices.reversed()) {
            val element = trace[i]
            if (TEST_CASE_CLASS_JUNIT_3 == element.className //
                    && TEST_CASE_METHOD_JUNIT_3 == element.methodName) {
                return extractStackElement(trace, i)
            }

            if (TEST_CASE_CLASS_JUNIT_4 == element.className //
                    && TEST_CASE_METHOD_JUNIT_4 == element.methodName) {
                return extractStackElement(trace, i)
            }
            if (TEST_CASE_CLASS_CUCUMBER_JVM == element.className //
                    && TEST_CASE_METHOD_CUCUMBER_JVM == element.methodName) {
                return extractStackElement(trace, i)
            }
        }

        throw IllegalArgumentException("Could not find test class!")
    }

    private fun extractStackElement(trace: Array<StackTraceElement>, i: Int): StackTraceElement {
        //Stacktrace length changed in M
        val testClassTraceIndex = if (Build.VERSION.SDK_INT >= MARSHMALLOW_API_LEVEL) i - 2 else i - 3
        return trace[testClassTraceIndex]
    }
}
