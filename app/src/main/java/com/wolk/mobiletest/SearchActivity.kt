package com.wolk.mobiletest

//TODO implement RecycleView with FAN to GET repos from user from Github API
// check best practices

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

    private var userLogin :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        search_button.setOnClickListener {

            userLogin = input.text.toString()

            if (Build.VERSION.SDK_INT >= 23)
            {
                if (isOnline())
                    getData()
                else
                    Toast.makeText(this, "No internet connection.", Toast.LENGTH_LONG).show()
            }
            else {
                if (isOnlineAPI23Below())
                    getData()
                else
                    Toast.makeText(this, "No internet connection.", Toast.LENGTH_LONG).show()
            }
        }
    }

    //region Functions

    private fun getData() {

        // GET Data as JSONObject
        AndroidNetworking.get("https://api.github.com/users/$userLogin")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                // SUCCESS
                override fun onResponse(response: JSONObject) {

                    sendData(response)
                }
                // FAILURE
                override fun onError(error: ANError) {

                    Toast.makeText(this@SearchActivity, "Not found.", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun sendData(response: JSONObject) {

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