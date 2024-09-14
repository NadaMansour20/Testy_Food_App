package com.android.food_app.ui.auth.register

import android.content.DialogInterface
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.android.food_app.base.BaseFragment
import com.android.food_app.ui.auth.login.LoginActivity
import com.android.testy_food.R
import com.android.testy_food.databinding.FragmentRegisterBinding

class RegisterFragment :BaseFragment<RegisterViewModel, FragmentRegisterBinding>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databinding.rvm=viewModel
        observe()


    }

    override fun get_layout(): Int {
        return R.layout.fragment_register
    }

    override fun initial_viewModel(): RegisterViewModel {
        return ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    fun observe(){

        viewModel.showDilog.observe(viewLifecycleOwner, Observer {massege->

                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.setMessage(massege)
                alertDialog.setCancelable(false)
                alertDialog.setPositiveButton("ok"){ dialogInterface: DialogInterface, i: Int ->

                    if(massege=="Register Successfully :)") {

                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                    }
                    dialogInterface.cancel()
                }

                alertDialog.create().show()

        })
    }


}