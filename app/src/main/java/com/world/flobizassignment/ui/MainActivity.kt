package com.world.flobizassignment.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.world.flobizassignment.R

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val viewModel: ListRecyclerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel.getListData()



        lifecycleScope.launchWhenStarted {

            viewModel.dataState.collect { event ->

                when (event) {

                    is ListRecyclerViewModel.MainStateEvent.Success -> {
                           Log.d("StackoverflowData" , event.result.toString())
                    }
                    is ListRecyclerViewModel.MainStateEvent.Failure -> {
                        Log.d("StackoverflowData" , event.errorText.toString())
                    }
                    is ListRecyclerViewModel.MainStateEvent.Loading -> {
                    }
                    else -> Unit
                }

            }
        }


    }
}