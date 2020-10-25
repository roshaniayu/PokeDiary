package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.DiaryEntity

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary ORDER BY id DESC")
    fun getAll(): LiveData<List<@JvmSuppressWildcards DiaryEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(diary: DiaryEntity)

    @Delete
    fun delete(diary: DiaryEntity)

    @Update
    fun update(diary: DiaryEntity)
}
