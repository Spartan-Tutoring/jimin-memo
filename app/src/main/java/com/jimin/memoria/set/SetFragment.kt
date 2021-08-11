package com.jimin.memoria.set

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.jimin.memoria.BaseFragment
import com.jimin.memoria.data.db.MemoDB
import com.jimin.memoria.data.db.UserDB
import com.jimin.memoria.databinding.FragmentHomeBinding
import com.jimin.memoria.databinding.FragmentSetBinding
import com.jimin.memoria.signin.SigninActivity

class SetFragment () : BaseFragment() {
    private lateinit var binding: FragmentSetBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentSetBinding.inflate(layoutInflater,container,false)
        val view=binding.root

        setUser()
        binding.setLogoutTv.setOnClickListener(this)
        return view
    }
    private fun initRecyclerView() {

    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v) {
            binding.setLogoutTv->logout()
        }
    }

    private fun setUser(){
        val spf:SharedPreferences=requireActivity().getSharedPreferences("memoria",MODE_PRIVATE)
        val token =spf.getInt("token",0)

        if(token!=0) {
            getUser(token)
        }
    }
    private fun getUser(idx:Int) {
        val db: UserDB = Room.databaseBuilder(requireActivity(),UserDB::class.java,"user-db").allowMainThreadQueries().build()
        val user=db.userDao().getUserByIdx(idx)

        user?.let {
            binding.setNameTv.text=it.name
        }
    }

    private fun logout() {
        val spf:SharedPreferences=requireActivity().getSharedPreferences("memoria",MODE_PRIVATE)
        val editor=spf.edit()

        editor.remove("token")
        editor.apply()

        startSigninActivity()
    }

    private fun startSigninActivity() {
        val intent = Intent(requireActivity(), SigninActivity::class.java) //액티비티 이동 위한 객체
        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        requireActivity().finish()

    }
}