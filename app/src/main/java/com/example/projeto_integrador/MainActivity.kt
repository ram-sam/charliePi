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

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializando o RecyclerView
        recyclerView = findViewById(R.id.recyclerViewProdutos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configuração do Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.thyagoquintas.com.br/CHARLIE/")  // Certifique-se de que termina com '/'
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // Faz a chamada à API para obter a lista de produtos
        apiService.getProdutos().enqueue(object : Callback<List<Produto>> {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                // Log da chamada da API
                Log.d("API_DEBUG", "onResponse chamada com status: ${response.code()}")

                if (response.isSuccessful) {
                    val produtos = response.body() ?: emptyList()
                    Log.d("API_DEBUG", "Produtos recebidos: $produtos") // Imprime a lista de produtos no console

                    // Inicializa o adapter com a lista de produtos
                    adapter = CustomAdapter(produtos)
                    recyclerView.adapter = adapter
                } else {
                    Log.e("API Error", "Response not successful. Code: ${response.code()}")
                    Log.e("API Error", "Error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                Log.e("API Failure", "Error fetching products", t)
            }
        })
        
    }

    interface ApiService {
        @GET("lista_de_produtos/")  // Ajuste conforme o endpoint correto
        fun getProdutos(): Call<List<Produto>>
    }
}