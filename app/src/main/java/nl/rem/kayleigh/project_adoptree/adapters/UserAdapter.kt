package nl.rem.kayleigh.project_adoptree.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_adoption_tree_card.view.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList

class UserAdapter : RecyclerView.Adapter<UserAdapter.TreeViewHolder>() {
    private val treeList = ArrayList<Fragment>()
    lateinit var relativeLayout: RelativeLayout
    lateinit var expandableLayout: RelativeLayout
    private var onItemClickListener: ((Tree) -> Unit)? = null

    inner class TreeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView: RecyclerView = itemView.findViewById(R.id.card_adoption_relative_layout)
        val relativeLayout: RelativeLayout = itemView.findViewById(R.id.card_relative_layout)
        var expandableLayout: RelativeLayout = itemView.findViewById(R.id.expandable_card_layout)
        var sessionManager: SessionManager = SessionManager(itemView.context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreeViewHolder {
        println("test?")
        return TreeViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_adoption_tree_card,
                        parent,
                        false
                )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TreeViewHolder, position: Int) {
        println("test treeadapter")
        val tree = differ.currentList[position]
        holder.itemView.apply {
//            if (holder.sessionManager.isLogin()) {
//                likeButton.visibility = View.VISIBLE
//                if (article.IsLiked == true) {
//                    likeButton.setImageResource(R.drawable.ic_baseline_favorite_24)
//                } else {
//                    likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
//                }
//            }
//            imageCard.load(article.Image) {
//                crossfade(true)
//                placeholder(R.drawable.ic_baseline_image_24)
//            }
            tree_name.text = tree.id.toString()
            tree_location.text = tree.forestId.toString()
            tree_age.text = LocalDateTime.parse(tree.dateSeeded).toLocalDate().format(
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.getDefault())
            ).toString()

        }
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

    fun setOnItemClickListener(listener: (Tree) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return treeList.size
    }
}