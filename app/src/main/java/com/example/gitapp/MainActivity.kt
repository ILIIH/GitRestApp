package com.example.gitapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gitapp.di.AppComponent
import com.example.gitapp.di.DaggerAppComponent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}