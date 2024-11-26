package com.example.manetes_artistes_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import com.example.manetes_artistes_app.common.NetworkErrorActivity
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity
import com.example.manetes_artistes_app.data.Center
import com.example.manetes_artistes_app.data.getCenters
import com.example.manetes_artistes_app.menus.StickerSelectorActivity
import com.example.manetes_artistes_app.stats.StatsState
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
            println("No wifi connection")
        }else{
            println("Wifi connection")
        }
        // initialize the stats state
        StatsState.initializeCenters(this)
        // initially upload the centers in case of wasnt uploaded by no-wifi
        StatsState.uploadStatsOnFtp(this)
        val btn = findViewById<ImageButton>(R.id.onlyButton)
        val centers: List<Center> = getCenters()

        btn.setOnClickListener {
            val intent = Intent(this, StickerSelectorActivity::class.java)
            startActivity(intent)
        }
    }
}