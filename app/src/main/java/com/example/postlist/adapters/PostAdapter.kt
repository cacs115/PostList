package com.example.postlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.postlist.R
import com.example.postlist.data.Post
import com.example.postlist.databinding.ItemPostBinding
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }


    var posts: List<Post>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private var onDotClickListener: ((Post) -> Unit)? = null

    private var onFavoriteClickListener: ((Post) -> Unit)? = null

    fun setOnItemClickListener(listener: (Post) -> Unit) {
        onDotClickListener = listener
    }

    fun setOnFavoriteClickListener(listener: (Post) -> Unit) {
        onFavoriteClickListener = listener
    }

    fun removeAt(position: Int) {
        differ.currentList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.itemView.apply {
            val post = differ.currentList[position]

            if (post.favorite) {
                Glide.with(this).load(R.drawable.ic_baseline_star_24).into(ivFavorite)
            } else {
                Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(ivFavorite)
            }


            tvTitle.text = post.title
            ivDotIndicator.isVisible = position < 20 && !post.read

            this.setOnClickListener {
                onDotClickListener?.let { click ->
                    click(post)
                }
            }
            ivFavorite.setOnClickListener {
                onFavoriteClickListener?.let { click ->
                    click(post)
                }
            }
        }
    }
}