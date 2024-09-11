package com.android.food_app.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


abstract class BaseFragment<VM:BaseViewModel,DB: ViewDataBinding> : Fragment() {

    lateinit var databinding:DB
    lateinit var viewModel:VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        viewModel=initial_viewModel()

        // Inflate the layout for this fragment by dataBinding

        databinding=DataBindingUtil.inflate(inflater,get_layout(),container,false)

        return databinding.root
    }


    abstract fun get_layout():Int

    abstract fun initial_viewModel():VM
}