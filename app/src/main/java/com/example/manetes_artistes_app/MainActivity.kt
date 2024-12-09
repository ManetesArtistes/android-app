package com.example.manetes_artistes_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import com.example.manetes_artistes_app.common.NetworkErrorActivity
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity
import com.example.manetes_artistes_app.data.Center
import com.example.manetes_artistes_app.data.getCenters
import com.example.manetes_artistes_app.menus.activities.StickerSelectorActivity
import com.example.manetes_artistes_app.stats.StatsState
import com.example.manetes_artistes_app.user.User
import com.example.manetes_artistes_app.utils.isWifiConnected

class MainActivity : ImmersiveCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // ensure device has wifi connection
        if (!isWifiConnected(this)) {
            val intent = Intent(this, NetworkErrorActivity::class.java)
            startActivity(intent)
            finish()
        }

        // initialize the stats state
        StatsState.initializeCenters(this)
        // initially upload the centers in case of wasnt uploaded by no-wifi
        StatsState.uploadStatsOnFtp(this)

        val centersList = findViewById<Spinner>(R.id.centersList)
        val groupsList = findViewById<Spinner>(R.id.groupsList)
        val centers: List<Center> = getCenters()

        setupSpinnersLists(centers, centersList, groupsList)
        setupButton(centers, centersList, groupsList)
    }

    private fun setupSpinnersLists(centers: List<Center>, centersList: Spinner, groupsList: Spinner){
        centersList.adapter = centersAdapter(centers.map { it.center_name })
        centersList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                groupsList.adapter = groupsAdapter(centers, position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setupButton(centers: List<Center>, centersList: Spinner, groupsList: Spinner){
        val button = findViewById<TextView>(R.id.onlyButton)

        button.text="ACCEDIR"
        button.setOnClickListener {
            reproduceSound(R.raw.activity)
            val intent = Intent(this, StickerSelectorActivity::class.java)
            startActivity(intent)
            User.centerId = centers[centersList.selectedItemPosition].center_id
            User.groupId = centers[centersList.selectedItemPosition].groups[groupsList.selectedItemPosition].group_id
            finish()
        }
    }

    private fun centersAdapter(centersNames: List<String>): ArrayAdapter<String> {
        val centersAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            centersNames
        )
        centersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return centersAdapter
    }

    private fun groupsAdapter(centers: List<Center>, position: Int): ArrayAdapter<String> {
        val groupsNames = centers[position].groups.map { it.group_name }
        val groupsAdapter = ArrayAdapter(
            this@MainActivity,
            android.R.layout.simple_spinner_item,
            groupsNames
        )
        groupsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return groupsAdapter
    }


}