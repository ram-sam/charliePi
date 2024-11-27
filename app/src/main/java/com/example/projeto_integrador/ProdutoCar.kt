package com.example.projeto_integrador

import android.os.Parcel
import android.os.Parcelable

class ProdutoCar (
    val PRODUTO_ID: Int,
    val QUANTIDADE_DISPONIVEL: Int,
    val PRODUTO_PRECO: Double
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(PRODUTO_ID)
        parcel.writeInt(QUANTIDADE_DISPONIVEL)
        parcel.writeDouble(PRODUTO_PRECO)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ProdutoCar> {
        override fun createFromParcel(parcel: Parcel): ProdutoCar {
            return ProdutoCar(parcel)
        }

        override fun newArray(size: Int): Array<ProdutoCar?> {
            return arrayOfNulls(size)
        }
    }
}