package com.example.freshauctionapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FreshDao {

    @Insert
    fun insertFresh(freshData: List<FreshData>)

    @Insert
    fun insertSave(saveItem: SaveItem): Long

    @Query("DELETE FROM SaveItem WHERE id=:saveId")
    fun deleteSaveData(saveId: Long)


}