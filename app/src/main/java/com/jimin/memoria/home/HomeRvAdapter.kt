package com.jimin.memoria.memo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.jimin.memoria.R
import com.jimin.memoria.data.db.MemoDB
import com.jimin.memoria.data.entites.Memo

class MemoRvAdapter(private val context: Context) : RecyclerView.Adapter<MemoRvAdapter.ViewHolder>() {
    val items = ArrayList<Memo>()
    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View,position: Int,memo: Memo)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.item_memo,parent,false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return items.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it,position,items[position])
        }

        holder.ivBookmark.setOnClickListener{
            if(items[position].isBookmark=="N") {
                bookmarkMemo(items[position].idx,"Y")
                items[position].isBookmark="Y"
                holder.ivBookmark.setImageDrawable(context.getDrawable(R.drawable.bookmark_y))
            }
            else if(items[position].isBookmark=="Y"){
                bookmarkMemo(items[position].idx,"N")
                items[position].isBookmark="N"
                holder.ivBookmark.setImageDrawable(context.getDrawable(R.drawable.bookmark_n))
            }

        }

    }
    private fun updateItems(idx: Int,title:String, content:String) {
        val db: MemoDB = Room.databaseBuilder(context, MemoDB::class.java,"memo-db").allowMainThreadQueries().build()
        db.memoDao().updateMemo(idx,title, content)
    }

    private fun bookmarkMemo(idx:Int,isBookmark:String) {
        val db: MemoDB = Room.databaseBuilder(context, MemoDB::class.java,"memo-db").allowMainThreadQueries().build()
        db.memoDao().bookmarkMemo(idx,isBookmark)

    }
    fun addItems(items:ArrayList<Memo>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    fun removeItems(position: Int) {
        this.items.removeAt(position)
        notifyDataSetChanged()
    }
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener=itemClickListener

    }
    inner class ViewHolder(itemView: View?):RecyclerView.ViewHolder(itemView!!) {
        val ivTitle:TextView=itemView?.findViewById(R.id.item_memo_title_tv)!!
        val ivContent:TextView=itemView?.findViewById(R.id.item_memo_content_tv)!!
        val ivBookmark:ImageView=itemView?.findViewById(R.id.item_memo_bookmark_iv)!!

        fun bind(item:Memo) {
//            Glide.with(context)
//                .load(item.profileUrl)
//                .circleCrop()
//                .into(pictureIv)

            ivTitle.text=item.title
            ivContent.text=item.content
            if(item.isBookmark=="Y") {
                ivBookmark.setImageDrawable(context.getDrawable(R.drawable.bookmark_y)!!)
            }
            else {
                ivBookmark.setImageDrawable(context.getDrawable(R.drawable.bookmark_n)!!)
            }


        }
    }
}