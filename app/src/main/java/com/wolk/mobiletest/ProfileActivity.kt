package com.wolk.mobiletest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val userData: Array<String>? = intent.getStringArrayExtra("data")

        setData(userData)

        // On repo button click
        repositories_button.setOnClickListener {

            val intent = Intent(this, RepositoriesActivity::class.java)
            intent.putExtra("repos_url", userData!![4])
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(data: Array<String>?) {

        // data = {avatar_url, name, login, bio, repos_url}

        // Setting Avatar
        avatar.setImageUrl(data!![0])

        // Setting User Name
        when {
            data[1] == "null" -> username.text = data[2]
            data[1].length < 19 -> username.text = data[1]
            else -> {
                username.text = data[1]
                username.textSize = 25F
            }
        }

        // Setting Description/Bio
        if (data[3] != "null")
            description.text = data[3]
        else
            description.text = "The user has not added a biography."
    }

    override fun onBackPressed() {

        super.onBackPressed()

        finish()
        overridePendingTransition(R.anim.slide_out, R.anim.slide_in)
    }
}