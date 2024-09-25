package com.example.projeto_integrador

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class ListaProdutos : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter



        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_lista_produtos)

            recyclerView = findViewById(R.id.recyclerViewProdutos)
            recyclerView.layoutManager = LinearLayoutManager(this)


            // ConfiguraÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o do Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("https://bb029659-46b5-468a-b231-4542de1636fd-00-3e3u27u8c3u5b.spock.replit.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)

            apiService.getProdutos().enqueue(object : Callback<List<Produto>> {
                override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                    if (response.isSuccessful) {
                        val produtos = response.body() ?: emptyList()
                        adapter = CustomAdapter(produtos)
                        recyclerView.adapter = adapter
                    } else {
                        Log.e("API Error", "Response not successful. Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                    Log.e("API Failure", "Error fetching products", t)
                }
            })
        }


    interface ApiService {
        @GET("/")
        fun getProdutos(): Call<List<Produto>>

    }
}
