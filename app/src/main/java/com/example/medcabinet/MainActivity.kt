package com.example.medcabinet

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textView)
        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            Toast.makeText(this, "智能药箱管家 - 欢迎使用!", Toast.LENGTH_SHORT).show()
            textView.text = "药品管理功能开发中..."
        }
    }
}
