package com.example.projeto_integrador

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class ProdutoDetalhes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto_detalhes)

        val nomeProduto = intent.getStringExtra("NOME_PRODUTO")
        val descricaoProduto = intent.getStringExtra("DESCRICAO_PRODUTO")
        val imageProduto = intent.getStringExtra("IMAGE_PRODUTO")
        val precoProduto = intent.getStringExtra("PRECO_PRODUTO")

        findViewById<TextView>(R.id.txtNomeProduto).text = nomeProduto
        findViewById<TextView>(R.id.txtDescricaoProduto).text = descricaoProduto
        findViewById<TextView>(R.id.precoProduto).text = precoProduto


        // Exibindo a imagem
        val imgProduto = findViewById<ImageView>(R.id.imgProduto)
        Glide.with(this)
            .load(imageProduto) // URL da imagem
            .into(imgProduto)

        findViewById<Button>(R.id.btnAdicionarAoCarrinho).setOnClickListener {
            // TODO ADICIONAR AO CARRINHO
        }
    }
}