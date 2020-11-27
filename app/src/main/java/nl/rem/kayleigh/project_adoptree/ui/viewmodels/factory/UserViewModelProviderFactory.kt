package nl.rem.kayleigh.project_adoptree.ui.viewmodels.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nl.rem.kayleigh.project_adoptree.repository.UserRepository
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel


class UserViewModelProviderFactory(
        private val userRepository: UserRepository,
        private val context: Context
) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(userRepository, context) as T
    }
}