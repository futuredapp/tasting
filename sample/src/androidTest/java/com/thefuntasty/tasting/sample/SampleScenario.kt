package com.thefuntasty.tasting.sample

import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SampleScenario : BaseScenario() {

    @Test
    fun login() {
        bot.writeById(bot.getViewId(R.id.login_field), bot.email)
        bot.writeById(bot.getViewId(R.id.password_field), bot.getRandomString(21))
        bot.tapById(bot.getViewId(R.id.login_button))

        bot.presentById(bot.getViewId(R.id.login_check))
        bot.takeScreenshot("loggedIn")
    }

    @Test
    fun updateMessage() {
        val message = bot.getRandomString(64)

        bot.writeById(bot.getViewId(R.id.message_field), message)
        bot.tapById(bot.getViewId(R.id.fab))

        bot.textInIdEquals(bot.getViewId(R.id.snackbar_text), message)
        bot.takeScreenshot("messageSent")

        bot.tapByDescription("Open navigation drawer")
        bot.textInIdEquals(bot.getViewId(R.id.message_view), message)
        bot.takeScreenshot("messageShown")
    }
}
