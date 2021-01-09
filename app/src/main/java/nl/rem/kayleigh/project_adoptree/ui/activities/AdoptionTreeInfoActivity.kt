package nl.rem.kayleigh.project_adoptree.ui.activities

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import kotlinx.android.synthetic.main.item_adoption_tree_info.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Product
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import nl.rem.kayleigh.project_adoptree.ui.activities.AdoptionTreeInfoActivityArgs as AdoptionTreeInfoActivityArgs1

class AdoptionTreeInfoActivity : AppCompatActivity() {

    lateinit var product: Product
    private val args: AdoptionTreeInfoActivityArgs1 by navArgs()
    private lateinit var sessionManager: SessionManager

    companion object {
        const val PRODUCT = "product"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_adoption_tree_info)

        sessionManager = SessionManager(this)
        initializeUI()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeUI() {
        product = args.product ?: (intent.getSerializableExtra(PRODUCT) as Product)
        tv_tree_item_description.text = product.description
        tv_title2.text = product.name
    }
}