package com.example.steamappkot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.steamappkot.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.registerButton.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val confirmPassword = binding.verifPasswordInput.text.toString()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                binding.alertMessage.text = "Veuillez remplir tous les champs"
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                binding.alertMessage.text = "Les mots de passe ne correspondent pas"
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        binding.alertMessage.text = "Inscription échouée"
                    }
                }
        }
    }
}