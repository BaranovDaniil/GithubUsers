package com.example.githubusers

import android.content.Context
import androidx.room.Room
import okhttp3.*
import org.json.JSONArray
import java.io.IOException


@Throws(IOException::class)
fun updateGithubUsers(path: String, context: Context){
    val okHttpClient = OkHttpClient()
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "github-users"
    ).build()
    try {
        val request: Request = Request.Builder().url(path).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val userDao = db.userDao()
                    val jsonArray = JSONArray(response.body()!!.string())
                    for (i in 0 until jsonArray.length()) {
                        val userJson = jsonArray.getJSONObject(i)
                        userDao.insertAll(User(
                        uid = userJson.getString("id").toInt(),
                        type = userJson.getString("type"),
                        login = userJson.getString("login"),
                        avatarUrl = userJson.getString("avatar_url")))
                    }
                }
            }
        })
    }
    catch (ex: IOException) {
    }
    finally {
        db.close()
    }
}