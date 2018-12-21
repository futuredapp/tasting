package com.thefuntasty.tasting.sample

import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SampleScenario : BaseScenario() {

    @Test
    fun login() {
        bot.apply {
            writeById(R.id.login_field, email)
            writeById(R.id.password_field, getRandomString(21))
            tapById(R.id.login_button)

            presentById(R.id.login_check)
            takeScreenshot("loggedIn")
        }
    }

    @Test
    fun updateMessage() {
        bot.apply {
            val message = getRandomString(64)

            writeById(R.id.message_field, message)
            tapById(R.id.fab)

            textInIdEquals(R.id.snackbar_text, message)
            takeScreenshot("messageSent")

            tapByDescription("Open navigation drawer")
            textInIdEquals(R.id.message_view, message)
            takeScreenshot("messageShown")
        }
    }

    @Test
    fun openAbout() {
        bot.apply {
            tapByContainedInDescription("navigation drawer")
            tapByText(getString(R.string.about))
            presentById(R.id.about, R.id.author, R.id.github)
            takeScreenshot("about")
        }
    }
}
