package com.example.recyclerrealmapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerrealmapp.R
import com.example.recyclerrealmapp.SampleModel
import androidx.recyclerview.widget.ItemTouchHelper
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort


class ListActivity : AppCompatActivity() {

    var textlist = mutableListOf<String>()
    private val adapter: RecyclerView.Adapter<*>? = null
    private lateinit var texts : List<SampleModel>
    private lateinit var mRealm : Realm
    private lateinit var recyclerView : RecyclerView
    private lateinit var viewAdapter : RecyclerView.Adapter<*>
    private lateinit var viewManager : RecyclerView.LayoutManager
    private lateinit var itemTouchHelper : ItemTouchHelper

    private lateinit var recyclerViewLayout : LinearLayout
    private lateinit var listHeaderLayout : RelativeLayout
    private var animFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        supportActionBar?.hide()

        Realm.init(this)

        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        mRealm = Realm.getInstance(realmConfig)

        createData()

        viewManager = LinearLayoutManager(this)
        viewAdapter = SampleAdapter(texts,this)

        //RecyclerViewをレイアウトから探す
        val recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)

        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager



    }

    private fun createData(){
        texts = mRealm.where(SampleModel::class.java).findAll().sort("listId", Sort.ASCENDING)
        for (text in texts){
            text.name
            textlist.add(text.listId,text.name)
        }
    }
}
