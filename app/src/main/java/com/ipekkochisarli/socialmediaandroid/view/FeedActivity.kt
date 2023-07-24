package com.ipekkochisarli.socialmediaandroid.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.ipekkochisarli.socialmediaandroid.R
import com.ipekkochisarli.socialmediaandroid.adapter.FeedRecyclerAdapter
import com.ipekkochisarli.socialmediaandroid.databinding.ActivityFeedBinding
import com.ipekkochisarli.socialmediaandroid.model.Post

class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var postArrayList: ArrayList<Post>
    private lateinit var postAdapter: FeedRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFeedBinding.inflate(layoutInflater)
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        val view = binding.root
        super.onCreate(savedInstanceState)
        // initialize empty array list
        postArrayList = ArrayList()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        setContentView(view)
        getData()
        postAdapter = FeedRecyclerAdapter(postArrayList)
        binding.recyclerView.adapter = postAdapter
    }

    // get data from firebase
    private fun getData() {
        // orderBy -> sort by date
        // we can use where to filter data like where("userEmail","==","ipek@gmail.com")
        db.collection("Posts").orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    if (snapshot != null && !snapshot.isEmpty) {
                        val documents = snapshot.documents
                        // clear array list to prevent duplicate data
                        // we should use clear() method because we are using addSnapshotListener method and it is called every time data changes in firebase
                        postArrayList.clear()
                        for (document in documents) {
                            // cast to string
                            val comment = document.get("comment") as String
                            val userEmail = document.get("userEmail") as String
                            val downloadUrl = document.get("downloadUrl") as String
                            val post = Post(userEmail, comment, downloadUrl)
                            postArrayList.add(post)
                        }
                        // update adapter
                        postAdapter.notifyDataSetChanged()

                    }
                }
            }
    }
    // inflate menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.newPost_menu) {
            // upload image
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)

        } else if (item.itemId == R.id.logOut_menu) {
            // sign out
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // user cannot go back to feed activity so we should finish it
            finish()
            Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
}