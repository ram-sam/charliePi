package com.example.projeto_integrador

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Produto(
    @SerializedName("PRODUTO_ID") val produtoId: Int,
    @SerializedName("PRODUTO_NOME") val produtoNome: String?,
    @SerializedName("PRODUTO_DESC") val produtoDesc: String?,
    @SerializedName("PRODUTO_PRECO") val produtoPreco: String?,
    @SerializedName("PRODUTO_DESCONTO") val produtoDesconto: String?,
    @SerializedName("CATEGORIA_ID") val categoriaId: Int?,
    @SerializedName("PRODUTO_ATIVO") val produtoAtivo: Int?,
    @SerializedName("IMAGEM_URL") val imagemUrl: String?,
    @SerializedName("QUANTIDADE_DISPONIVEL") val quantidadeDisponivel: Int?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(produtoId)
        parcel.writeString(produtoNome)
        parcel.writeString(produtoDesc)
        parcel.writeString(produtoPreco)
        parcel.writeString(produtoDesconto)
        parcel.writeValue(categoriaId)
        parcel.writeValue(produtoAtivo)
        parcel.writeString(imagemUrl)
        parcel.writeValue(quantidadeDisponivel)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Produto> {
        override fun createFromParcel(parcel: Parcel): Produto {
            return Produto(parcel)
        }

        override fun newArray(size: Int): Array<Produto?> {
            return arrayOfNulls(size)
        }
    }
}