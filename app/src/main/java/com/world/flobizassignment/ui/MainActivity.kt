package com.world.flobizassignment.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.world.flobizassignment.FilterRecyclerAdapter
import com.world.flobizassignment.Model.Item
import com.world.flobizassignment.R
import com.world.flobizassignment.RecyclerAdapter
import com.world.flobizassignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() , FilterRecyclerAdapter.OnRecyclerViewItemClick {


    private val viewModel: ListRecyclerViewModel by viewModels()

    lateinit var binding : ActivityMainBinding
    lateinit var layoutManager : LinearLayoutManager
    lateinit var filterLayoutManager : LinearLayoutManager
    lateinit var adapter : RecyclerAdapter
    lateinit var filterAdapter : FilterRecyclerAdapter
    var avgViewCount  : Double = 0.0
    var avgAnswerCount  : Double = 0.0

     var filteredList =  ArrayList<Item>()
    lateinit var filterList : ArrayList<String>

    private lateinit var itemList : ArrayList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this , R.layout.activity_main)

        itemList = ArrayList()
        filterList = ArrayList()

        filterAdapter = FilterRecyclerAdapter(this@MainActivity ,this , filterList)


        getData()

       binding.searchView.clearFocus()
       binding.searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
           override fun onQueryTextSubmit(query: String?): Boolean {
                  return false
           }

           override fun onQueryTextChange(newText: String?): Boolean {
               applyCustomSearch(newText.toString())
               return true
           }
       })

        binding.filterOption.setOnClickListener{
              showDialog()
        }
    }


    private fun updateFilterList() {

        val updatedFilterList = ArrayList<String>()
        for (item in filteredList) {
            for (tags in item.tags) {
                if (!updatedFilterList.contains(tags)) updatedFilterList.add(tags)
            }
        }
        filterList=updatedFilterList
    }

    private fun getData() {
        viewModel.getListData()
        lifecycleScope.launchWhenStarted {

            viewModel.dataState.collect { event ->

                when (event) {

                    is ListRecyclerViewModel.MainStateEvent.Success -> {

                        Log.d("StackoverflowData" , event.result.toString())
                        itemList.addAll(event.result.items)
                        for(item in itemList){
                            avgAnswerCount += item.answerCount
                            avgViewCount+=item.viewCount
                        }
                        binding.avgViewCount.text = (((avgViewCount)/(itemList.size)).toString())
                        binding.avgAnswerCount.text = (((avgAnswerCount)/(itemList.size)).toString())
                        filteredList = itemList
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = RecyclerAdapter(this@MainActivity , itemList)

                        binding.listRecyclerView.layoutManager = layoutManager
                        binding.listRecyclerView.adapter = adapter

                    }
                    is ListRecyclerViewModel.MainStateEvent.Failure -> {
                        Log.d("StackoverflowData", event.errorText.toString())
                    }
                    is ListRecyclerViewModel.MainStateEvent.Loading -> {
                    }
                    else -> Unit
                }

            }
        }
    }

    private fun applyCustomSearch(searchText : String){

        val updatedList = ArrayList<Item>()

        if(searchText.isEmpty()){
            filteredList = itemList
            adapter.filterList(itemList)
            return
        }


        for (item in itemList) {
            if (item.title.toLowerCase().contains(
                    searchText.toLowerCase(),
                    true
                ) || item.owner.displayName.toLowerCase().contains(searchText.toLowerCase(), true)
            ){
                updatedList.add(item)
            }
        }


        filteredList = updatedList
        adapter.filterList(updatedList)
    }

     fun applyFilters(filterField : String){
        var updatedList = ArrayList<Item>()

        if(filterField.isEmpty()){
           adapter.filterList(filteredList)
            return
        }

        for (item in filteredList) {
            if(item.tags.contains(filterField)){
                updatedList.add(item)
            }
        }

        adapter.filterList(updatedList)
    }

    private fun showDialog() {

        val dialog  = Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_layout)

        val filterRecyclerView = dialog.findViewById<RecyclerView>(R.id.filter_recycler_view)

        updateFilterList()

        filterAdapter = FilterRecyclerAdapter(this@MainActivity ,this , filterList)


        filterLayoutManager = LinearLayoutManager(this)

        filterRecyclerView.layoutManager = filterLayoutManager
        filterRecyclerView.adapter = filterAdapter


        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);

    }

    override fun onItemClick(position: Int) {
        applyFilters(filterList[position])
    }


}


