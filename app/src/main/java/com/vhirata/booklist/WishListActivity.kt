package com.vhirata.booklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vhirata.booklist.adapter.ApiAdapter
import com.vhirata.booklist.api.ApiInterface
import com.vhirata.booklist.model.DoggosItem
import com.vhirata.booklist.model.TodosItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder
import java.util.function.DoubleBinaryOperator

const val DOGGO_URL = "https://api.thedogapi.com/v1/"

class WishListActivity : AppCompatActivity() {

    private lateinit var apiRV: RecyclerView
    private lateinit var breedCountTV: TextView

    private lateinit var apiAdapter: ApiAdapter

    val TAG = "WishListActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_list)

        breedCountTV = findViewById(R.id.breedCountTV)

        apiRV = findViewById(R.id.apiRV)

        // RecyclerView Adapter
        apiRV.layoutManager = LinearLayoutManager(this)
        apiRV.setHasFixedSize(true)

        getBreedsData()

    }

    private fun getBreedsData() {

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(DOGGO_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getBreeds()

        retrofitData.enqueue(object : Callback<List<DoggosItem>?> {
            override fun onResponse(
                call: Call<List<DoggosItem>?>,
                response: Response<List<DoggosItem>?>
            ) {
                val responseBody = response.body()!!

                apiAdapter = ApiAdapter(baseContext, responseBody)
                apiAdapter.notifyDataSetChanged()
                apiRV.adapter = apiAdapter

                breedCountTV.text = "Breeds: " + apiAdapter.itemCount.toString()

            }

            override fun onFailure(call: Call<List<DoggosItem>?>, t: Throwable) {
                Log.d(TAG, "onFailure: " + t.message)
            }

        })
    }

}