package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.dao.DiaryDao
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.DiaryEntity

@Database(entities = [DiaryEntity::class], version = 2, exportSchema = false)
abstract class DiaryDatabase : RoomDatabase() {

    abstract fun diaryDao(): DiaryDao

    companion object {
        @Volatile
        private var instance: DiaryDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                DiaryDatabase::class.java,
                "diary-database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}