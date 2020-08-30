package com.wolk.mobiletest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_repositories.*


class RepositoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositories)

        val reposNames: Array<String>? = intent.getStringArrayExtra("repos_names")
        val reposDescriptions: Array<String>? = intent.getStringArrayExtra("repos_descriptions")
        val reposLanguages: Array<String>? = intent.getStringArrayExtra("repos_languages")

        val arrayList = ArrayList<Model>()

        for (i in reposNames!!.indices)
            arrayList.add(Model(reposNames[i], reposDescriptions!![i], reposLanguages!![i]))

        val myAdapter = MyAdapter(arrayList, this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
        overridePendingTransition(R.anim.slide_out, R.anim.slide_in)
    }
}