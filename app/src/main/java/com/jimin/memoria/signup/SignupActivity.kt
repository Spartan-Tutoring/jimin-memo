package com.jimin.memoria.signup


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.room.Room
import com.jimin.memoria.BaseActivity
import com.jimin.memoria.data.db.UserDB
import com.jimin.memoria.data.entites.User
import com.jimin.memoria.databinding.ActivitySignupBinding
import com.jimin.memoria.signin.SigninActivity


class SignupActivity: BaseActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupSignupBt.setOnClickListener(this)
        binding.signupArrowIv.setOnClickListener(this)
    }

    override fun onRestart() {
        super.onRestart()
        binding.signupNameEt.setText("")
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when(v) {
            binding.signupSignupBt -> {
                if(binding.signupPwEt.text.toString().equals(binding.signupPwCheckEt.text.toString())) {
                    signUp(
                        binding.signupIdEt.text.toString(),
                        binding.signupPwEt.text.toString(),
                        binding.signupNameEt.text.toString()
                    )

                } else {
                    Toast.makeText(this,"비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                    return
                }
            }
            binding.signupArrowIv -> {
                startSigninActivity()
            }
        }
    }

    private fun signUp(id : String, pw : String, name : String) {
        if(id.isEmpty()||pw.isEmpty()||name.isEmpty()) { //정보가 비어있는지 확인
            Toast.makeText(this,"정보를 입력해 주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val userDB: UserDB =Room.databaseBuilder(this,UserDB::class.java,"user-db").allowMainThreadQueries().build()
        val alreadyUser = userDB.userDao().getUserById(id)

        if(alreadyUser===null) {
            val user = User(id, pw, name)
            userDB.userDao().insertUser(user)

            val users = userDB.userDao().getUsers()
            for (user in users) {
                Log.d(
                    "user-db",
                    "\"idx: ${user.idx},userId: ${user.id},userPw: ${user.pw}, userName:${user.name}\""
                )
            }
            showDialog("가입에 성공했습니다")
            startSigninActivity()
        }
        else {
            binding.signupIdTl.error="이미 존재하는 회원입니다."

        }

    }

    private fun startSigninActivity() {
        val intent = Intent(this, SigninActivity::class.java) //액티비티 이동 위한 객체
        startActivity(intent)
        finish()
    }

    override fun onOKClicked() {
        super.onOKClicked()
        finish()
    }
}