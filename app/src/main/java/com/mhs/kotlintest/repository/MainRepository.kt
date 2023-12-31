package com.mhs.kotlintest.repository

import com.mhs.kotlintest.api.ApiService
import com.mhs.kotlintest.utils.DataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {

    //get characterList
    suspend fun getCharacterList(page: Int) = flow {
        emit(DataStatus.loading())
        val result = apiService.getCharacterList(page)
        when (result.code()){
            200 ->{
                emit(DataStatus.success(result.body()))
            }
            400 ->{
                emit(DataStatus.error(result.message()))
            }
            500 ->{
                emit(DataStatus.error(result.message()))
            }
        }
    }.catch {
        emit(DataStatus.error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    //get character details
    suspend fun getCharacterDetails(id: Int) = flow {
        emit(DataStatus.loading())
        val result = apiService.getCharacterDetails(id)
        when (result.code()) {
            200 -> {
                emit(DataStatus.success(result.body()))
            }

            400 -> {
                emit(DataStatus.error(result.message()))
            }

            500 -> {
                emit(DataStatus.error(result.message()))
            }
        }
    }.catch {
        emit(DataStatus.error(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}