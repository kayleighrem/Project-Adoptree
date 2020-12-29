package nl.rem.kayleigh.project_adoptree.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.util.Linkify
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_adoption.*
import kotlinx.android.synthetic.main.activity_adoption.toolbar
import kotlinx.android.synthetic.main.item_adoption_tree_info.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Product
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class AdoptionTreeInfoActivity : AppCompatActivity() {

    lateinit var product: Product
    private val args: AdoptionTreeInfoActivityArgs by navArgs()
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