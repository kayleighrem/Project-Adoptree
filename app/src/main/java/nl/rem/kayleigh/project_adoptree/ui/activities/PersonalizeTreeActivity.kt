package nl.rem.kayleigh.project_adoptree.ui.activities

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_personalize_tree.*
import kotlinx.android.synthetic.main.fragment_login.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.AssignedTree
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.repository.TreeRepository
import nl.rem.kayleigh.project_adoptree.repository.UserRepository
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.TreeViewModel
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.factory.TreeViewModelFactory
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.factory.UserViewModelProviderFactory
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class PersonalizeTreeActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var treeViewModel: TreeViewModel
    lateinit var tree: Tree
    private val args: PersonalizeTreeActivityArgs by navArgs()
    private lateinit var sessionManager: SessionManager

    var color_name: String = "Green"

    companion object {
        const val TREE = "tree"
        const val TAG = "PersonalizeTreeActivity"
        const val PINK = "pink"
        const val RED = "red"
        const val BLUE = "blue"
        const val GREEN = "green"
        const val PURPLE = "purple"
        const val ORANGE = "orange"

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalize_tree)

        sessionManager = SessionManager(this)

        initializeViewModel()
        initializeUI()
    }

    @SuppressLint("ResourceAsColor", "CutPasteId")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeUI() {
        tree = args.tree ?: (intent.getSerializableExtra(TREE) as Tree)
        et_tree_name.hint = tree.assignedTree?.tree_name

        val tree_name = et_tree_name.text.toString().trim()

        ib_tree_color.setOnClickListener {
            showPopUp(it)
        }

        var assignedTree: AssignedTree = AssignedTree(user_id = 100, tree_id = tree.id!!, order_id = null, created_at = null, expire_date = null, tree_color = color_name, tree_name = tree.assignedTree!!.tree_name)

        btn_commit_changes.setOnClickListener {
            if (assignedTree.tree_color == null || assignedTree.tree_color == "") {
                assignedTree.tree_color == GREEN
            }
            if (assignedTree.tree_name == null || assignedTree.tree_name == "") {
                if (assignedTree.tree_name.equals(tree.assignedTree?.tree_name) == false) {
//                    try {
                        assignedTree.tree_name === tree.assignedTree!!.tree_name.toString()
//                    } catch (e: Exception) {
//                        assignedTree.tree_name === " "
//                    }
                }

            }
            println("color? = " + color_name)
            try {
                println("tree assigned? " + assignedTree)
                treeViewModel.personalizeTree(sessionManager.getUserDetails().accessToken, assignedTree)

            }catch (e: Exception) {
                Log.e(TAG, e.message.toString())
            }
            println("test?")

        }
    }

    @SuppressLint("ResourceAsColor")
    private fun showPopUp(view: View?) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.color_picker_menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.red -> {
                    Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show() ; color_name = RED ; ib_tree_color.setBackgroundColor(R.color.colorPersonalizeTree_red)
                }
                R.id.pink -> {
                    Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show() ; color_name = PINK ; ib_tree_color.setBackgroundColor(R.color.colorPersonalizeTree_pink)
                }
                R.id.purple -> {
                    Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show() ; color_name = PURPLE ; ib_tree_color.setBackgroundColor(R.color.colorPersonalizeTree_purple)
                }
                R.id.blue -> {
                    Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show() ; color_name = BLUE ; ib_tree_color.setBackgroundColor(R.color.colorPersonalizeTree_blue)
                }
                R.id.green -> {
                    Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show() ; color_name = GREEN ; ib_tree_color.setBackgroundColor(R.color.colorPersonalizeTree_green)
                }
                R.id.orange -> {
                    Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show() ; color_name = ORANGE ; ib_tree_color.setBackgroundColor(R.color.colorPersonalizeTree_orange)
                }
            }
            true
        })

        popup.show()
    }


    fun initializeViewModel() {
        val treeRepository = TreeRepository()
        val treeViewModelFactory = TreeViewModelFactory(treeRepository, this)
        treeViewModel = ViewModelProvider(this, treeViewModelFactory).get(TreeViewModel::class.java)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        color_name = parent?.selectedItem.toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        color_name = "Green"
    }
}