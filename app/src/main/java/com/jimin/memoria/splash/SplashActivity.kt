package com.jimin.memoria.splash

import android.content.Intent
import android.os.Bundle
import com.jimin.memoria.BaseActivity
import com.jimin.memoria.databinding.ActivitySplashBinding
import com.jimin.memoria.signin.SigninActivity
import java.util.*
import kotlin.concurrent.schedule


class SplashActivity: BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()

        Timer().schedule(3000) {
            val intent = Intent(this@SplashActivity, SigninActivity::class.java)

            startActivity(intent)

            finish()
        }


    }
}


