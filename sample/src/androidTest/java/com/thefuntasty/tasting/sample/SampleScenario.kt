package com.thefuntasty.tasting.sample

import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SampleScenario : BaseScenario() {

    @Test
    fun login() {
        bot.writeById(R.id.login_field, bot.email)
        bot.writeById(R.id.password_field, bot.getRandomString(21))
        bot.tapById(R.id.login_button)

        bot.presentById(R.id.login_check)
        bot.takeScreenshot("loggedIn")
    }

    @Test
    fun updateMessage() {
        val message = bot.getRandomString(64)

        bot.writeById(R.id.message_field, message)
        bot.tapById(R.id.fab)

        bot.textInIdEquals(R.id.snackbar_text, message)
        bot.takeScreenshot("messageSent")

        bot.tapByDescription("Open navigation drawer")
        bot.textInIdEquals(R.id.message_view, message)
        bot.takeScreenshot("messageShown")
    }
}
