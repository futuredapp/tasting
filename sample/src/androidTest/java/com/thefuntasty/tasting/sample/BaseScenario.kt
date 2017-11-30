package com.thefuntasty.tasting.sample

import com.thefuntasty.tasting.Scenario


open class BaseScenario : Scenario() {

    //TODO START ALL TESTS via terminal using ./gradlew spoonDebug

    override fun getPackageName(): String {
        //TODO specify your app package name here
        return "com.penguin.intergalactic.eatsample"
    }

    override fun beforeSetUp() {
        //TODO configure testing or delete persistent data here
        //if you want to have clean starts of application before every test
        botConfig.setscrollThreshold(20)
        botConfig.viewTimeout = 10000
    }

    override fun afterSetUp() {
        robot.waitForId(robot.getViewId(R.id.login_button))
    }
}
