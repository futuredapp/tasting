package com.thefuntasty.tasting.sample

import com.thefuntasty.tasting.Scenario


open class BaseScenario : Scenario() {

    //TODO START ALL TESTS via terminal using ./gradlew spoonDebug

    override fun beforeSetUp() {
        //TODO configure testing or delete persistent data here
        //if you want to have clean starts of application before every test
        bot.scrollThreshold = 20
        bot.viewTimeout = 10000
    }

    override fun afterSetUp() {
        bot.waitForId(bot.getViewId(R.id.login_button))
    }
}
