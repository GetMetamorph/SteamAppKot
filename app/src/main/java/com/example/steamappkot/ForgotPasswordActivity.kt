package com.example.steamappkot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        binding.forgotPasswordButton.setOnClickListener {
            val email = binding.emailInput.text.toString()

            if (email.isEmpty()) {
                binding.alertMessage.text = "Veuillez remplir tous les champs"
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        binding.alertMessage.text = "Un email de réinitialisation a été envoyé"
                    } else {
                        binding.alertMessage.text = "L'email n'existe pas"
                    }
                }
        }
    }
}