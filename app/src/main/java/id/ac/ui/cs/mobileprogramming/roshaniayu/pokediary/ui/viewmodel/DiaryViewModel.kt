package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.DiaryEntity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.repository.DiaryRepository
import kotlinx.coroutines.ObsoleteCoroutinesApi

class DiaryViewModel (private val diaryRepository: DiaryRepository)
    : ViewModel() {
    private val allDiary: LiveData<List<DiaryEntity>> = diaryRepository.getAllDiary()

    @ObsoleteCoroutinesApi
    fun addDiary(diary: DiaryEntity) {
        diaryRepository.insertDiary(diary)
    }

    @ObsoleteCoroutinesApi
    fun deleteDiary(diary: DiaryEntity) {
        diaryRepository.deleteDiary(diary)
    }

    fun getAllDiary(): LiveData<List<DiaryEntity>> {
        return allDiary
    }
}