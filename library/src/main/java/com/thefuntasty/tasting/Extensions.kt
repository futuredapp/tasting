package com.thefuntasty.tasting

import android.support.test.uiautomator.StaleObjectException
import android.support.test.uiautomator.UiObject2

fun UiObject2.tap(bot: Bot){
    if (this != null) {
        try {
            this.click()
        } catch (e: StaleObjectException) {
            this.tap(bot)
        }
    } else {
        bot.takeScreenshot("exception")
        throw TastingException("View not found")
    }
}
