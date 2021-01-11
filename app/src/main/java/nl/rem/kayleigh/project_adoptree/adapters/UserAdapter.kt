package nl.rem.kayleigh.project_adoptree.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_personalize_tree.*
import kotlinx.android.synthetic.main.fragment_adoption.view.*
import kotlinx.android.synthetic.main.item_adoption_tree_card.view.*
import kotlinx.android.synthetic.main.item_adoption_tree_card.view.tree_name
import kotlinx.android.synthetic.main.item_tree_card.view.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Telemetry
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.ui.activities.PersonalizeTreeActivity
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import java.util.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.TreeViewHolder>(), OnMapReadyCallback {

    inner class TreeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sessionManager: SessionManager = SessionManager(itemView.context)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Tree>() {
        override fun areItemsTheSame(oldItem: Tree, newItem: Tree): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tree, newItem: Tree): Boolean {
            return oldItem == newItem
        }
    }

    var telemetry : Telemetry? = null

    fun thisTelemetry(telemetry: Telemetry) {
        this.telemetry = telemetry
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreeViewHolder {

        return TreeViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_tree_card,
                        parent,
                        false
                )
        )
    }

    private var onPersonalizeButtonClickListener: ((Tree, Int) -> Unit)? = null
    private var onItemClickListener: ((Tree) -> Unit)? = null
    private var onExpandClickListener: ((Tree, Int) -> Unit)? = null


    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TreeViewHolder, position: Int) {
        val tree = differ.currentList[position]

        fun setColor(color: Int) {
            holder.itemView.tree_icon.setImageResource(color)
        }

        holder.itemView.apply {
            tv_growth_value.text = "${telemetry?.reports?.last()?.treeLength.toString()} m"
            tv_humidity_value.text = "${telemetry?.reports?.last()?.humidity.toString()} %"
            tv_co2reduction_value.text = "${tree.health.toString()} g"
            tv_temperature_value.text = "${telemetry?.reports?.last()?.temperature.toString()} °C"
            pb_health_indicator.progress = tree.health!!
//                            if (tree.health in 0..100) {
//                                pb_health_indicator.setProgressTintList(ColorStateList.valueOf(R.color.color_redBrown))
////                                pb_health_indicator.ColorFilter = Color.Red
//                            }
//                            pb_health_indicator.


            if (expandable_card_layout.visibility == View.GONE) {
                ll_rl_tree_item.setOnClickListener {
                    val pos: Int = holder.adapterPosition
                    if (pos!= RecyclerView.NO_POSITION) {
                        onExpandClickListener?.let {
                            expandable_card_layout.visibility = View.VISIBLE
                            it(tree, pos)
                        }
                    }
                }

            } else {
                ll_rl_tree_item.setOnClickListener {
                    onExpandClickListener?.let {
                        expandable_card_layout.visibility = View.GONE
                        notifyDataSetChanged()
                    }
                }
            }

            if (tree.assignedTree?.tree_name.toString() != "" && tree.assignedTree?.tree_name != null) {
                tree_name.text = tree.assignedTree.tree_name.toString()
            }




//            if (tree.assignedTree?.tree_color != null && tree.assignedTree?.tree_color != "") {
//                tree_icon.setColorFilter(R.color.colorPersonalizeTree_purple)
//            }
            when {
                tree.assignedTree?.tree_color?.contains("purple") == true -> setColor(R.drawable.tree_icon_purple)
                tree.assignedTree?.tree_color?.contains("orange") == true -> setColor(R.drawable.tree_icon_orange)
                tree.assignedTree?.tree_color?.contains("blue") == true -> setColor(R.drawable.tree_icon_blue)
                tree.assignedTree?.tree_color?.contains("red") == true -> setColor(R.drawable.tree_icon_red)
                tree.assignedTree?.tree_color?.contains("green") == true -> setColor(R.drawable.tree_icon_green)
                tree.assignedTree?.tree_color?.contains("pink") == true -> setColor(R.drawable.tree_icon_pink)
            }

            btn_personalize_tree.setOnClickListener {
                val pos: Int = holder.adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onPersonalizeButtonClickListener?.let {
                        it(tree, pos)
                    }
                }
            }

            setOnClickListener {
                onItemClickListener?.let { it(tree) }
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

    fun setOnExpandButtonClickListener(listener: (Tree, Int) -> Unit) {
        onExpandClickListener = listener
    }

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