package com.mhs.kotlintest.api

import com.mhs.kotlintest.response.CharacterDetails
import com.mhs.kotlintest.response.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("people/")
    suspend fun getCharacterList(@Query("page") page: Int): Response<CharacterList>

    @GET("people/{id}/")
    suspend fun getCharacterDetails(@Path("id") id: Int): Response<CharacterDetails>
}