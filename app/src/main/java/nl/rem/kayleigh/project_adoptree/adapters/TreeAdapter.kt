package nl.rem.kayleigh.project_adoptree.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList

class TreeAdapter : RecyclerView.Adapter<TreeAdapter.TreeViewHolder>() {
    private val treeList = ArrayList<Fragment>()
    lateinit var linearLayout: LinearLayout
    lateinit var expandableLayout: RelativeLayout


    private var onItemClickListener: ((Tree) -> Unit)? = null

    inner class TreeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var linearLayout: LinearLayout = itemView.findViewById(R.id.card_linear_layout)
        var expandableLayout: RelativeLayout = itemView.findViewById(R.id.expandable_card_layout)
        var sessionManager: SessionManager = SessionManager(itemView.context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreeViewHolder {
        return TreeViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_tree_card,
                        parent,
                        false
                )
        )
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

    override fun onBindViewHolder(holder: TreeViewHolder, position: Int) {
//        linearLayout.setOnClickListener(itemView)
//        var isExpandable: Boolean = holder.get(position).isExpandable()
        holder.expandableLayout


        val article = differ.currentList[position]
        holder.itemView.apply {
            if (holder.sessionManager.isLogin()) {
                expandableLayout.visibility = View.VISIBLE
//                if (article.IsLiked == true) {
//                    likeButton.setImageResource(R.drawable.ic_baseline_favorite_24)
//                } else {
//                    likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
//                }
            }
            linearLayout.setOnClickListener {
                val pos: Int = holder.adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
//                    onLikedButtonClickListener?.let {
//                        it(article, pos)
//                    }
                }
            }
//            imageCard.load(article.Image) {
//                crossfade(true)
//                placeholder(R.drawable.ic_baseline_image_24)
//            }
//            title.text = article.Title
//            date.text = LocalDateTime.parse(article.PublishDate).toLocalDate().format(
//                    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.getDefault())
//            ).toString()

            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    override fun getItemCount(): Int {
        return treeList.size
    }
}