package com.example.pxabay_api_project

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteService{

    @GET("?key=39787025-e772bd417c90068a2c8d79ce3")
    suspend fun getQuotes(@Query("page") page: Int) : Response<QuoteList>

}