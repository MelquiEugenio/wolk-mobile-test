package com.wolk.mobiletest

    //TODO implement Github Rest API using FAN library
    // check best practices
    // validation > check if internet is connected

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repositories_button.setOnClickListener{
            startActivity(Intent(this, RepositoriesActivity::class.java))
        }
    }
}