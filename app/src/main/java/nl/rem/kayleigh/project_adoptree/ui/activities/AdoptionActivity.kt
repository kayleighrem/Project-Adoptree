package nl.rem.kayleigh.project_adoptree.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Order
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.repository.TreeRepository
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.AdoptionViewModel
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.factory.AdoptionViewModelFactory
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

class AdoptionActivity : AppCompatActivity() {
    lateinit var adoptionViewModel: AdoptionViewModel
    lateinit var adoptionViewModelFactory: AdoptionViewModelFactory
    private lateinit var treeRepository: TreeRepository
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adoption)

        initializeViewModels()

        val action: String? = intent?.action
        if (action === android.content.Intent.ACTION_VIEW) {
            val data: Uri? = intent?.data
            val paymentId: String? = data!!.getQueryParameter("id")

            // Optional: Do stuff with the payment ID
        }
    }

    fun initializeViewModels() {
        treeRepository = TreeRepository()
        adoptionViewModel = ViewModelProvider(this, adoptionViewModelFactory).get(AdoptionViewModel::class.java)
    }

    private fun startPayment(order: Order) {
        val client: OkHttpClient =  OkHttpClient()

        val requestBody : RequestBody =  MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("orderId", order.id.toString())
                .build();

        val request: Request =  Request.Builder()
                .url("https://www.thisismylink.com/api/create-payment")
                .post(requestBody)
                .build();
    }
}