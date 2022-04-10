package com.alexcutovoi.githubrepos.login.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexcutovoi.githubrepos.R
import com.alexcutovoi.githubrepos.login.domain.model.Repository
import com.bumptech.glide.Glide

class GithubRepositoriesAdapter(private val context: Context, newRepositories: MutableList<Repository>, private val recyclerView: RecyclerView? = null, private var callback: (() -> Unit)? = null) : RecyclerView.Adapter<GithubRepositoriesAdapter.RepositoryInfoViewHolder>() {
    var currentPage = 1
        private set

    private var repositories = mutableListOf<Repository>()
    private var isLimitReached = false

    init {
        recyclerView?.let{ it ->
            insertScrollListener(it)
        }
        repositories = newRepositories
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryInfoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.repository_layout, parent, false)
        return RepositoryInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryInfoViewHolder, position: Int) {
        holder.repositoryNameTextView.text = repositories[position].repositoryName
        holder.forksCountTextView.text = repositories[position].forks.toString()
        holder.starsCountTextView.text = repositories[position].stargazersCount.toString()
        holder.profileNameTextView.text = repositories[position].owner.name
        Glide.with(context).load(repositories[position].owner.avatarImageUrl).into(holder.profileView)
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    inner class RepositoryInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var starsCountTextView: TextView = itemView.findViewById(R.id.stars_count_text_view)
        var forksCountTextView: TextView = itemView.findViewById(R.id.forks_count_text_view)
        var repositoryNameTextView: TextView = itemView.findViewById(R.id.repository_name_text_view)
        var profileNameTextView: TextView = itemView.findViewById(R.id.profile_name_text_view)
        var profileView: AppCompatImageView = itemView.findViewById(R.id.profile_image_view)
    }

    private fun insertScrollListener(targetRecyclerView: RecyclerView) {
        targetRecyclerView.layoutManager = LinearLayoutManager(context)
        targetRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                layoutManager.let {
                    if(!isLimitReached && layoutManager.itemCount <= layoutManager.findLastCompletelyVisibleItemPosition()+1) {
                        isLimitReached = !isLimitReached
                        ++currentPage
                        callback?.invoke()
                    }
                }
            }
        })
    }

    fun addItems(newRepositories: List<Repository>) {
        repositories.addAll(newRepositories)
        isLimitReached = false
        notifyItemRangeChanged(0, repositories.size-1)
    }
}