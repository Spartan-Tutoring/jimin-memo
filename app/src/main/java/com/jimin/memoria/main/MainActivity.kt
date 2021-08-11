package com.jimin.memoria.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jimin.memoria.BaseActivity
import com.jimin.memoria.data.db.UserDB
import com.jimin.memoria.databinding.ActivityMainBinding
import com.jimin.memoria.home.HomeFragment
import com.jimin.memoria.memo.MemoFragment
import com.jimin.memoria.set.SetFragment
import com.jimin.memoria.signup.SignupActivity
import com.jimin.memoria.write.WriteActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    val tabNames = listOf("HOME","MEMO","SET")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainNewTv.setOnClickListener(this)

        val tabLayout:TabLayout=binding.mainTab
        val pagerAdapter=MainPagerAdapter(this)

        pagerAdapter.addFragment(HomeFragment())
        pagerAdapter.addFragment(MemoFragment())
        pagerAdapter.addFragment(SetFragment())

        val viewPager:ViewPager2=binding.mainViewpager

        viewPager.adapter=pagerAdapter

        TabLayoutMediator(tabLayout,viewPager) { tab,position->
            tab.text=tabNames[position]
        }.attach()
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when (v) {
            binding.mainNewTv-> {
                startWriteActivity()
            }
        }
    }

    private fun startWriteActivity() {
        val intent = Intent(this@MainActivity, WriteActivity::class.java) //액티비티 이동 위한 객체
        startActivity(intent)
    }
}