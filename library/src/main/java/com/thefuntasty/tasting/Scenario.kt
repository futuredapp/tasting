package com.thefuntasty.tasting

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

        bot.launchApp()

        // Here you can wait for your app to load
        afterSetUp()
    }

    protected open fun beforeSetUp() {}

    protected open fun afterSetUp() {}
}
