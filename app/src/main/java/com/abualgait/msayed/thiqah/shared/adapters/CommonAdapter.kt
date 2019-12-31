package com.abualgait.msayed.thiqah.shared.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abualgait.msayed.thiqah.R
import com.abualgait.msayed.thiqah.shared.data.model.PostPOJO
import com.abualgait.msayed.thiqah.shared.interfaces.IDeleteClickListener
import com.abualgait.msayed.thiqah.shared.interfaces.IEditClickListener

import com.abualgait.msayed.thiqah.shared.interfaces.SimpleItemClickListener
import com.abualgait.msayed.thiqah.shared.viewholders.PostsVH
import com.abualgait.msayed.thiqah.shared.viewholders.RecyclerViewHolder
import java.util.*

class CommonAdapter() : RecyclerView.Adapter<RecyclerViewHolder>() {


    private var mContext: Context? = null
    private lateinit var mList: ArrayList<PostPOJO>


    private val POST: Int = 1

    private lateinit var itemClick: SimpleItemClickListener
    private lateinit var editClick: IEditClickListener
    private lateinit var deleteClick: IDeleteClickListener


    constructor(mContext: Context, mList: ArrayList<PostPOJO>) : this() {
        this.mContext = mContext
        this.mList = mList

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        var viewHolder: RecyclerViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {


            POST -> {
                val view: View = inflater.inflate(R.layout.item_post, parent, false)
                viewHolder = PostsVH(this.mContext!!, view, itemClick, editClick, deleteClick)

            }


        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.onBindView(mList[position], position)

    }

    override fun getItemCount(): Int {
        return mList.size


    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getItemViewType(position: Int): Int {
        return POST
    }

    fun setItemClick(itemClick: SimpleItemClickListener) {
        this.itemClick = itemClick
    }

    fun setEditClick(editClick: IEditClickListener) {
        this.editClick = editClick
    }

    fun setDeleteClick(deleteClick: IDeleteClickListener) {
        this.deleteClick = deleteClick
    }


}