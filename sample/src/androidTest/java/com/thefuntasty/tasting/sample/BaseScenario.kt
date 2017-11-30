package com.thefuntasty.tasting.sample

import com.penguin.intergalactic.sample.R

open class BaseScenario : TasteTestingScenario() {

    //TODO START ALL TESTS via terminal using ./gradlew spoonDebug

    override fun getPackageName(): String {
        //TODO specify your app package name here
        return "com.penguin.intergalactic.eatsample"
    }

    override fun beforeSetUp() {
        //TODO configure testing or delete persistent data here
        //if you want to have clean starts of application before every test
        config.setscrollThreshold(20)
        config.viewTimeout = 10000
    }

    override fun afterSetUp() {
        robot.waitForId(robot.getViewId(R.id.login_button))
    }
}
