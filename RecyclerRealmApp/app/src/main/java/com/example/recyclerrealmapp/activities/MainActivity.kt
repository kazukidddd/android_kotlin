package com.example.recyclerrealmapp.activities

import android.app.ListActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerrealmapp.R
import com.example.recyclerrealmapp.SampleModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mRealm : Realm
    private var ids : Int = 0
    private var realmId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)

        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        mRealm = Realm.getInstance(realmConfig)


        /**
         * 追加ボタンのクリックリスナー
         */
        button.setOnClickListener {
            mRealm = Realm.getInstance(realmConfig)
            ids = mRealm.where(SampleModel::class.java).findAll().size

            realmId = if (!mRealm.where(SampleModel::class.java).findAll().isEmpty()){
                mRealm.where(SampleModel::class.java).findAll().sort("id", Sort.ASCENDING).last()?.id!! + 1
            }else{
                0
            }

            create()

            val intent = Intent(applicationContext, com.example.recyclerrealmapp.activities.ListActivity::class.java)
            startActivity(intent)
        }

        /**
         * リストへボタンのクリックリスナー
         */
        listButton.setOnClickListener {
            val intent = Intent(applicationContext, com.example.recyclerrealmapp.activities.ListActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        edit_text.text.clear()
    }

    fun create() {
        mRealm.executeTransaction {
            var texts = mRealm.createObject(SampleModel::class.java, realmId)
            if (!edit_text.text.isEmpty()){
                texts.name = edit_text.text.toString()
            }else{
                texts.name = "サヨナライオン"
            }
            texts.listId = ids
            mRealm.copyToRealm(texts)
        }
    }
}