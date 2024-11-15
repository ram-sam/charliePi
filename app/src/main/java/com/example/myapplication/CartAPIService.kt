package com.example.myapplication

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CartApiService {
    @GET("CHARLIE/carrinho_de_compras/getCartItems/index.php")
    fun getCartItems(@Query("userId") userId: Int): Call<List<Produto>>

    @DELETE("CHARLIE/carrinho_de_compras/deleteCartItem/index.php")
    fun deleteCartItem(@Query("produtoId") produtoId: Int, @Query("userId") userId: Int): Call<Void>
}

