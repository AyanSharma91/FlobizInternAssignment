package com.world.flobizassignment.network



import android.graphics.ColorSpace
import com.world.flobizassignment.Model.StackoverflowModel
import retrofit2.Response
import retrofit2.http.*



interface Destination {

  @GET("/2.2/questions?key=ZiXCZbWaOwnDgpVT9Hx8IA((&order=desc&sort=activity&site=stackoverflow")
  suspend fun  getList() : Response<StackoverflowModel>

}