package com.example.recyclerrealmapp.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerrealmapp.R
import com.example.recyclerrealmapp.SampleModel
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.recyclerrealmapp.entities.SampleAdapter
import com.example.recyclerrealmapp.usecases.TargetSizeChecker
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.delete
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : AppCompatActivity() {

    //表示用のリスト
    var textlist = ArrayList<SampleModel>()
    private lateinit var texts : RealmResults<SampleModel>
    private lateinit var mRealm : Realm
    private lateinit var recyclerView : RecyclerView
    private lateinit var viewAdapter : RecyclerView.Adapter<*>
    private lateinit var viewManager : RecyclerView.LayoutManager
    private lateinit var itemTouchHelper : ItemTouchHelper

    private lateinit var recyclerViewLayout : LinearLayout
    private var animFlag = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        //戻るボタンを設定
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "List"

        // realm準備
        Realm.init(this)

        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        mRealm = Realm.getInstance(realmConfig)

        createData()

        viewManager = LinearLayoutManager(this)
        viewAdapter = SampleAdapter(textlist)

        //RecyclerViewをレイアウトから探す
        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        // RecyclerView のドラッグ、スワイプ操作に関する設定
        itemTouchHelper = ItemTouchHelper(getRecyclerViewSimpleCallBack())
        itemTouchHelper.attachToRecyclerView(recyclerView)


        recyclerViewLayout = findViewById(R.id.recycler_view_layout)


        // 画面サイズに合わせてレイアウトサイズを決定する。
        setLayoutSize(this)
        recyclerView.adapter = viewAdapter

        fab.setOnClickListener {
            realmDelete()
            setContentView(R.layout.activity_list)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)

        onBackPressed()

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /**
     * realmの情報読み取り
     */
    private fun createData(){
        texts = mRealm.where(SampleModel::class.java).findAll().sort("listId", Sort.ASCENDING)
        for (text in texts){
            text.name
            textlist.add(text)
        }
    }

    // RecyclerView などのサイズを設定する。
    private fun setLayoutSize(activity: Activity){
        // 画面サイズを取得
        var screenSize = TargetSizeChecker.getDisplaySize(activity)

        // 対象 View のサイズに関する値
        var recyclerViewLayoutHeight = (screenSize.y)
        var listHeaderLayoutHeight = (screenSize.y * 0.1).toInt()
        var recyclerViewHeight = recyclerViewLayoutHeight - listHeaderLayoutHeight
        Log.d("heights", recyclerViewHeight.toString())

        // 対象 View にサイズを挿入。
        var recyclerViewLayoutParams = recyclerViewLayout.layoutParams
        recyclerViewLayoutParams.height = recyclerViewLayoutHeight
//        recyclerViewLayout.layoutParams = recyclerViewLayoutParams

        var recyclerViewParams = recyclerView.layoutParams
        recyclerViewParams.height = recyclerViewHeight
        recyclerView.layoutParams = recyclerViewParams
    }

    //recyclerView をドラッグ、スワイプした時に呼び出されるコールバック関数
    private fun getRecyclerViewSimpleCallBack() =
        // 引数で、上下のドラッグ、および左右のスワイプを有効にしている。
        object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            // ドラッグしたとき
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {

                val fromPosition = p1.adapterPosition
                val toPosition = p2.adapterPosition

                // 同じ ViewType アイテムを超える時だけ notifyItemMoved を呼び出す。
                if (p1.itemViewType == p2.itemViewType) {
                    textlist.add(toPosition, textlist.removeAt(fromPosition))
                    viewAdapter.notifyItemMoved(fromPosition, toPosition)
                }

                realmSave()

                return true
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                endDragging(viewHolder)
            }


            //左右にスワイプしたとき
            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                if (p1 == ItemTouchHelper.LEFT) {
                    p0.let {
                        textlist.removeAt(p0.adapterPosition)
                        viewAdapter.notifyItemRemoved(p0.adapterPosition)
                        mRealm.executeTransaction {
                            var text = mRealm.where(SampleModel::class.java).findAll().sort("listId", Sort.ASCENDING)
                            text.deleteFromRealm(p0.layoutPosition)
                            for (index in p0.layoutPosition until text.size) {
                                val obj = text[index]
                                obj?.listId = obj?.listId?.minus(1) ?: 0
//                obj?.listId?.let { it - 1 }
                            }
                        }
                    }
                }else if (p1 == ItemTouchHelper.RIGHT){
                    mRealm.executeTransaction {
                        textlist[p0.adapterPosition].name = "ゴリラ"
                        viewAdapter.notifyItemChanged(p0.adapterPosition)
                        var texts = mRealm.where(SampleModel::class.java).equalTo("listId",p0.layoutPosition).findFirst()
                        texts?.name = "ゴリラ"
                    }
                }

            }

            /*
            * 一部リストアイテム（セクション）はドラッグ・スワイプさせたくないため、以下で制御。
            * https://kotaeta.com/61339696
            * */
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                return super.getMovementFlags(recyclerView, viewHolder)
            }
        }

//    fun closeRecyclerViewLayout(){
//        listHeaderLayout.callOnClick()
//    }
    // リスト画像をタップすると即座にドラッグ開始
    fun startDragging(viewHolder : RecyclerView.ViewHolder){
        itemTouchHelper.startDrag(viewHolder)
        viewHolder.itemView.isPressed = true
        /*
        // ドラッグ開始通知のためにバイブさせる場合。API レベルによって使用できるメソッドが異なるので分岐
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val vibrationEffect = VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        } else {
            vibrator.vibrate(300)
        }
        */
    }

    // imageView から指を離した時
    fun endDragging(viewHolder : RecyclerView.ViewHolder){
        Log.d("endDragging", "now")
        viewHolder.itemView.isPressed = false
    }

    // realmのリストを更新
    fun realmSave(){
        mRealm.executeTransaction {
            var realmTexts = mRealm.where(SampleModel::class.java).findAll().sort("id",Sort.ASCENDING)
            for ((index,text)in textlist.withIndex()){
                realmTexts.find { it.id == text.id }.let {
                    it?.listId = index
                }
            }
        }
    }

    private fun realmDelete(){
        mRealm.executeTransaction {
            mRealm.deleteAll()
            textlist = ArrayList()
        }
    }
}
