package com.jimin.memoria.main
import android.os.Bundle
import com.jimin.memoria.BaseActivity
import com.jimin.memoria.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    val tabNames = listof("HOME","MEMO","SET")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}