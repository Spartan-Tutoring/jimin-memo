package com.jimin.memoria.write

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.jimin.memoria.BaseActivity
import com.jimin.memoria.data.db.MemoDB
import com.jimin.memoria.data.entites.Memo
import com.jimin.memoria.databinding.ActivitySigninBinding
import com.jimin.memoria.databinding.ActivityWriteBinding
import com.jimin.memoria.memo.MemoRvAdapter

class WriteActivity:BaseActivity() {

    private lateinit var binding: ActivityWriteBinding
    private lateinit var rvAdapter: MemoRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("memoria")) {
            val gson = Gson()
            val json = intent.getStringExtra("memoria")
            val memo = gson.fromJson(json, Memo::class.java)
            setMemo(memo)

            binding.writeDeleteTv.setOnClickListener {
                removeMemo(memo.idx)

            }
        }

        binding.writeSaveTv.setOnClickListener(this)
        binding.writeArrowIv.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v) {
            binding.writeArrowIv ->finish()
            binding.writeSaveTv -> write()
        }
    }

    private fun write() {
        val title=binding.writeTitleEt.text.toString()
        val content=binding.writeContentEt.text.toString()

        val spf : SharedPreferences=getSharedPreferences("memoria", MODE_PRIVATE)
        val token = spf.getInt("token",0)

        if(token != 0) {
           val memo= Memo(title=title,content=content,writer = token)

           val db:MemoDB= Room.databaseBuilder(this,MemoDB::class.java,"memo-db").allowMainThreadQueries().build()
            db.memoDao().writeMemo(memo)

            val memos=db.memoDao().getMemos()
            for (_memo in memos) {
                Log.d(
                    "memo-db",
                    "\"idx: ${_memo.idx},title: ${_memo.title},content: ${_memo.content}, bookmark:${_memo.isBookmark}, writer:${_memo.writer}\""
                )
            }
            finish()
        }

    }

    private fun setMemo(memo: Memo) {
        binding.writeTitleEt.setText(memo.title)
        binding.writeContentEt.setText(memo.content)

    }

    private fun removeMemo(idx:Int) {
        val db:MemoDB= Room.databaseBuilder(this,MemoDB::class.java,"memo-db").allowMainThreadQueries().build()
        db.memoDao().removeMemo(idx)

        finish()
    }
}