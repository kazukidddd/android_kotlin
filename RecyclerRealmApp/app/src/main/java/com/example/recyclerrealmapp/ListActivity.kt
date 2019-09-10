package com.example.recyclerrealmapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        //RecyclerViewをレイアウトから探す
        val recyclerView = findViewById<RecyclerView>(R.id.timeZoneList)

    }
}
