package com.thefuntasty.tasting.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
