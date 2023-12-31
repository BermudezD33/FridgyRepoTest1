package com.gquesada.notes.ui.notes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gquesada.notes.domain.models.NoteModel
import com.gquesada.notes.domain.usecases.DeleteNoteUseCase
import com.gquesada.notes.domain.usecases.GetNotesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteListViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {

    private val _noteListLiveData = MutableLiveData<List<NoteModel>>()
    val noteListLiveData: LiveData<List<NoteModel>>
        get() = _noteListLiveData

    //Referencia para entender que es un CoroutineScope y un CoroutineContext
    // actualmente no se esta utilizando
//    val context = Dispatchers.Main + SupervisorJob()
//    val scope = CoroutineScope(context)

    // cuando usamos un custom scope debemos asegurarnos de cancelar el scope en el onCleared del viewmodel
    // esto para no dejar en memoria cualquier proceso que ya no necesitemos
//    override fun onCleared() {
//        super.onCleared()
//        scope.cancel()
//    }


    fun onViewReady() {
        // aca puede usar el custom scope

        // scope es necesario para poder lanzar una coroutine
        viewModelScope.launch {
           getNotesUseCase.execute()
                .flowOn(Dispatchers.IO )
                .collect { notes ->
                    _noteListLiveData.value = notes
                }
        }
    }

    fun deleteNote(note: NoteModel) {
        viewModelScope.launch {
            //lanzar la tarea
            withContext(Dispatchers.IO) {
                deleteNoteUseCase.execute(note)
            }
            //luego modificar UI

        }
    }
}