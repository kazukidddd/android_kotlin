package com.example.recyclerrealmapp.entities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageView
import com.example.recyclerrealmapp.R
import com.example.recyclerrealmapp.SampleModel
import com.example.recyclerrealmapp.activities.ListActivity
import io.realm.RealmResults

/**
 * アダプタークラス
 */
class SampleAdapter// Provide a suitable constructor (depends on the kind of dataset)
internal constructor(
    private val iNames: ArrayList<SampleModel>
) : RecyclerView.Adapter<SampleAdapter.ViewHolder>() {

    open class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var textView: TextView = v.findViewById(R.id.title_text_view)
        var listIdView: TextView = v.findViewById(R.id.text_list_id)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.realm_list, parent, false)

        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.textView.text = iNames[position]?.name
        holder.listIdView.text = iNames[position]?.listId.toString()
    }


    override fun getItemCount(): Int {
        return iNames.size
    }
}