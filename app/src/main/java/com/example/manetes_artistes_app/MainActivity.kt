package com.example.manetes_artistes_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import com.example.manetes_artistes_app.common.NetworkErrorActivity
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity
import com.example.manetes_artistes_app.data.Center
import com.example.manetes_artistes_app.data.getCenters
import com.example.manetes_artistes_app.menus.StickerSelectorActivity
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
        val isWifiConnected = isWifiConnected(this)
        if (!isWifiConnected) {
            val intent = Intent(this, NetworkErrorActivity::class.java)
            startActivity(intent)
            finish()
            println("No wifi connection")
        }else{
            println("Wifi connection")
        }
        // initialize the stats state
        StatsState.initializeCenters(this)
        // initially upload the centers in case of wasnt uploaded by no-wifi
        StatsState.uploadStatsOnFtp(this)

        val btn = findViewById<ImageButton>(R.id.onlyButton)
        val centersList = findViewById<Spinner>(R.id.centersList)
        val centers: List<Center> = getCenters()
        val groupsList = findViewById<Spinner>(R.id.groupsList)

        val centerNames = centers.map { it.center_name }

        val centersAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            centerNames
        )
        centersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        centersList.adapter = centersAdapter

        centersList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val groupsNames = centers[position].groups.map { it.group_name }
                val groupsAdapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_spinner_item,
                    groupsNames
                )
                groupsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                groupsList.adapter = groupsAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        btn.setOnClickListener {
            val intent = Intent(this, StickerSelectorActivity::class.java)
            startActivity(intent)
            User.centerId = centers[centersList.selectedItemPosition].center_id
            User.groupId = centers[centersList.selectedItemPosition].groups[groupsList.selectedItemPosition].group_id
        }
    }
}