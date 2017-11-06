package com.tomaschlapek.tcbasearchitecture.network

/**
 * Network models
 */

data class UserInfoResponse(val id: Long, val name: String, val photo_url: String, val location: LocationInfo?, val token: String?, val facebook_uid: String?)

data class LocationInfo(val latitude: Double, val longitude: Double, val name: String)
