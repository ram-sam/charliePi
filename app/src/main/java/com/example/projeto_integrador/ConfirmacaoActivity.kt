package com.example.projeto_integrador

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConfirmacaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmacao)

        val imageView = findViewById<ImageView>(R.id.logo)
        imageView.setImageResource(R.drawable.logo1)  // Nome da imagem sem a extensão

        val orderId = intent.getIntExtra("ORDER_ID", -1)


        val confirmationTextView = findViewById<TextView>(R.id.confirmationTextView)
        if (orderId != -1) {
            confirmationTextView.text = "Pedido Realizado"
        } else {
            confirmationTextView.text = "Erro ao realizar pedido."
            Toast.makeText(this, "ID do pedido não encontrado.", Toast.LENGTH_LONG).show()
        }

        // Aguarda 5 segundos e redireciona para a MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@ConfirmacaoActivity, MainActivity::class.java))
            finish()
        }, 5000)
    }
}