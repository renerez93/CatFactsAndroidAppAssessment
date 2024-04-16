package com.example.a1stapp_trying

import retrofit2.http.GET

interface CatFactService {

    @GET("/fact?max_length=120")
    suspend fun getCatFact(): CatFactResponse

}