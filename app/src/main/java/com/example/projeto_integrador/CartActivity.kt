package com.example.projeto_integrador

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class CartActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalTextView: TextView
    private lateinit var goToPaymentButton: Button

    private var total: Double = 0.0
    private var cartItems: MutableList<Produto> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)


        // Configuração do BottomNavigationView
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bn_navegation)

        bottomNavigationView.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.mi_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.mi_car -> {
                    // Navegar para a tela de Perfil
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.mi_profile -> {
                    // Navegar para a tela de Carrinho
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        recyclerView = findViewById(R.id.cartRecyclerView)
        totalTextView = findViewById(R.id.totalTextView)
        goToPaymentButton = findViewById(R.id.goToPaymentButton)


        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchCartItems()

        goToPaymentButton.setOnClickListener {


            val sharedPreferences = getSharedPreferences("Dados", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("id", 0)
            val intent = Intent(this, PaymentActivity::class.java).apply {
                putExtra("TOTAL", total.toString())
                putExtra("USER", userId)  // O ID do usuÃƒÆ’Ã‚Â¡rio deve ser obtido de maneira segura, por exemplo, atravÃƒÆ’Ã‚Â©s de uma sessÃƒÆ’Ã‚Â£o de login com o Shared Preferences
                putParcelableArrayListExtra("PRODUCT_LIST", ArrayList(cartItems))
            }
            startActivity(intent)
        }


    }

    private fun fetchCartItems() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.thyagoquintas.com.br/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val sharedPreferences = getSharedPreferences("Dados", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getInt("id", 0)

        val api = retrofit.create(CartApiService::class.java)
        api.getCartItems(userId = idUsuario).enqueue(object : Callback<List<Produto>> {

            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if (response.isSuccessful) {
                    val cartItems = response.body()?.toMutableList() ?: mutableListOf()
                    val total = cartItems.sumOf { item ->
                        val preco = item.produtoPreco?.toDoubleOrNull() ?: 0.0
                        val quantidade = item.quantidadeDisponivel ?: 0
                        preco * quantidade
                    }
                    totalTextView.text = "Total: R$${String.format("%.2f", total)}"
                    recyclerView.adapter = CartAdapter(cartItems, this@CartActivity) { userId, produtoId ->
                        // Aqui você define o que acontece quando o item for removido
                        // userId e produtoId são passados pelo adapter
                        removerItem(userId, produtoId)  // Exemplo de função de remoção
                    }
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                // Tratamento de exception
            }
        })
    }
    private fun removerItem(userId: Int, produtoId: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.thyagoquintas.com.br/CHARLIE/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)
        api.adicionarAoCarrinho(userId, produtoId, -1).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CartActivity, response.body() ?: "Sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@CartActivity, "Resposta nÃƒÂ£o bem-sucedida", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@CartActivity, "Erro na API: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    interface ApiService {
        @FormUrlEncoded
        @POST("manter_produto_ao_carrinho/")
        fun adicionarAoCarrinho(
            @Field("userId") userId: Int,
            @Field("produtoId") produtoId: Int,
            @Field("quantidade") quantidade: Int
        ): Call<String>
    }
}
