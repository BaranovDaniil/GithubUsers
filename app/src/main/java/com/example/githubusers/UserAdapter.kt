package com.example.githubusers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class UserAdapter(private val context: Context, private var githubUsers: List<User>,
                  private val onClickListener: OnUserClickListener )
    : RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    interface OnUserClickListener {
        fun onUserClick(user: User, position: Int, context: Context)
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.user_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user : User = githubUsers[position]
        Picasso.get().load(user.avatarUrl).into(holder.avatarView)
        holder.typeView.text = user.type
        holder.loginView.text = user.login

        holder.itemView.setOnClickListener { onClickListener.onUserClick(user, position, context) }
    }

    override fun getItemCount(): Int {
        return githubUsers.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatarView: ImageView = view.findViewById(R.id.avatarOfUser)
        val typeView: TextView = view.findViewById(R.id.typeOfUser)
        val loginView: TextView = view.findViewById(R.id.loginOfUser)
    }
}