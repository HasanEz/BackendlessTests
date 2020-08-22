package com.hasanze.backendlesstests

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.local.UserIdStorageFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

     private val APPLICATION_ID = "E4FE8B6C-206D-B845-FF03-26F7EAA42A00"
     private val API_KEY = "39B28387-8018-40A8-9F4D-527EF7848115"
     private val SERVER_URL = "https://api.backendless.com"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Init Backendless
        Backendless.setUrl(SERVER_URL)
        Backendless.initApp(applicationContext, APPLICATION_ID, API_KEY)



        //IF stay LoggedIn is set to TRUE skip login and move to next activity


        Backendless.UserService.isValidLogin(object : AsyncCallback<Boolean>{

            override fun handleResponse(response: Boolean) {

                // if login is valid find User and move to next activity
                if(response){

                    val userId = UserIdStorageFactory.instance().storage.get()

                    Backendless.Data.of(BackendlessUser::class.java).findById(userId,object : AsyncCallback<BackendlessUser>{
                        override fun handleResponse(response: BackendlessUser) {

                            //user found move to next activity
                            Backendless.UserService.setCurrentUser(response)

                            startActivity(Intent(this@MainActivity,SecondActivity::class.java))


                        }

                        override fun handleFault(fault: BackendlessFault) {

                            //user not found : show error message
                            Toast.makeText(applicationContext,"error: ${fault.message}",Toast.LENGTH_SHORT).show()


                        }


                    })


                }


            }

            override fun handleFault(fault: BackendlessFault) {

                //NOT A VALID LOGIN SHOW MESSAGE
                Toast.makeText(applicationContext,"error: ${fault.message}",Toast.LENGTH_SHORT).show()

            }

        })



        btnTest.setOnClickListener {

            //Login Test User
            val user = "test@test.com"
            val password = "1234"


            Backendless.UserService.login(user,password,object :AsyncCallback<BackendlessUser>{


                override fun handleResponse(response: BackendlessUser) {

                    //Login OK move to next Activity

                    startActivity(Intent(this@MainActivity,SecondActivity::class.java))

                }

                override fun handleFault(fault: BackendlessFault) {
                    //Login Fail Show message
                    Toast.makeText(applicationContext,"error: ${fault.message}",Toast.LENGTH_SHORT).show()
                }


            })

        }
    }
}