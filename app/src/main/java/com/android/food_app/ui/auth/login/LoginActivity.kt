package com.android.food_app.ui.auth.login

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.food_app.base.BaseActivity
import com.android.food_app.ui.auth.register.RegisterFragment
import com.android.food_app.ui.main.MainActivity
import com.android.testy_food.R
import com.android.testy_food.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task


class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {



     val registerFragment= RegisterFragment()
     lateinit var googleSignInClient: GoogleSignInClient
     lateinit var activityResultLauncher: ActivityResultLauncher<Intent>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databinding.lvm=viewModel

        observe()
        click()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("429098166296-uorcq0riku5p8m47cfobdnteatpb3gob.apps.googleusercontent.com") // Replace with your server client ID
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    handleSignInResult(task)

                }
            }


    }

    override fun get_layout(): Int {
        return R.layout.activity_login

    }

    override fun initial_viewModel(): LoginViewModel {
        return ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    fun observe(){

        viewModel.showDilog.observe(this, Observer {massege->

            val alertDialog = AlertDialog.Builder(this)
                alertDialog.setMessage(massege)
                alertDialog.setCancelable(false)
                alertDialog.setPositiveButton("ok"){ dialogInterface: DialogInterface, i: Int ->

                    if(massege=="Login Successfully :)") {

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    dialogInterface.cancel()

                }

                alertDialog.create().show()
        })
    }

    fun click(){

        databinding.newAccount.setOnClickListener {

            pushFragment(registerFragment)
        }

        databinding.signInGoogle.setOnClickListener {

            signInWithGoogle()
        }
    }
    fun pushFragment(fragment: Fragment){

        supportFragmentManager.beginTransaction().replace(R.id.register_framelayout,fragment).commit()
    }

    fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        activityResultLauncher.launch(signInIntent)
    }

    fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.result ?: throw Exception("Google sign-in failed")
            viewModel.updateUi(account) // Update UI through ViewModel
        } catch (e: Exception) {
            // Handle the error
        }
    }



}