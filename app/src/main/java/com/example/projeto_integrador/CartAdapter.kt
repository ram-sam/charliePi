package com.example.projeto_integrador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CartAdapter(
    private val items: MutableList<Produto>,
    private val context: Context,
    private val removerItemCallback: (Int, Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private val filteredItems = mutableListOf<Produto>()

    init {
        updateFilteredItems()  // Atualiza a lista filtrada no início
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.productNameTextView)
        val productPrice: TextView = view.findViewById(R.id.productPriceTextView)
        val productQuantity: TextView = view.findViewById(R.id.productQuantityTextView)
        val productImage: ImageView = view.findViewById(R.id.productImageView)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detalhes_carrinho, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredItems[position]

        holder.productName.text = item.produtoNome
        holder.productPrice.text = "R$${item.produtoPreco}"
        holder.productQuantity.text = "Qtd: ${item.quantidadeDisponivel}"
        Glide.with(context).load(item.imagemUrl).into(holder.productImage)

        holder.deleteButton.setOnClickListener {
            val sharedPreferences = context.getSharedPreferences("Dados", Context.MODE_PRIVATE)
            val idUsuario = sharedPreferences.getInt("id", 0)

            // Chama a função para remover o item
            removerItemCallback(idUsuario, item.produtoId)

            // Atualiza a lista
            items.remove(item)
            updateFilteredItems()  // Atualiza a lista filtrada
            notifyItemRemoved(position)  // Notifica a remoção no RecyclerView
        }
    }

    override fun getItemCount(): Int {
        return filteredItems.size
    }

    // Função para atualizar a lista filtrada
    private fun updateFilteredItems() {
        filteredItems.clear()
        items.forEach { item ->
            if (item.quantidadeDisponivel != null && item.quantidadeDisponivel!! > 0) {
                filteredItems.add(item)
            }
        }
        notifyDataSetChanged()  // Atualiza a UI
    }
}

