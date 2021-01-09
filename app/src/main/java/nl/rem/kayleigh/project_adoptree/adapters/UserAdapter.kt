package nl.rem.kayleigh.project_adoptree.adapters

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_personalize_tree.*
import kotlinx.android.synthetic.main.item_adoption_tree_card.view.*
import kotlinx.android.synthetic.main.item_adoption_tree_card.view.tree_name
import kotlinx.android.synthetic.main.item_tree_card.view.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Product
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList

class UserAdapter : RecyclerView.Adapter<UserAdapter.TreeViewHolder>(), OnMapReadyCallback {

    inner class TreeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sessionManager: SessionManager = SessionManager(itemView.context)
        lateinit var tree: Tree
    }

    private val differCallback = object : DiffUtil.ItemCallback<Tree>() {
        override fun areItemsTheSame(oldItem: Tree, newItem: Tree): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tree, newItem: Tree): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreeViewHolder {
        println("test create")

//        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
        return TreeViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_tree_card,
                        parent,
                        false
                )
        )
    }

    private var onPersonalizeButtonClickListener: ((Tree, Int) -> Unit)? = null


//    private val treeList = ArrayList<Fragment>()
    private var onItemClickListener: ((Tree) -> Unit)? = null


    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TreeViewHolder, position: Int) {

        println("test bindview")

        val tree = differ.currentList[position]

        fun setColor(color: Int) {
           holder.itemView.tree_icon.setColorFilter(color)
        }

        holder.itemView.apply {
            if (expandable_card_layout.visibility == View.GONE) {
                ll_rl_tree_item.setOnClickListener {
                    expandable_card_layout.visibility = View.VISIBLE
                    notifyDataSetChanged()
                }
            } else {
                ll_rl_tree_item.setOnClickListener {
                    expandable_card_layout.visibility = View.GONE
                    notifyDataSetChanged()
                }
            }

            if (tree.assignedTree?.tree_name.toString() != "" && tree.assignedTree?.tree_name != null) {
                tree_name.text = tree.assignedTree.tree_name.toString()
            }

//            if (tree.assignedTree?.tree_color != null && tree.assignedTree?.tree_color != "") {
//                tree_icon.setColorFilter(R.color.colorPersonalizeTree_purple)
//            }
            when {
                tree.assignedTree?.tree_color?.contains("purple") == true -> setColor(R.color.colorPersonalizeTree_purple)
                tree.assignedTree?.tree_color?.contains("pink") == true -> setColor(R.color.colorPersonalizeTree_yellow)
//                tree.assignedTree?.tree_color?.contains("pink") == true -> setColor(R.color.colorPersonalizeTree_blue)
                tree.assignedTree?.tree_color?.contains("red") == true -> setColor(R.color.colorPersonalizeTree_red)
                tree.assignedTree?.tree_color?.contains("green") == true -> setColor(R.color.colorPersonalizeTree_green)
            }

            btn_personalize_tree.setOnClickListener {
                val pos: Int = holder.adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onPersonalizeButtonClickListener?.let {
                        it(tree, pos)
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap, ) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        val treelocation = LatLng(51.547052, 4.760808)
        googleMap.addMarker(MarkerOptions().position(treelocation)
                .title("Marker in Mastbos"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(treelocation))
    }


//    private val differCallback = object : DiffUtil.ItemCallback<Tree>() {
//        override fun areItemsTheSame(oldItem: Tree, newItem: Tree): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: Tree, newItem: Tree): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    val differ = AsyncListDiffer(this, differCallback)

    fun setOnPersonalizeButtonClickListener(listener: (Tree, Int) -> Unit) {
        onPersonalizeButtonClickListener = listener
    }

    fun setOnItemClickListener(listener: (Tree) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}