package com.world.flobiz_assgnment.sealedClasses

sealed class DataState<T>(val data : T? , val message : String?) {

    class  Success<T>(data : T) : DataState<T>(data , null)

    class Error<T>(message: String) : DataState<T>(null , message)

}