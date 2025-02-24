package com.harmony.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.harmony.core.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE uid = 0")
    fun getMyUserData(): Flow<UserEntity>

    @Query("SELECT * FROM user WHERE uid > 0")
    fun getUsers(): Flow<List<UserEntity>>

    @Insert
    suspend fun insertUsers(vararg userEntity: UserEntity)

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Query("DELETE FROM user WHERE uid = :uid")
    fun deleteUser(uid: Long): Int
}