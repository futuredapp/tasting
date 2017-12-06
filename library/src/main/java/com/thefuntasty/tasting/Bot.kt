package com.thefuntasty.tasting

import android.content.Context
import android.os.RemoteException
import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.By
import android.support.test.uiautomator.StaleObjectException
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiObject2
import android.support.test.uiautomator.Until
import com.github.javafaker.Faker
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import java.util.concurrent.TimeUnit

class Bot(private val testDevice: UiDevice) {

    private val context: Context = InstrumentationRegistry.getTargetContext()
    private val faker = Faker()

    // Public bot settings
    var viewTimeout = 5000
    var scrollSteps = 10
    var scrollThreshold = 10
    var scrollTimeout = 2000
    var testedPackageName = context.packageName

    // Fake data
    val firstName: String
        get() = faker.name().firstName()

    val lastName: String
        get() = faker.name().lastName()

    val fullName: String
        get() = firstName + " " + lastName

    val email: String
        get() = faker.internet().safeEmailAddress()

    // Actions
    fun tapById(viewId: String) {
        try {
            testDevice.wait(Until.findObject(By.res(testedPackageName, viewId)), viewTimeout.toLong()).click()
        } catch (e: NullPointerException) {
            takeScreenshot("exception")
            throw TastingException("View with id \"$viewId\" not found", e)
        } catch (e: StaleObjectException) {
            tapById(viewId)
        }
    }

    fun tapByText(text: String) {
        try {
            testDevice.wait(Until.findObject(By.text(text)), viewTimeout.toLong()).click()
        } catch (e: NullPointerException) {
            takeScreenshot("exception")
            throw TastingException("View with text \"$text\" not found", e)
        } catch (e: StaleObjectException) {
            tapByText(text)
        }
    }

    fun tapByContainedText(text: String) {
        try {
            testDevice.wait(Until.findObject(By.textContains(text)), viewTimeout.toLong()).click()
        } catch (e: NullPointerException) {
            takeScreenshot("exception")
            throw TastingException("View with text that contains \"$text\" not found", e)
        } catch (e: StaleObjectException) {
            tapByContainedText(text)
        }
    }

    fun tapByDescription(contentDescription: String) {
        try {
            testDevice.wait(Until.findObject(By.desc(contentDescription)), viewTimeout.toLong()).click()
        } catch (e: NullPointerException) {
            takeScreenshot("exception")
            throw TastingException("View with content description \"$contentDescription\" not found", e)
        } catch (e: StaleObjectException) {
            tapByDescription(contentDescription)
        }
    }

    fun tapByContainedInDescription(contentDescription: String) {
        try {
            testDevice.wait(Until.findObject(By.descContains(contentDescription)), viewTimeout.toLong()).click()
        } catch (e: NullPointerException) {
            takeScreenshot("exception")
            throw TastingException("View with content description that contains \"$contentDescription\" not found", e)
        } catch (e: StaleObjectException) {
            tapByContainedInDescription(contentDescription)
        }
    }

    fun writeByText(findText: String, writeText: String) {
        try {
            testDevice.wait(Until.findObject(By.text(findText).clazz("android.widget.EditText")), viewTimeout.toLong()).text = writeText
        } catch (e: NullPointerException) {
            takeScreenshot("exception")
            throw TastingException("View with text \"$findText\" not found", e)
        } catch (e: StaleObjectException) {
            writeByText(findText, writeText)
        }
    }

    fun writeById(viewId: String, writeText: String) {
        try {
            testDevice.wait(Until.findObject(By.res(testedPackageName, viewId).clazz("android.widget.EditText")), viewTimeout.toLong()).text = writeText
        } catch (e: NullPointerException) {
            takeScreenshot("exception")
            throw TastingException("View with id \"$viewId\" not found", e)
        } catch (e: StaleObjectException) {
            writeById(viewId, writeText)
        }
    }

    fun writeByDescription(contentDescription: String, writeText: String) {
        try {
            testDevice.wait(Until.findObject(By.desc(contentDescription).clazz("android.widget.EditText")), viewTimeout.toLong()).text = writeText
        } catch (e: NullPointerException) {
            takeScreenshot("exception")
            throw TastingException("View with content description \"$contentDescription\" not found", e)
        } catch (e: StaleObjectException) {
            writeByText(contentDescription, writeText)
        }
    }

    fun allowPermission() {
        try {
            testDevice.wait(Until.findObject(By.res("com.android.packageinstaller", "permission_allow_button")), viewTimeout.toLong()).click()
        } catch (e: NullPointerException) {
            takeScreenshot("exception")
            throw TastingException("Permission dialog not found", e)
        }
    }

    fun denyPermission() {
        try {
            testDevice.wait(Until.findObject(By.res("com.android.packageinstaller", "permission_deny_button")), viewTimeout.toLong()).click()
        } catch (e: NullPointerException) {
            takeScreenshot("exception")
            throw TastingException("Permission dialog not found", e)
        }
    }

    fun scroll(direction: ScrollDirection) {
        val screenWidth = testDevice.displayWidth
        val screenHeight = testDevice.displayHeight

        when (direction) {
            ScrollDirection.DOWN -> testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, 0, scrollSteps)
            ScrollDirection.UP -> testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, screenHeight, scrollSteps)
            ScrollDirection.LEFT -> testDevice.drag(screenWidth / 4, screenHeight / 2, screenWidth, screenHeight / 2, scrollSteps)
            ScrollDirection.RIGHT -> testDevice.drag(screenWidth - screenWidth / 4, screenHeight / 2, 0, screenHeight / 2, scrollSteps)
        }
    }

    fun halfScroll(direction: ScrollDirection) {
        val screenWidth = testDevice.displayWidth
        val screenHeight = testDevice.displayHeight

        when (direction) {
            ScrollDirection.DOWN -> testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, screenHeight / 4, scrollSteps)
            ScrollDirection.UP -> testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, screenHeight / 4 * 3, scrollSteps)
            ScrollDirection.LEFT -> testDevice.drag(screenWidth / 4, screenHeight / 2, screenWidth / 2, screenHeight / 2, scrollSteps)
            ScrollDirection.RIGHT -> testDevice.drag(screenWidth - screenWidth / 4, screenHeight / 2, screenWidth / 2, screenHeight / 2, scrollSteps)
        }
    }

    fun scrollUntilId(direction: ScrollDirection, viewId: String) {
        var retry = 0
        do {
            if (testDevice.wait(Until.findObject(By.res(testedPackageName, viewId)), scrollTimeout.toLong()) != null) {
                return
            }
            halfScroll(direction)
            retry++
        } while (retry <= scrollThreshold)
        takeScreenshot("exception")
        throw TastingException("View with id \"$viewId\" not found")
    }

    fun scrollUntilText(direction: ScrollDirection, text: String) {
        var retry = 0
        do {
            if (testDevice.wait(Until.findObject(By.text(text)), scrollTimeout.toLong()) != null) {
                return
            }
            halfScroll(direction)
            retry++
        } while (retry <= scrollThreshold)
        takeScreenshot("exception")
        throw TastingException("View with text \"$text\" not found")
    }

    fun dragViewById(direction: ScrollDirection, viewId: String) {
        val screenWidth = testDevice.displayWidth
        val screenHeight = testDevice.displayHeight
        val viewX = waitForId(viewId).visibleCenter.x
        val viewY = waitForId(viewId).visibleCenter.y

        when (direction) {
            ScrollDirection.DOWN -> testDevice.drag(viewX, viewY, viewX, viewY - screenHeight, scrollSteps)
            ScrollDirection.UP -> testDevice.drag(viewX, viewY, viewX, viewY + screenHeight, scrollSteps)
            ScrollDirection.LEFT -> testDevice.drag(viewX, viewY, viewX + screenWidth, viewY, scrollSteps)
            ScrollDirection.RIGHT -> testDevice.drag(viewX, viewY, viewX - screenWidth, viewY, scrollSteps)
        }
    }

    fun dragViewByText(direction: ScrollDirection, text: String) {
        val screenWidth = testDevice.displayWidth
        val screenHeight = testDevice.displayHeight
        val viewX = waitForText(text).visibleCenter.x
        val viewY = waitForText(text).visibleCenter.y

        when (direction) {
            ScrollDirection.DOWN -> testDevice.drag(viewX, viewY, viewX, viewY - screenHeight, scrollSteps)
            ScrollDirection.UP -> testDevice.drag(viewX, viewY, viewX, viewY + screenHeight, scrollSteps)
            ScrollDirection.LEFT -> testDevice.drag(viewX, viewY, viewX + screenWidth, viewY, scrollSteps)
            ScrollDirection.RIGHT -> testDevice.drag(viewX, viewY, viewX - screenWidth, viewY, scrollSteps)
        }
    }

    fun pressBack() {
        testDevice.pressBack()
    }

    fun pressHome() {
        testDevice.pressHome()
    }

    fun pressRecents() {
        try {
            testDevice.pressRecentApps()
        } catch (e: RemoteException) {
            throw TastingException(e)
        }
    }

    // Assertions
    fun notPresentByText(text: String) {
        try {
            assertNull("Text \"$text\" should not be present", waitForTextOrNull(text))
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            notPresentByText(text)
        }
    }

    fun notPresentById(viewId: String) {
        try {
            assertNull("View with id \"$viewId\" should not be present", waitForIdOrNull(viewId))
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            notPresentById(viewId)
        }
    }

    fun presentByText(text: String) {
        try {
            assertNotNull("Text \"$text\" is not present", waitForTextOrNull(text))
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            presentByText(text)
        }
    }

    fun presentById(viewId: String) {
        try {
            assertNotNull("View with id \"$viewId\" is not present", waitForIdOrNull(viewId))
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            presentById(viewId)
        }
    }

    fun textInIdEquals(viewId: String, text: String) {
        try {
            assertTrue("Text in view with id \"$viewId\" is not \"$text\"", waitForId(viewId).text == text)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            textInIdEquals(viewId, text)
        }
    }

    fun textInIdEqualsCaseInsensitive(viewId: String, text: String) {
        try {
            assertTrue("Text in view with id \"$viewId\" is not \"$text\"", waitForId(viewId).text.equals(text, ignoreCase = true))
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            textInIdEqualsCaseInsensitive(viewId, text)
        }
    }

    fun textInIdContains(viewId: String, text: String) {
        try {
            assertTrue("Text in view with id \"$viewId\" does not contain \"$text\"", waitForId(viewId).text.contains(text))
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            textInIdContains(viewId, text)
        }
    }

    fun textInIdDiffer(viewId: String, text: String) {
        try {
            assertFalse("Text in view with id \"$viewId\" should not be \"$text\"", waitForId(viewId).text == text)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            textInIdDiffer(viewId, text)
        }
    }

    fun enabledById(viewId: String) {
        try {
            assertTrue("View with id \"$viewId\" should not be enabled", waitForId(viewId).isEnabled)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            enabledById(viewId)
        }
    }

    fun disabledById(viewId: String) {
        try {
            assertFalse("View with id \"$viewId\" should not be disabled", waitForId(viewId).isEnabled)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            disabledById(viewId)
        }
    }

    fun checkedById(viewId: String) {
        try {
            assertTrue("View with id \"$viewId\" is not checked", waitForId(viewId).isChecked)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            checkedById(viewId)
        }
    }

    fun notCheckedById(viewId: String) {
        try {
            assertFalse("View with id \"$viewId\" should not be checked", waitForId(viewId).isChecked)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            notCheckedById(viewId)
        }
    }

    fun checkedByText(text: String) {
        try {
            assertTrue("View with text \"$text\" is not checked", waitForText(text).isChecked)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            checkedByText(text)
        }
    }

    fun notCheckedByText(text: String) {
        try {
            assertFalse("View with text \"$text\" should not be checked", waitForText(text).isChecked)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            notCheckedByText(text)
        }
    }

    fun selectedById(viewId: String) {
        try {
            assertTrue("View with id \"$viewId\" is not selected", waitForId(viewId).isSelected)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            selectedById(viewId)
        }
    }

    fun notSelectedById(viewId: String) {
        try {
            assertFalse("View with id \"$viewId\" should not be selected", waitForId(viewId).isSelected)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            notSelectedById(viewId)
        }
    }

    fun selectedByText(text: String) {
        try {
            assertTrue("View with text \"$text\" is not selected", waitForText(text).isSelected)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            selectedByText(text)
        }
    }

    fun notSelectedByText(text: String) {
        try {
            assertFalse("View with text \"$text\" should not be selected", waitForText(text).isSelected)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            notSelectedByText(text)
        }
    }

    fun getRandomString(length: Int): String = faker.lorem().characters(length)

    fun getRandomNumber(min: Int, max: Int): String = faker.number().numberBetween(min, max).toString()

    // Misc
    fun getTextById(viewId: String): String = waitForId(viewId).text

    fun takeScreenshot(name: String) {
        testDevice.takeScreenshot(TastingSpoonWrapper.getScreenshotDirectory(name))
    }

    @JvmOverloads
    fun waitForId(viewId: String, milliseconds: Int = viewTimeout): UiObject2 {
        val view = testDevice.wait(Until.findObject(By.res(testedPackageName, viewId)), milliseconds.toLong())
        if (view == null) {
            takeScreenshot("exception")
            throw TastingException("View with id \"$viewId\" not found")
        } else {
            return view
        }
    }

    fun waitForIdOrNull(viewId: String): UiObject2 =
            testDevice.wait(Until.findObject(By.res(testedPackageName, viewId)), viewTimeout.toLong())

    @JvmOverloads
    fun waitForText(text: String, milliseconds: Int = viewTimeout): UiObject2 {
        val view = testDevice.wait(Until.findObject(By.text(text)), milliseconds.toLong())
        if (view == null) {
            takeScreenshot("exception")
            throw TastingException("View with text \"$text\" not found")
        } else {
            return view
        }
    }

    fun waitForTextOrNull(text: String): UiObject2 =
            testDevice.wait(Until.findObject(By.text(text)), viewTimeout.toLong())

    fun wait(seconds: Int) {
        // This is ugly but it works
        try {
            TimeUnit.SECONDS.sleep(seconds.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun waitForNextActivity() {
        testDevice.waitForWindowUpdate(testedPackageName, viewTimeout.toLong())
    }

    fun getString(resourceId: Int): String = context.getString(resourceId)

    fun getViewId(resourceId: Int): String = context.resources.getResourceEntryName(resourceId)
}
