package com.massimoregoli.listofproverbs.viewmodel
import android.app.Application
import androidx.lifecycle.*
import com.massimoregoli.listofproverbs.db.DbProverb
import com.massimoregoli.listofproverbs.db.Proverb
import com.massimoregoli.listofproverbs.db.ProverbRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProverbViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProverbRepo

    init {
        val proverbDao = DbProverb.getInstance(application).proverbDao()
        repository = ProverbRepo(proverbDao)
    }

    fun readByTag(s: String, f: Int): LiveData<MutableList<Proverb>> {
        return repository.readByTag("%$s%", f)
    }

    @Suppress("unused")
    fun insert(item: Proverb) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(item)
        }
    }
    fun update(item: Proverb) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(item)
        }
    }
    fun delete(item: Proverb) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(item)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ProverbDbViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProverbViewModel(application) as T
    }
}