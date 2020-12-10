package nl.rem.kayleigh.project_adoptree.ui.viewmodels.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nl.rem.kayleigh.project_adoptree.repository.AdoptionRepository
import nl.rem.kayleigh.project_adoptree.repository.TreeRepository
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.AdoptionViewModel

class AdoptionViewModelFactory (
    private val adoptionRepository: AdoptionRepository,
    val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AdoptionViewModel(adoptionRepository, context) as T
    }
}