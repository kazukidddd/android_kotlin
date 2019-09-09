package com.example.realmapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mRealm : Realm
    var ids :Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)

        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        mRealm = Realm.getInstance(realmConfig)

        button.setOnClickListener {
            mRealm = Realm.getInstance(realmConfig)
            ids = mRealm.where(RealmMode::class.java).findAll().size
            if (!edit_text.text.isEmpty()){
                create()
            }else{
                createLion()
            }
            val intent = Intent(applicationContext, ListActivity::class.java)
            startActivity(intent)
        }

    }

    fun create() {
        mRealm.executeTransaction {
            var texts = mRealm.createObject(RealmMode::class.java, ids)
            texts.name = edit_text.text.toString()
            mRealm.copyToRealm(texts)
            edit_text.text.clear()
        }
    }

    fun createLion(){
        mRealm.executeTransaction {
            var texts = mRealm.createObject(RealmMode::class.java, ids)
            texts.name = "サヨナライオン"
            mRealm.copyToRealm(texts)
            edit_text.text.clear()
        }
    }

}
