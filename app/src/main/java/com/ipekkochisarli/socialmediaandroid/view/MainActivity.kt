package com.ipekkochisarli.socialmediaandroid.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ipekkochisarli.socialmediaandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val view = binding.root
        auth = Firebase.auth
        setContentView(view)

    }

    override fun onStart() {
        super.onStart()
        // check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)
        }
    }

    fun onSignInClicked(view: View) {
        val email = binding.EmailText.text.toString()
        val password = binding.PasswordText.text.toString()
        // check if email and password is empty
        if (email == "" || password == ("")) {
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_LONG).show()
        } else {
            // we should use async task here because it takes time to create user
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                // success
                val intent = Intent(this, FeedActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "Welcome $email", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                // failure
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }

        }
    }

    fun onSignUpClicked(view: View) {
        val email = binding.EmailText.text.toString()
        val password = binding.PasswordText.text.toString()
        // check if email and password is empty
        if (email == ("") || password == ("")) {
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_LONG).show()
        } else {
            // we should use async task here because it takes time to create user
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                // success
                val intent = Intent(this, FeedActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                // failure
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }

        }
    }
}