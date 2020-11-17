package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.dao

import android.content.ContentValues
import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.DiaryEntity

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary")
    fun getAllCursor(): Cursor?

    @Query("SELECT * FROM diary ORDER BY id DESC")
    fun getAll(): LiveData<List<@JvmSuppressWildcards DiaryEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(diary: DiaryEntity): Long

    @Query("DELETE FROM diary WHERE id = :id ")
    fun delete(id: Int): Int

    @Update
    fun update(diary: DiaryEntity): Int

    fun diaryFromContentValues(contentValues: ContentValues): DiaryEntity? {
        var diary: DiaryEntity? = null
        if (contentValues.containsKey("content") && contentValues.containsKey("date")) {
            diary = DiaryEntity(contentValues.getAsString("content"), contentValues.getAsString("date"))
        }
        return diary
    }
}
