package com.example.projeto_integrador

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConfirmacaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmacao)

        // Obtém o ID do pedido passado pela Intent
        val orderId = intent.getIntExtra("ORDER_ID", -1)

        // Configura o texto na tela
        val confirmationTextView = findViewById<TextView>(R.id.confirmationTextView)
        if (orderId != -1) {
            confirmationTextView.text = "Pedido Realizado\nPedido ID: $orderId"
        } else {
            confirmationTextView.text = "Erro ao realizar pedido."
            Toast.makeText(this, "ID do pedido não encontrado.", Toast.LENGTH_LONG).show()
        }

        // Aguarda 5 segundos e redireciona para a MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@ConfirmacaoActivity, MainActivity::class.java))
            finish()
        }, 5000) // 5000 milissegundos = 5 segundos
    }
}
