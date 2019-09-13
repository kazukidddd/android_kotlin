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


class SampleAdapter// Provide a suitable constructor (depends on the kind of dataset)
internal constructor(
    private val iNames: ArrayList<SampleModel>, var activity: ListActivity
) : RecyclerView.Adapter<SampleAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    open class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var textView: TextView = v.findViewById(R.id.title_text_view)
        var listIdView: TextView = v.findViewById(R.id.text_list_id)

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.realm_list, parent, false)

        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.text = iNames[position]?.name
        holder.listIdView.text = iNames[position]?.listId.toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return iNames.size
    }
}