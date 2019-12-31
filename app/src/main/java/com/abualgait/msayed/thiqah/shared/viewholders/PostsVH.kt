package com.abualgait.msayed.thiqah.shared.viewholders


import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import com.abualgait.msayed.thiqah.R
import com.abualgait.msayed.thiqah.shared.data.model.PostPOJO
import com.abualgait.msayed.thiqah.shared.interfaces.IDeleteClickListener
import com.abualgait.msayed.thiqah.shared.interfaces.IEditClickListener
import com.abualgait.msayed.thiqah.shared.interfaces.SimpleItemClickListener


class PostsVH(private var mContext: Context, itemView: View?, private var itemClick: SimpleItemClickListener, private var editClick: IEditClickListener, private var deleteClick: IDeleteClickListener) :
        RecyclerViewHolder(itemView!!) {

    private var postTitle: TextView? = null
    private var postActions: ImageView? = null
    private var popup: PopupMenu? = null
    private var obj: Any? = null


    init {
        postTitle = itemView?.findViewById(R.id.postTitle)
        postActions = itemView?.findViewById(R.id.postActions)

        itemView!!.setOnClickListener { itemClick.onItemClick(this.obj!!) }
    }

    override fun onBindView(`object`: Any, position: Int) {
        obj = `object`
        if (`object` is PostPOJO) {
            val info = `object`
            postTitle?.text = if (!TextUtils.isEmpty(info.title)) info.title else ""
            postActions?.setOnClickListener {
                popup = PopupMenu(mContext, postActions!!)
                popup?.inflate(R.menu.post_actions)
                popup?.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.edit -> {
                            editClick.onEditClick(this.obj!!)
                        }
                        R.id.delete -> {
                            deleteClick.onDeleteClick(this.obj!!)
                        }
                    }
                    false
                }
                popup?.show()
            }
        }


    }


}