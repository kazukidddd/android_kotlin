package com.example.realmapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

import io.realm.Realm
import io.realm.RealmConfiguration
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

        texts = mRealm.where(RealmMode::class.java).findAll()
        for (text in texts){
            text.name
            textlist.add(text.id,text.name)
        }

        val listView = ListView(this)
        setContentView(listView)

        val arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, textlist)

        listView.setAdapter(arrayAdapter)

        listView.setOnItemClickListener { parent, view, position, id ->
//            update(id.toInt())
            delete(id.toInt())

            val arrayAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, textlist)
            listView.setAdapter(arrayAdapter)
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
            var text = mRealm.where(RealmMode::class.java).findAll()
            text.deleteAllFromRealm()
            textlist.removeAt(id)
        }
        create()
    }

    fun create() {
        mRealm.executeTransaction {
            var ids = 0

            for (text in textlist) {
                var texts = mRealm.createObject(RealmMode::class.java, ids)
                texts.name = text.toString()
                mRealm.copyToRealm(texts)
                ids += 1
            }
        }
    }
}
