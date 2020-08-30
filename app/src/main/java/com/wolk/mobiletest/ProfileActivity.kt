package com.wolk.mobiletest

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONArray


class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val userData: Array<String>? = intent.getStringArrayExtra("data")

        setData(userData)

        // On repo button click
        repositories_button.setOnClickListener {

            if (internetConnectionValidation()) {
                buttonLock(true)
                val repoUrl = userData!![4]
                getData(repoUrl)
            } else
                Toast.makeText(this, "No internet connection.", Toast.LENGTH_LONG).show()
        }
    }

    //region Functions

    @SuppressLint("SetTextI18n")
    private fun setData(data: Array<String>?) {

        // data = {avatar_url, name, login, bio, repos_url}

        // Setting Avatar
        avatar.setDefaultImageResId(R.drawable.default_avatar);
        avatar.setErrorImageResId(R.drawable.default_avatar)
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

    private fun internetConnectionValidation(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) isOnline() else isOnlineAPI23Below()
    }

    private fun buttonLock(toLock: Boolean) {

        if (toLock) {
            repositories_button.isEnabled = false
            repositories_button.setImageResource(R.drawable.searching)
        } else {
            repositories_button.setImageResource(R.drawable.see_repositories)
            repositories_button.isEnabled = true
        }
    }

    private fun getData(repo_url: String) {

        // GET Data as JSONArray
        AndroidNetworking.get(repo_url)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                // SUCCESS
                override fun onResponse(response: JSONArray) {

                    sendDataToActivity(response)
                    buttonLock(false)
                }

                // FAILURE
                override fun onError(error: ANError) {

                    Toast.makeText(this@ProfileActivity, "Not found.", Toast.LENGTH_SHORT).show()
                    buttonLock(false)
                }
            })
    }

    private fun sendDataToActivity(response: JSONArray) {

        if (response.toString() != "[]") {

            // Assign data to Arrays
            val reposNames: Array<String> = Array(response.length()) { "" }
            val reposDescriptions: Array<String> = Array(response.length()) { "" }
            val reposLanguages: Array<String> = Array(response.length()) { "" }

            for (i in 0 until response.length()) {
                reposNames[i] = response.getJSONObject(i).getString("name")
                reposDescriptions[i] = response.getJSONObject(i).getString("description")
                reposLanguages[i] = response.getJSONObject(i).getString("language")
            }

            // Start profile activity and send data, with a transition
            val intent = Intent(this@ProfileActivity, RepositoriesActivity::class.java)
            intent.putExtra("repos_names", reposNames)
            intent.putExtra("repos_descriptions", reposDescriptions)
            intent.putExtra("repos_languages", reposLanguages)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        } else {
            Toast.makeText(
                this@ProfileActivity,
                "No public repositories found.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isOnline(): Boolean {

        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
        return false
    }

    private fun isOnlineAPI23Below(): Boolean {

        val connectivity = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.allNetworkInfo
        //use getAllNetworks() instead
        for (i in info.indices) if (info[i].state == NetworkInfo.State.CONNECTED) {
            return true
        }
        return false
    }

    override fun onBackPressed() {

        super.onBackPressed()

        finish()
        overridePendingTransition(R.anim.slide_out, R.anim.slide_in)
    }

    //endregion
}

