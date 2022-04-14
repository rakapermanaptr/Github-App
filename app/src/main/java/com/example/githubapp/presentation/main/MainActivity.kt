package com.example.githubapp.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.databinding.ActivityMainBinding
import com.example.githubapp.model.User
import com.example.githubapp.presentation.details.UserDetailsActivity

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    private val mainAdapter = MainAdapter { user ->
        val username = user.login

        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra(UserDetailsActivity.USERNAME, username)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        initView()
        observeViewModel()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
    }

    private fun initView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val username = newText!!
                mainViewModel.getUsers(username)
                return true
            }

        })
    }

    private fun observeViewModel() {
        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        mainViewModel.listUser.observe(this, { users ->
            if (users.isNotEmpty()) showUserList(users)
            else showUserNotFoundText()
        })
    }

    private fun showUserList(userList: List<User>?) {
        userList?.let { list ->
            mainAdapter.addItems(list)

            binding.rvUsers.visibility = View.VISIBLE
            binding.rvUsers.apply {
                layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = mainAdapter
            }
            binding.tvUserNotFound.visibility = View.GONE
        }
    }

    private fun showUserNotFoundText() {
        binding.tvUserNotFound.visibility = View.VISIBLE
        binding.rvUsers.visibility = View.GONE
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}