package com.example.belajarrxjava.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.belajarrxjava.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val observable = Observable.just("A stream of data", "Hello", "RX Java")

        val observer = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.d("RX Observer: ", "Start subscribing")
            }

            override fun onNext(t: String) {
                Log.d("RX Observable Data: ", t)
            }

            override fun onError(e: Throwable) {
                Log.e("RX Error: ", e.message.toString())
            }

            override fun onComplete() {
                Log.d("RX Observer Status: ", "done")
            }
        }

        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)

        Observable
            .just("Another stream of data", "Hello Again", "Using RX Kotlin")
            .subscribe(
                { Log.d("RX Kotlin: ", it) },
                { Log.e("RX Kotlin: ", it.message.toString()) },
                { Log.d("RX Kotlin: ", "Completed") }
            )

        Observable.timer(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("Delayed: ", "I am delayed by 5 seconds")
            }
    }
}