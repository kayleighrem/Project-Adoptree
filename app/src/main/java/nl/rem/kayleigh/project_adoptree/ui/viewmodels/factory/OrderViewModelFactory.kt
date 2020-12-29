package nl.rem.kayleigh.project_adoptree.ui.viewmodels.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nl.rem.kayleigh.project_adoptree.repository.OrderRepository
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.OrderViewModel

class OrderViewModelFactory {
    class OrderViewModelFactory(
            private val orderRepository: OrderRepository,
            val context: Context
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return OrderViewModel(orderRepository, context) as T
        }
    }
}