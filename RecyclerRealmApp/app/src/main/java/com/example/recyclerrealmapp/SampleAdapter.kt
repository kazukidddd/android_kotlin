package com.example.recyclerrealmapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class SampleAdapter(context: Context,
                    private val onItemClicked: (TimeZone) -> Unit)
    :RecyclerView.Adapter<SampleAdapter.SampleViewHolder>(){
    //レイアウトからビューを生成するinflater
    private val inflater = LayoutInflater.from(context)

    //リサイクラビューで表示するタイムゾーンのリスト
    private val timeZones = TimeZone.getAvailableIDs().map {
            id -> TimeZone.getTimeZone(id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        //Viewを生成する
        val view = inflater.inflate(R.layout.realm_list,parent,false)

        //ViewHolderを作る
        val viewHolder = SampleViewHolder(view)

        //viewをタップしたときの処理
        view.setOnClickListener{
            //アダプター上の位置を得る
            val position = viewHolder.adapterPosition

            //位置をもとに、タイムゾーンを得る
            val timeZone = timeZones[position]

            //コールバックを呼び出す
            onItemClicked(timeZone)
        }
        return viewHolder
    }

    override fun getItemCount() = timeZones.size

    override fun onBindViewHolder(holder: SampleViewHolder,position:Int) {
        //位置に応じたタイムゾーンを得る
        val timeZone = timeZones[position]

        //表示内容を更新する
        holder.timeZone.text = timeZone.id
    }

    //Viewへの参照をもっておくViewHolder
    class SampleViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val timeZone = view.findViewById<TextView>(R.id.timeZone)
    }
}