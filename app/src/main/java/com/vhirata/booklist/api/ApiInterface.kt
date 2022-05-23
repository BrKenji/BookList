package com.vhirata.booklist.api

import com.vhirata.booklist.model.DoggosItem
import com.vhirata.booklist.model.TodosItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("breeds")
    fun getBreeds(): Call<List<DoggosItem>>

}