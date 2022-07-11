package com.example.githubusers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room


class MainActivity : AppCompatActivity() {

    private var githubUsers : List<User> = emptyList()
    private var isDataLoad: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val thread = Thread {
            run() {
                updateGithubUsers("https://api.github.com/users?since=0", this)
                loadData()
                isDataLoad = true
            }
        }
        thread.start()
        while (!isDataLoad){
        }
        val recyclerView = findViewById<RecyclerView>(R.id.githubUsersListView)

        val userClickListener: UserAdapter.OnUserClickListener =
            object : UserAdapter.OnUserClickListener {
                override fun onUserClick(user: User, position: Int, context: Context) {
                    val intent = Intent(context, UserInfoActivity::class.java)
                    intent.putExtra("login", user.login)
                    intent.putExtra("id", user.uid)
                    intent.putExtra("avatarUrl", user.avatarUrl)
                    startActivity(intent)
                }
            }
        // создаем адаптер
        val adapter = UserAdapter(this, githubUsers, userClickListener)
        // устанавливаем для списка адаптер
        recyclerView.adapter = adapter
    }

    private fun loadData() {
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "github-users"
        ).build()
        val userDao = db.userDao()
        githubUsers = userDao.getAll()
        db.close()
    }
}