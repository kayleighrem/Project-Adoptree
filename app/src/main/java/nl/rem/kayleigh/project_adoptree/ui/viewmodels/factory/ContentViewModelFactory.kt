package nl.rem.kayleigh.project_adoptree.ui.viewmodels.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nl.rem.kayleigh.project_adoptree.repository.ContentRepository
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.ContentViewModel

class ContentViewModelFactory (
        private val contentRepository: ContentRepository,
        val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContentViewModel(contentRepository, context) as T
    }
}
