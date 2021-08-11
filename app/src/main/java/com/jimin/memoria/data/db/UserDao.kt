package com.jimin.memoria.data.db
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jimin.memoria.data.entites.User


@Dao
interface UserDao {
    @Query("SELECT * FROM UserTable")
    fun getUsers() : List<User>

    @Query("SELECT * FROM UserTable WHERE id = :id AND pw = :pw")
    fun getUser(id: String, pw: String): User?

    @Query("SELECT idx,id,pw,name FROM UserTable WHERE id= :id")
    fun getUserById(id: String):User?

    @Query("SELECT idx,id,pw,name FROM UserTable WHERE idx= :idx")
    fun getUserByIdx(idx: Int):User?

    @Insert
    fun insertUser(user: User)

    @Query("DELETE FROM UserTable")
    fun deleteUsers()
}