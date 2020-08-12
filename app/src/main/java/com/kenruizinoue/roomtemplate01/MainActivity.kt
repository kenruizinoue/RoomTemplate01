package com.kenruizinoue.roomtemplate01

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var disposable: Disposable? = null

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setup of the DB
        db = AppDatabase.getAppDataBase(context = this)
        userDao = db?.userDao()

        disposable = Observable.fromCallable {
            // io thread
            // by specifying like this, you can avoid providing the userId
            val user = User(firstName = "Ken", lastName = "Ruiz Inoue", age = 28)
            userDao?.insertUser(user)
            // return users as a result
            userDao?.getUsers()
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    // main thread
                    countTextView.text = result?.size.toString()
                }
    }

    // dispose the io task and destroy the db
    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        AppDatabase.destroyDataBase()
    }
}