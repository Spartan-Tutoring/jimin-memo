package com.jimin.memoria.signin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.room.Room.databaseBuilder
import com.jimin.memoria.BaseActivity
import com.jimin.memoria.data.db.UserDB
import com.jimin.memoria.databinding.ActivitySigninBinding
import com.jimin.memoria.main.MainActivity
import com.jimin.memoria.signup.SignupActivity

class SigninActivity: BaseActivity() {
    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signinBt.setOnClickListener(this)
        binding.signinSignupBt.setOnClickListener(this)
    }

   override fun onClick(v: View?) {
        super.onClick(v)

        when (v) {
            binding.signinBt -> {
                signIn(binding.signinIdEt.text.toString(),binding.signinPwEt.text.toString())
            }
            binding.signinSignupBt-> {
                startSignupActivity()
            }
        }
    }

    private fun signIn(id:String,pw:String) {
        if(id.isEmpty()||pw.isEmpty()) { //정보가 비어있는지 확인
            Toast.makeText(this,"정보를 입력해 주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val userDB: UserDB = databaseBuilder(this,UserDB::class.java,"user-db").allowMainThreadQueries().build()
        val user = userDB.userDao().getUser(id,pw)

        if(user == null) {
            Toast.makeText(this,"없는 회원정보 입니다",Toast.LENGTH_SHORT).show()
        } else {
            val spf:SharedPreferences=this.getSharedPreferences("memoria",MODE_PRIVATE)
            val editor=spf.edit()

            editor.putInt("token",user.idx)
            editor.putString("name",user.name)
            editor.apply()

            startMainActivity(user.name)
        }
    }

    private fun startMainActivity(name:String) {
        val intent = Intent(this@SigninActivity, MainActivity::class.java) //액티비티 이동 위한 객체
        startActivity(intent)
        finish()
    }

    private fun startSignupActivity() {
        val intent = Intent(this@SigninActivity, SignupActivity::class.java) //액티비티 이동 위한 객체
        startActivity(intent)
    }
}

