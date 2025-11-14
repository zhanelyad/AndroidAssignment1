package com.example.assignment1.data

import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    fun getAllProfiles(): Flow<List<UserProfile>> = userDao.getAllProfiles()

    suspend fun insertProfile(profile: UserProfile) = userDao.insert(profile)
    suspend fun deleteProfile(profile: UserProfile) = userDao.delete(profile)
}
