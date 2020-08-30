package com.wolk.mobiletest

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
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_search.*
import org.json.JSONObject


class SearchActivity : AppCompatActivity() {

    private var userLogin: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // On Search button click
        search_button.setOnClickListener {

            userLogin = input.text.toString()

            if (internetConnectionValidation()) {
                buttonLock(true)
                getData()
            } else
                Toast.makeText(this, "No internet connection.", Toast.LENGTH_LONG).show()
        }
    }

    //region Functions

    private fun buttonLock(toLock: Boolean) {

        if (toLock) {
            search_button.isEnabled = false
            search_button.text = getString(R.string.searching)
        } else {
            search_button.text = getString(R.string.search)
            search_button.isEnabled = true
        }
    }

    private fun internetConnectionValidation(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) isOnline() else isOnlineAPI23Below()
    }

    private fun getData() {

        // GET Data as JSONObject
        AndroidNetworking.get("https://api.github.com/users/$userLogin")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                // SUCCESS
                override fun onResponse(response: JSONObject) {

                    sendDataToActivity(response)
                    buttonLock(false)
                }

                // FAILURE
                override fun onError(error: ANError) {

                    Toast.makeText(this@SearchActivity, "Not found.", Toast.LENGTH_SHORT).show()
                    buttonLock(false)
                }
            })
    }

    private fun sendDataToActivity(response: JSONObject) {

        // Assign JSONObj data to array
        val dataArray: Array<String> = arrayOf(
            response.getString("avatar_url"),
            response.getString("name"),
            response.getString("login"),
            response.getString("bio"),
            response.getString("repos_url")
        )

        // Start profile activity and send data, with a transition
        val intent = Intent(this@SearchActivity, ProfileActivity::class.java)
        intent.putExtra("data", dataArray)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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

    //endregion
}