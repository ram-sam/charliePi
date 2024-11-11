package com.example.projeto_integrador

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowInsetsAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


class ProdutoDetalhes : AppCompatActivity() {
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_produto_detalhes)
//
//        val nomeProduto = intent.getStringExtra("NOME_PRODUTO")
//        val descricaoProduto = intent.getStringExtra("DESCRICAO_PRODUTO")
//        val imageProduto = intent.getStringExtra("IMAGE_PRODUTO")
//        val precoProduto = intent.getStringExtra("PRECO_PRODUTO")
//
//        findViewById<TextView>(R.id.txtNomeProduto).text = nomeProduto
//        findViewById<TextView>(R.id.txtDescricaoProduto).text = descricaoProduto
//        findViewById<TextView>(R.id.precoProduto).text = precoProduto
//
//        val editTextQuantidade = findViewById<EditText>(R.id.editQuantidadeDesejada)
//        val btnAdicionarCarrinho = findViewById<Button>(R.id.btnAdicionarAoCarrinho)
//
//
//        val sharedPreferences = getSharedPreferences("Dados", Context.MODE_PRIVATE)
//        val userId = sharedPreferences.getInt("id", 0)
//
//        // Exibindo a imagem
//        val imgProduto = findViewById<ImageView>(R.id.imgProduto)
//        Glide.with(this)
//            .load(imageProduto) // URL da imagem
//            .into(imgProduto)
//
//        findViewById<Button>(R.id.btnAdicionarAoCarrinho).setOnClickListener {
//            val quantidadeDesejada = editTextQuantidade.text.toString().toIntOrNull() ?: 0
//            adicionarAoCarrinho(userId, produtoId, quantidadeDesejada)
//        }
//
//    }
//
//    private fun adicionarAoCarrinho(userId: Int, produtoId: Int, quantidade: Int) {
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://www.thyagoquintas.com.br/CHARLIE/")
//            .addConverterFactory(ScalarsConverterFactory.create())
//            .build()
//
//        val api = retrofit.create(ApiService::class.java)
//        api.adicionarAoCarrinho(userId, produtoId, quantidade).enqueue(object :
//            WindowInsetsAnimation.Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(this@ProdutoDetalhes, response.body() ?: "Sucesso!", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this@ProdutoDetalhes, "Resposta nÃƒÂ£o bem-sucedida", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Toast.makeText(this@ProdutoDetalhes, "Erro na API: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    interface ApiService {
//        @FormUrlEncoded
//        @POST("manter_produto_ao_carrinho/")
//        fun adicionarAoCarrinho(
//            @Field("userId") userId: Int,
//            @Field("produtoId") produtoId: Int,
//            @Field("quantidade") quantidade: Int
//        ): Call<String>
//    }
//}
//
//}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto_detalhes)

        val nomeProduto = intent.getStringExtra("NOME_PRODUTO") ?: "Nome nÃƒÂ£o disponÃƒÂ­vel"
        val descricaoProduto = intent.getStringExtra("DESCRICAO_PRODUTO") ?: "DescriÃƒÂ§ÃƒÂ£o nÃƒÂ£o disponÃƒÂ­vel"
        val produtoId = intent.getIntExtra("ID_PRODUTO", 0)
        val quantidadeDisponivel = intent.getIntExtra("QUANTIDADE_DISPONIVEL", 0)
        val imageProduto = intent.getStringExtra("IMAGE_PRODUTO")

        findViewById<TextView>(R.id.txtNomeProduto).text = nomeProduto
        findViewById<TextView>(R.id.txtDescricaoProduto).text = descricaoProduto
        findViewById<TextView>(R.id.txtQuantidadeDisponivel).text = quantidadeDisponivel.toString()


        val editTextQuantidade = findViewById<EditText>(R.id.editQuantidadeDesejada)
        val btnAdicionarCarrinho = findViewById<Button>(R.id.btnAdicionarAoCarrinho)

        // Exibindo a imagem
        val imgProduto = findViewById<ImageView>(R.id.imgProduto)
        Glide.with(this)
            .load(imageProduto) // URL da imagem
            .into(imgProduto)

        val sharedPreferences = getSharedPreferences("Dados", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("id", 0)


        btnAdicionarCarrinho.setOnClickListener {
            val quantidadeDesejada = editTextQuantidade.text.toString().toIntOrNull() ?: 0
            adicionarAoCarrinho(userId, produtoId, quantidadeDesejada)
        }
    }

    private fun adicionarAoCarrinho(userId: Int, produtoId: Int, quantidade: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.thyagoquintas.com.br/CHARLIE/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)
        api.adicionarAoCarrinho(userId, produtoId, quantidade).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ProdutoDetalhes, response.body() ?: "Sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ProdutoDetalhes, "Resposta nÃƒÂ£o bem-sucedida", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@ProdutoDetalhes, "Erro na API: ${t.message}", Toast.LENGTH_SHORT).show()
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
