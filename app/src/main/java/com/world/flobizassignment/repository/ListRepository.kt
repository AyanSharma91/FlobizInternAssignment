package com.world.flobizassignment.repository


import com.world.flobiz_assgnment.sealedClasses.DataState
import com.world.flobizassignment.Model.StackoverflowModel
import com.world.flobizassignment.network.Destination


class ListRepository
constructor(
    var destination: Destination
    )
{

   suspend  fun getListData() : DataState<StackoverflowModel> {
       return try {
           val response = destination.getList()
           val result = response.body()
           if (response.isSuccessful) {
               val dataList = result!!
               DataState.Success(dataList)
           } else {
               DataState.Error(response.message())
           }
       } catch (e: Exception) {
           DataState.Error(e.message ?: "Something Went Wrong")
       }
   }

}