package com.wolk.mobiletest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class RepositoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.repositories_activity)

        Toast.makeText(this, "Activity working.", Toast.LENGTH_LONG).show()
    }
}