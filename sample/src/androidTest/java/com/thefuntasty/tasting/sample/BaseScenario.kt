package com.thefuntasty.tasting.sample

import com.thefuntasty.tasting.Scenario

open class BaseScenario : Scenario() {

    override fun beforeSetUp() {
        bot.scrollThreshold = 20
        bot.viewTimeout = 10000
    }

    override fun afterSetUp() {
        bot.waitForId(R.id.login_button)
    }
}
