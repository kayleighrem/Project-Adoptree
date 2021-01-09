package nl.rem.kayleigh.project_adoptree.ui.activities

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.navArgs
import kotlinx.android.synthetic.main.activity_personalize_tree.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class PersonalizeTreeActivity : AppCompatActivity() {

    lateinit var tree: Tree
    private val args: PersonalizeTreeActivityArgs by navArgs()
//    private val args: AdoptionTreeInfoActivityArgs by navArgs()
    private lateinit var sessionManager: SessionManager

    companion object {
        const val TREE = "tree"
        const val TAG = "PersonalizeTreeActivity"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalize_tree)

        sessionManager = SessionManager(this)
        initializeUI()
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeUI() {
        tree = args.tree ?: (intent.getSerializableExtra(TREE) as Tree)
//        tree = args.tree ?: (intent.getSerializableExtra(TREE) as Tree)
//        tv_tree_item_description.text = product.description
//        tv_title2.text = product.name
//        tv_tree_name.text = tree.assignedTree?.tree_name.toString()
//        et_tree_name.setText(tree.assignedTree?.tree_name.toString())
        et_tree_name.hint = tree.assignedTree?.tree_name

        getColor()

    }

    fun getColor() {
        when {
            tree.assignedTree?.tree_color?.contains("purple") == true -> setColor(R.color.colorPersonalizeTree_purple)
            tree.assignedTree?.tree_color?.contains("yellow") == true -> setColor(R.color.colorPersonalizeTree_yellow)
            tree.assignedTree?.tree_color?.contains("pink") == true -> setColor(R.color.colorPersonalizeTree_blue)
        }
    }
    fun setColor(color: Int) {
        ib_tree_color.setBackgroundColor(color)
    }
}