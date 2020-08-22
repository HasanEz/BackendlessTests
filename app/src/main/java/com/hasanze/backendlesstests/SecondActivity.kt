package com.hasanze.backendlesstests

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.backendless.Backendless
import com.backendless.BackendlessUser

class SecondActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


         val user: BackendlessUser? = Backendless.UserService.CurrentUser()




        Log.d("username : ", user?.email.toString())




    }
}