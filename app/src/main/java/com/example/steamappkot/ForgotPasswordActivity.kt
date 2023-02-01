package com.example.steamappkot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.steamappkot.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.alertMessage.setOnClickListener {
            if(binding.alertMessage.text == "Se connecter"){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        binding.forgotPasswordButton.setOnClickListener {
            val email = binding.emailInput.text.toString()

            if (email.isEmpty()) {
                binding.alertMessage.text = "Veuillez remplir tous les champs"
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        binding.alertMessage.text = "Se connecter"
                        Toast.makeText(this, "Un email de réinitialisation a été envoyé", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.alertMessage.text = "L'email n'existe pas"
                    }
                }
        }
    }
}