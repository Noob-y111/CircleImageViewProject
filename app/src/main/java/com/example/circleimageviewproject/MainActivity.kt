package com.example.circleimageviewproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.circleimageviewproject.custom.NewImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<NewImageView>(R.id.test).also {
            it.setOnClickListener { _ ->
                Glide.with(this)
                    .load("https://img1.baidu.com/it/u=2725256892,1346976217&fm=26&fmt=auto&gp=0.jpg")
                    .into(it)
            }
        }

        Glide.with(this)
            .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Ftupian.qqjay.com%2Fu%2F2015%2F0615%2F21_15181_5.jpg&refer=http%3A%2F%2Ftupian.qqjay.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1633933463&t=8bf88a53d423bb150a9701de291ace85")
            .into(view)


    }
}