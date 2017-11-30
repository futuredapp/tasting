package com.thefuntasty.tasting.sample

import com.thefuntasty.tasting.Scenario


open class BaseScenario : Scenario() {

    //TODO START ALL TESTS via terminal using ./gradlew spoonDebug

    override fun getPackageName() = "com.thefuntasty.tasting.sample"

    override fun beforeSetUp() {
        //TODO configure testing or delete persistent data here
        //if you want to have clean starts of application before every test
        botConfig.setscrollThreshold(20)
        botConfig.viewTimeout = 10000
    }

    override fun afterSetUp() {
        bot.waitForId(bot.getViewId(R.id.login_button))
    }
}
