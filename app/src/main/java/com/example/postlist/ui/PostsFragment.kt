package com.example.postlist.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.postlist.R
import com.example.postlist.adapters.PostAdapter
import com.example.postlist.callbacks.SwipeToDeleteCallback
import com.example.postlist.other.Constants.POST_ID
import com.example.postlist.other.Constants.USER_ID
import com.example.postlist.other.Status
import com.example.postlist.ui.viewmodels.PostsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_posts.*

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private val viewModel: PostsViewModel by viewModels()
    lateinit var postAdapter: PostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setHasOptionsMenu(true);

        postAdapter.setOnItemClickListener {
            it.read = true
            viewModel.updatePost(it)
        }

        postAdapter.setOnFavoriteClickListener {
            it.favorite = !it.favorite
            viewModel.updatePost(it)
        }

        viewModel.posts.observe(viewLifecycleOwner, Observer {
            it.data?.let { it1 ->
                postAdapter.posts = it1
                postAdapter.notifyDataSetChanged()
            }

            progressBar.isVisible = it.status == Status.LOADING && it.data.isNullOrEmpty()
            tvErrorMessage.isVisible = it.status == Status.ERROR && it.data.isNullOrEmpty()
            tvErrorMessage.text = it.message
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemRefresh -> {
                Toast.makeText(context, "Hola click", Toast.LENGTH_SHORT).show()
                viewModel.getAllPosts()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupRecyclerView() = rvPosts.apply {
        postAdapter = PostAdapter()
        adapter = postAdapter
        layoutManager = LinearLayoutManager(requireContext())
        val swipeHandler = object : SwipeToDeleteCallback(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.layoutPosition
                val item = postAdapter.posts[pos]
                viewModel.deletePost(item)
                Snackbar.make(requireView(), "Successfully deleted item", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo") {
                            viewModel.insertPost(item)
                        }
                        show()
                    }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(this)
    }
}