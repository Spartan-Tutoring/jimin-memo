package com.jimin.memoria.memo

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.gson.Gson
import com.jimin.memoria.BaseFragment
import com.jimin.memoria.data.db.MemoDB
import com.jimin.memoria.data.entites.Memo
import com.jimin.memoria.databinding.FragmentHomeBinding
import com.jimin.memoria.databinding.FragmentMemoBinding
import com.jimin.memoria.write.WriteActivity

class MemoFragment () : BaseFragment() {
    private lateinit var binding: FragmentMemoBinding
    private lateinit var rvAdapter:MemoRvAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentMemoBinding.inflate(layoutInflater,container,false)
        val view=binding.root

        initRecyclerView()

        return view
    }

    override fun onStart() {
        super.onStart()
        val token=getWriterIdx()
        if(token!=0) {
            getMemos(token)
        }
    }

    private fun initRecyclerView() {
        val lm=LinearLayoutManager(requireActivity())
        binding.memoRecyclerview.layoutManager=lm

        rvAdapter=MemoRvAdapter(requireActivity())
        rvAdapter.setItemClickListener(object :MemoRvAdapter.ItemClickListener {
            override fun onClick(view: View,position:Int,memo: Memo) {
                startWriteActivity(memo)
            }
        })
        binding.memoRecyclerview.adapter=rvAdapter

    }

    private fun getMemos(writer:Int){
        val db: MemoDB = Room.databaseBuilder(requireActivity(), MemoDB::class.java,"memo-db").allowMainThreadQueries().build()
        val list=ArrayList(db.memoDao().getMemosByWriter(writer))


        rvAdapter.addItems(list)
    }

   private fun getWriterIdx():Int{
        val spf : SharedPreferences =requireActivity().getSharedPreferences("memoria", AppCompatActivity.MODE_PRIVATE)
        return spf.getInt("token",0)
   }

    private fun startWriteActivity(memo:Memo) {
        val intent = Intent(requireActivity(), WriteActivity::class.java)
        val gson = Gson()
        val json = gson.toJson(memo)

        intent.putExtra("memoria", json)
        startActivity(intent)
    }
}