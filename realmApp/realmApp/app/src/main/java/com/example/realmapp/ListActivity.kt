package com.example.realmapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_main.*

class ListActivity : AppCompatActivity() {

    var textlist = mutableListOf<String>()
    private lateinit var mRealm : Realm
    private lateinit var texts : List<RealmMode>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Realm.init(this)

        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        mRealm = Realm.getInstance(realmConfig)

        texts = mRealm.where(RealmMode::class.java).findAll().sort("listId",Sort.ASCENDING)
        for (text in texts){
            text.name
            textlist.add(text.listId,text.name)
        }

        val listView = ListView(this)
        setContentView(listView)

        val arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, textlist)

        listView.adapter = arrayAdapter

        listView.setOnItemClickListener { _, _, _, id ->
//            update(id.toInt())
            delete(id.toInt())

            val arrayAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, textlist)
            listView.adapter = arrayAdapter
        }

    }
    fun update(id:Int){
        mRealm.executeTransaction {
            mRealm.where(RealmMode::class.java).equalTo("id",id).findFirst()
            textlist.set(id,"こんにちわに")
            var texts = mRealm.where(RealmMode::class.java).equalTo("id",id).findFirst()
            texts?.name = "こんにちわに"
        }
    }

    fun delete(id:Int){
        mRealm.executeTransaction {
            var text = mRealm.where(RealmMode::class.java).findAll().sort("listId", Sort.ASCENDING)
            text.deleteFromRealm(id)
            for (index in id until text.size - 1){
                val obj = text[index]
                obj?.listId = obj?.listId?.minus(1)!!
//                obj?.listId?.let { it - 1 }
            }
            textlist.removeAt(id)
        }
    }
}
