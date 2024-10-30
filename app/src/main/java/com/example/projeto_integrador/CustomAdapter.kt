package com.example.projeto_integrador
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class CustomAdapter(private val dataSet: List<Produto>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nome: TextView = view.findViewById(R.id.nomeProduto)
        //val descricao: TextView = view.findViewById(R.id.descricaoProduto)
        val valor: TextView = view.findViewById(R.id.valorProduto)
        val imagem: ImageView = view.findViewById(R.id.imgProduto)

        val btnComprar: Button = view.findViewById(R.id.btnComprar)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.itens_produtos, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val produto = dataSet[position]
        viewHolder.nome.text = produto.PRODUTO_NOME
        //viewHolder.descricao.text = produto.PRODUTO_DESC
        viewHolder.valor.text = produto.PRODUTO_PRECO.toString()

        Glide.with(viewHolder.itemView.context)
            .load(produto. IMAGEM_URL)
            .placeholder(R.drawable.ic_launcher_background) // placeholder
            .error(com.google.android.material.R.drawable.mtrl_ic_error) // indica erro
            .into(viewHolder.imagem)

        viewHolder.btnComprar.setOnClickListener {
            val intent = Intent(viewHolder.itemView.context, ProdutoDetalhes::class.java)
            intent.putExtra("NOME_PRODUTO", produto.PRODUTO_NOME)
            intent.putExtra("DESCRICAO_PRODUTO", produto.PRODUTO_DESC)
            intent.putExtra("IMAGE_PRODUTO", produto.IMAGEM_URL)
            intent.putExtra("PRECO_PRODUTO", produto.PRODUTO_PRECO)
            intent.putExtra("QUANTIDADE_DISPONIVEL", produto.QUANTIDADE_DISPONIVEL)
            viewHolder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataSet.size
}
