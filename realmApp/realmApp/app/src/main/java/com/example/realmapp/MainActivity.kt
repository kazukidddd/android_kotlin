package com.example.realmapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
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

        //Realmの準備
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
            ids = mRealm.where(RealmMode::class.java).findAll().size

            realmId = if (!mRealm.where(RealmMode::class.java).findAll().isEmpty()){
                mRealm.where(RealmMode::class.java).findAll().sort("id",Sort.ASCENDING).last()?.id!! + 1
            }else{
                0
            }

            create()

            val intent = Intent(applicationContext, ListActivity::class.java)
            startActivity(intent)
        }

        /**
         * リストへボタンのクリックリスナー
         */
        listButton.setOnClickListener {
            val intent = Intent(applicationContext, ListActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        edit_text.text.clear()
    }

    /**
     * Realmにデータ追加
     */
    fun create() {
        mRealm.executeTransaction {
            var texts = mRealm.createObject(RealmMode::class.java, realmId)
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
