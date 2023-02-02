package com.example.steamappkot

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.steamappkot.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

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
                val id = resources.getIdentifier(R.drawable.warning.toString(), null, null)
                binding.alertMessage.text = "Les mots de passe ne correspondent pas"
                binding.iconClose.setImageDrawable(resources.getDrawable(id, null))
                binding.passwordInput.background = resources.getDrawable(R.drawable.button_password_err_bg, null)
                return@setOnClickListener
            }

            // Create a new user with a first and last name
            val user = hashMapOf(
                "username" to "$username",
                "email" to "$email",
                "password" to "$password",
                "LikedGames" to MutableList<String>(0, {" "}),
                "Wishlist" to  MutableList<String>(0, {" "}),
            )


            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //val user = auth.currentUser
                        // Add a new document with a generated ID
                        db.collection("users")
                            .add(user)
                            .addOnSuccessListener { documentReference ->
                                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }
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