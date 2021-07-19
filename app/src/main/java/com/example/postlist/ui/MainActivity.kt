package com.example.postlist.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.RequestManager
import com.example.postlist.R
import com.example.postlist.adapters.ViewPagerAdapter
import com.example.postlist.databinding.ActivityMainBinding
import com.example.postlist.ui.viewmodels.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: PostsViewModel by viewModels()

        private lateinit var binding: ActivityMainBinding
        private var menu: Menu? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(PostsFragment(), "ALL")
        viewPagerAdapter.addFragment(FavoritesFragment(), "FAVORITES")
        pager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(pager)
        fab.setOnClickListener {
            viewModel.deleteAllPosts()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

}