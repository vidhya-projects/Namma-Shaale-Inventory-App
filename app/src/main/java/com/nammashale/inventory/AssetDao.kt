package com.nammashale.inventory

import androidx.room.*

@Dao
interface AssetDao {

    @Insert
    suspend fun insertAsset(
        asset: SchoolAssetEntity
    )

    @Query("SELECT * FROM assets")
    suspend fun getAllAssets():
            List<SchoolAssetEntity>

    @Delete
    suspend fun deleteAsset(
        asset: SchoolAssetEntity
    )

    @Update
    suspend fun updateAsset(
        asset: SchoolAssetEntity
    )
}