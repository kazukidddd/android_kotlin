package com.example.recyclerrealmapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.LinearLayoutManager



class ListActivity : AppCompatActivity() {

    private val adapter: RecyclerView.Adapter<*>? = null
    private lateinit var texts : List<SampleModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        //RecyclerViewをレイアウトから探す
        val recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)

        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager



    }
}
