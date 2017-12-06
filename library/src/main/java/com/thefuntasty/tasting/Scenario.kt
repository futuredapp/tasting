package com.thefuntasty.tasting

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.UiDevice
import org.junit.Before

abstract class Scenario {

    private val testDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    @JvmField protected val bot: Bot = Bot(testDevice)

    @Before
    fun setUp() {
        // Here you can delete shared preferences or databases to make tests run from same initial state
        beforeSetUp()
        if (!testDevice.isScreenOn) {
            testDevice.wakeUp()
            testDevice.pressKeyCode(1) // Unlocks screen - works for some devices only
        }

        // Launch the app
        val context = InstrumentationRegistry.getContext()
        val intent = context.packageManager
                .getLaunchIntentForPackage(bot.testedPackageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) // Clear out any previous instances
        context.startActivity(intent)

        // Here you can wait for your app to load
        afterSetUp()
    }

    protected open fun beforeSetUp() {}

    protected open fun afterSetUp() {}
}
