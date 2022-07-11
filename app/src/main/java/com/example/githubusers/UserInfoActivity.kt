package com.example.githubusers

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class UserInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val arguments = intent.extras!!
        val id = arguments["id"].toString()
        val login = arguments["login"].toString()
        val avatarUrl = arguments["avatarUrl"].toString()

        val idView: TextView = findViewById(R.id.idOfUserOnCard)
        val loginView: TextView = findViewById(R.id.loginOfUserOnCard)
        val avatarView: ImageView = findViewById(R.id.avatarOfUserOnCard)
        idView.text = getString(R.string.id, id)
        loginView.text = login
        Picasso.get().load(avatarUrl).into(avatarView)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}