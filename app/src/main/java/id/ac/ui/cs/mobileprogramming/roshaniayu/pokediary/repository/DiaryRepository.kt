package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.repository

import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.dao.DiaryDao
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.DiaryEntity
import kotlinx.coroutines.*

class DiaryRepository private constructor(private val diaryDao: DiaryDao) {
    private val allDiary: LiveData<List<DiaryEntity>> = diaryDao.getAll()

    @ObsoleteCoroutinesApi
    var thread = newSingleThreadContext("diaryRepository") as CoroutineDispatcher

    @ObsoleteCoroutinesApi
    fun insertDiary(diary: DiaryEntity) = GlobalScope.launch(thread) {
        diaryDao.insert(diary)
    }

    fun getAllDiary(): LiveData<List<DiaryEntity>> {
        return allDiary
    }

    @ObsoleteCoroutinesApi
    fun updateDiary(diary: DiaryEntity) = GlobalScope.launch(thread) {
        diaryDao.update(diary)
    }

    @ObsoleteCoroutinesApi
    fun deleteDiary(diary: DiaryEntity) = GlobalScope.launch(thread) {
        diaryDao.delete(diary)
    }

    companion object {
        @Volatile private var instance: DiaryRepository? = null

        fun getInstance(diaryDao: DiaryDao) =
            instance ?: synchronized(this) {
                instance ?: DiaryRepository(diaryDao).also { instance = it }
            }
    }
}