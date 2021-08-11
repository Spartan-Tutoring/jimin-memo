package com.jimin.memoria.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.jimin.memoria.BaseFragment
import com.jimin.memoria.data.db.UserDB
import com.jimin.memoria.databinding.FragmentHomeBinding

class HomeFragment() : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentHomeBinding.inflate(layoutInflater,container,false)
        val view=binding.root

        setUser()
        binding.homeIdTv.setOnClickListener(this)
        return view
    }

    private fun setUser(){
        val spf: SharedPreferences =requireActivity().getSharedPreferences("memoria", Context.MODE_PRIVATE)
        val token =spf.getInt("token",0)

        if(token!=0) {
            getUser(token)
        }
    }
    private fun getUser(idx:Int) {
        val db: UserDB = Room.databaseBuilder(requireActivity(),UserDB::class.java,"user-db").allowMainThreadQueries().build()
        val user=db.userDao().getUserByIdx(idx)

        user?.let {
            binding.homeIdTv.text= it.name
            binding.homeFixIdTv.text=it.name
        }
    }
}