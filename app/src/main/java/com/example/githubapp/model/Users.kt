package com.example.githubapp.model

import com.squareup.moshi.Json

data class Users(

	@Json(name="total_count")
	val totalCount: Int? = null,

	@Json(name="incomplete_results")
	val incompleteResults: Boolean? = null,

	@Json(name = "items")
	val users: List<User>? = null
)

data class User(

	@Json(name="login")
	val login: String? = null,

	@Json(name="avatar_url")
	val avatarUrl: String? = null,

	@Json(name="events_url")
	val eventsUrl: String? = null,

	@Json(name="id")
	val id: Int? = null
)
