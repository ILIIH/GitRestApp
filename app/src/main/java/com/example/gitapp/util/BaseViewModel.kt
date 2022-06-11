package com.example.gitapp.util

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val mCompositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: () -> Disposable) {
        mCompositeDisposable.add(disposable.invoke())
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }
}
