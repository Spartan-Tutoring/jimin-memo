package com.jimin.memoria.main
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jimin.memoria.BaseActivity
import com.jimin.memoria.R

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}