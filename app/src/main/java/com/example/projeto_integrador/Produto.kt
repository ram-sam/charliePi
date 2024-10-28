package com.example.projeto_integrador
//
//data class Produto (
//    val PRODUTO_ID: Int,
//    val PRODUTO_NOME: String,
//    val PRODUTO_DESC: String,
//    val PRODUTO_PRECO: Double,
//    val PRODUTO_DESCONTO: Double,
//    val CATEGORIA_ID: Int,
//    val PRODUTO_ATIVO: Boolean,
//    val IMAGEM_URL: String?,
//    val QUANTIDADE_DISPONIVEL: Int?
//)

data class Produto(
    val PRODUTO_ID: Int,
    val PRODUTO_NOME: String,
    val PRODUTO_DESC: String,
    val PRODUTO_PRECO: String,
    val PRODUTO_DESCONTO: String,
    val CATEGORIA_ID: Int,
    val PRODUTO_ATIVO: Int,
    val IMAGEM_URL: String?,
    val QUANTIDADE_DISPONIVEL: Int?
)
