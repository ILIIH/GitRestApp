package com.example.gitapp.util

import androidx.fragment.app.Fragment
import com.example.gitapp.di.AppComponent
import com.example.gitapp.di.MyApplication

fun Fragment.getAppComponent(): AppComponent = (activity!!.applicationContext as MyApplication).appComponent
