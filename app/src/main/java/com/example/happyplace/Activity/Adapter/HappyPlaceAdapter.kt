package com.example.happyplace.Activity.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.happyplace.Activity.AddHappyPlaceActivity
import com.example.happyplace.Activity.Database.DatabaseHandler
import com.example.happyplace.Activity.MainActivity
import com.example.happyplace.Activity.Models.HappyPlaceModel
import com.example.happyplace.R
import kotlinx.android.synthetic.main.item_happy_place.view.*

open class HappyPlaceAdapter(
    private val context: Context,
    private var list: ArrayList<HappyPlaceModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_happy_place,
                parent,
                false
            )
        )
    }

    fun setonClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        if (holder is MyViewHolder){
            holder.itemView.iv_place_image.setImageURI(Uri.parse(model.image))
            holder.itemView.tvTitle.text = model.name
            holder.itemView.tvDescription.text = model.description
            holder.itemView.tvAddress.text = model.location
            holder.itemView.tvDate.text = model.date

            holder.itemView.setOnClickListener {
                if (onClickListener != null){
                    onClickListener!!.onClick(position, model)
                }
            }
        }
    }
    fun notifyEditItem(activity: Activity, position: Int, requestCode: Int){
        val intent = Intent(context, AddHappyPlaceActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_PLACE_DETAILS, list[position])
        activity.startActivityForResult(intent, requestCode)
        notifyItemChanged(position)
    }
    interface OnClickListener{
        fun onClick(position: Int, model: HappyPlaceModel)
    }
    fun removeAt(position: Int){
     val dbHandler = DatabaseHandler(context)
     val isDeleted = dbHandler.deleteHappyPlace(list[position])
     if (isDeleted > 0){
         list.removeAt(position)
         notifyItemRemoved(position)
     }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}