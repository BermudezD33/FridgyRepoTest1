package com.gquesada.notes.ui.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gquesada.notes.domain.models.NoteModel

class MainViewModel : ViewModel() {

    private val _navigationEvent = MutableLiveData<NavigationScreen>()
    val navigationEvent: LiveData<NavigationScreen>
        get() = _navigationEvent

    fun navigateTo(screen: NavigationScreen) {
        _navigationEvent.value = screen
    }
}

sealed class NavigationScreen {
    object AddNotes : NavigationScreen()
    object TagList : NavigationScreen()

    class EditNote(val noteModel: NoteModel) : NavigationScreen()
}