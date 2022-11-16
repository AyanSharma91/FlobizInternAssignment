package com.world.flobizassignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.world.flobiz_assgnment.sealedClasses.DataState
import com.world.flobizassignment.Model.StackoverflowModel
import com.world.flobizassignment.repository.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListRecyclerViewModel
@Inject
constructor(
    var repository: ListRepository
) : ViewModel()
{

    private  val _dataState = MutableStateFlow<MainStateEvent>(MainStateEvent.None)
    var dataState : StateFlow<MainStateEvent> = _dataState


    fun getListData() {


        viewModelScope.launch {
            _dataState.value = MainStateEvent.Loading
            when (val dataDetails = repository.getListData()) {
                is DataState.Error -> _dataState.value =
                    MainStateEvent.Failure(dataDetails.message!!)
                is DataState.Success -> {
                    _dataState.value = MainStateEvent.Success(dataDetails.data!!)
                }
            }
        }
    }



    //Just a separate class to handle the result in view Model
    sealed class MainStateEvent {

        class Success(val result : StackoverflowModel) : MainStateEvent()
        class Failure(val errorText: String) : MainStateEvent()
        object Loading : MainStateEvent()
        object None : MainStateEvent()
    }
}