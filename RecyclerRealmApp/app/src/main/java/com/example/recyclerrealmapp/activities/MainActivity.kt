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

        // realm準備
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
                mRealm.where(SampleModel::class.java).findAll().sort("id", Sort.ASCENDING).last()?.id?.plus(1) ?: 0
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
        /**
         * Main画面に戻る時にテキストを非表示にする
         */
        edit_text.text.clear()
    }

    /**
     * realmに追加
     */
    fun create() {
        mRealm.executeTransaction {
            var texts = mRealm.createObject(SampleModel::class.java, realmId)
            if (!edit_text.text.isEmpty()){
                texts.name = edit_text.text.toString()
            }else{
                // 何も入力されてなければ「サヨナライオン」を登録する
                texts.name = "サヨナライオン"
            }
            texts.listId = ids
            mRealm.copyToRealm(texts)
        }
    }
}