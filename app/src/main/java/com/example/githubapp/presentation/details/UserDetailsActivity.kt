package com.example.githubapp.presentation.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.databinding.ActivityUserDetailsBinding
import com.example.githubapp.model.UserDetails

class UserDetailsActivity : AppCompatActivity() {

    private var _binding: ActivityUserDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        observeViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[UserDetailsViewModel::class.java]
    }

    private fun observeViewModel() {
        val username = intent.getStringExtra(USERNAME) ?: ""
        viewModel.getUserDetails(username)

        viewModel.isLoading.observe(this, { isLoading ->
            showLoading(isLoading)
        })

        viewModel.userDetails.observe(this, { userDetails ->
            showUserDetails(userDetails)
        })
    }

    private fun showUserDetails(userDetails: UserDetails) {
        binding.apply {
            Glide.with(this@UserDetailsActivity)
                .load(userDetails.avatarUrl)
                .into(imgUser)
            tvUserName.text = userDetails.name
            tvCompanyName.text = userDetails.company ?: "-"
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val USERNAME = "USERNAME"
    }
}