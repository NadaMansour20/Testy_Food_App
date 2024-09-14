package com.android.food_app.ui.profile

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.android.food_app.base.BaseFragment
import com.android.food_app.ui.auth.login.LoginActivity
import com.android.food_app.ui.main.MainActivity
import com.android.testy_food.R
import com.android.testy_food.databinding.FragmentUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserProfileFragment :BaseFragment<ProfileViewModel,FragmentUserProfileBinding>() {


    var auth=FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databinding.pvm=viewModel

        click()
        viewModel.get_user_data()
        viewModel.update_user_data()
        observe()

    }
    override fun get_layout(): Int {
        return R.layout.fragment_user_profile
    }

    override fun initial_viewModel(): ProfileViewModel {
        return ViewModelProvider(this).get(ProfileViewModel::class.java)
    }


    fun click(){

        databinding.logout.setOnClickListener {

            auth.signOut()
            val intent=Intent(requireContext(),LoginActivity::class.java)
            startActivity(intent)

        }

    }

    fun observe(){
        viewModel.showDilog.observe(viewLifecycleOwner, Observer {massege->

            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setMessage(massege)
            alertDialog.setCancelable(false)
            alertDialog.setPositiveButton("ok"){ dialogInterface: DialogInterface, i: Int ->


                if(massege=="User data updated successfully :)") {
                    //return user data after update
                    viewModel.get_user_data()
                }
                showLoading()

            }

            alertDialog.create().show()
        })

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            if(it){
                showLoading()
            }
            else hideProgressLoading()
        })

    }
    var progress: ProgressDialog?=null
    fun showLoading(){
        progress= ProgressDialog(requireContext())
        progress?.setMessage("Loading...")
        progress?.setCancelable(false)
        progress?.show()

    }
    fun hideProgressLoading(){
        progress?.dismiss()
        progress=null

    }

}