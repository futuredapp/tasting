package com.penguin.intergalactic.eatsample

import android.support.test.runner.AndroidJUnit4
import com.thefuntasty.tasting.sample.BaseScenario
import com.thefuntasty.tasting.sample.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SampleScenario : BaseScenario() {


    @Test
    fun login() {
        //TODO interact with views
        robot.writeById(robot.getViewId(R.id.login_field), robot.email)
        robot.writeById(robot.getViewId(R.id.password_field), robot.getRandomString(21))
        robot.tapById(robot.getViewId(R.id.login_button))

        //TODO assert actions
        robot.presentById(robot.getViewId(R.id.login_check))
        //TODO take screenshot for your report
        robot.takeScreenshot("loggedIn")
        robot.takeScreenshot("loggedIn2")
    }

    @Test
    fun updateMessage() {
        val message = robot.getRandomString(64)

        robot.writeById(robot.getViewId(R.id.message_field),message)
        robot.tapById(robot.getViewId(R.id.fab))

        robot.textInIdEquals(robot.getViewId(R.id.snackbar_text),message)
        robot.takeScreenshot("messageSent")

        robot.tapByDescription("Open navigation drawer")
        robot.textInIdEquals(robot.getViewId(R.id.message_view),message)
        robot.takeScreenshot("messageShown")
    }

}
