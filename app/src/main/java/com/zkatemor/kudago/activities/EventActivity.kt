package com.zkatemor.kudago.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.zkatemor.kudago.R

class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
    }

    fun onClickArrow(v : View){
        onBackPressed()
    }
}