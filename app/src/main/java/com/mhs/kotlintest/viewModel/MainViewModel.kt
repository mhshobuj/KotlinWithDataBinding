package com.mhs.kotlintest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhs.kotlintest.repository.MainRepository
import com.mhs.kotlintest.response.CharacterDetails
import com.mhs.kotlintest.response.CharacterList
import com.mhs.kotlintest.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _characterList =MutableLiveData<DataStatus<CharacterList>> ()

    val characterList: LiveData<DataStatus<CharacterList>> get() = _characterList

    fun getCharacterList(page: Int) =viewModelScope.launch {
        repository.getCharacterList(page).collect{
            _characterList.value = it
        }
    }

    private val _characterDetails = MutableLiveData<DataStatus<CharacterDetails>>()
    val characterDetails: LiveData<DataStatus<CharacterDetails>> get() = _characterDetails

    fun getCharacterDetails(id: Int) = viewModelScope.launch {
        repository.getCharacterDetails(id).collect {
            _characterDetails.value = it
        }
    }
}