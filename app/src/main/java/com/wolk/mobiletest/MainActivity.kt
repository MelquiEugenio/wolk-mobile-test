package com.wolk.mobiletest

//TODO implemente RecycleView with FAN to GET repos from user from Github API
// check best practices
// validation > check if internet is connected > catch and deal with errors
// scroll roll on description
// make size of username adaptable

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private var userLogin :String = "luiz-matias"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()

        // On repo button click
        repositories_button.setOnClickListener {

            // Start repo activity with transition
            startActivity(Intent(this, RepositoriesActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun getData() {

        // GET Data as JSONObject
        AndroidNetworking.get("https://api.github.com/users/$userLogin")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject) {

                    setData(response)
                }

                override fun onError(error: ANError) {

                    username.text = getString(R.string.error)
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun setData(response: JSONObject) {

        // Setting User Name
        username.text = response.getString("name")

        // Setting Description/Bio
        if (response.getString("bio") != "null")
            description.text = response.getString("bio")
        else
            description.text = "The user has not added a biography."

        // Setting Avatar
        avatar.setImageUrl(response.getString("avatar_url"))

        repositories_button.visibility = View.VISIBLE
    }
}