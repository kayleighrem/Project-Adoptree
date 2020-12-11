package nl.rem.kayleigh.project_adoptree.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
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

//class AdoptionAdapter(private var trees: List<Tree>) : RecyclerView.Adapter<AdoptionAdapter.ViewHolder>() {
class AdoptionAdapter : RecyclerView.Adapter<AdoptionAdapter.AdoptionViewHolder>()  {
    inner class AdoptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sessionManager: SessionManager = SessionManager(itemView.context)
//        val itemTreeName: TextView = itemView.findViewById(R.id.tree_name)
//        val itemTreeAge: TextView = itemView.findViewById(R.id.tree_age)
//        val itemTreeLocation: TextView = itemView.findViewById(R.id.tree_location)
//
        init {
            println("test adapter 32")
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "You clicked on item # ${position + 1}", Toast.LENGTH_SHORT).show()
            }
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdoptionViewHolder {
        println("test adapter 41")
//        adoptionRepository = adoptionRepository
//        CoroutineScope(Dispatchers.IO).launch {
////            trees = adoptionRepository.getAllTrees()
////
////            println("trees: "+ trees)
//        }
        return AdoptionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_adoption_tree_card,
                    parent,
                    false
            )
        )
    }

//    override fun onBindViewHolder(holder: AdoptionViewHolder, position: Int) {
//        println("test adapter 56")
//        holder.itemTreeName.text = trees[position].id.toString()
//        holder.itemTreeAge.text = trees[position].dateSeeded
//        holder.itemTreeLocation.text = trees[position].forestId.toString()
//    }

    private var onItemClickListener: ((Tree) -> Unit)? = null
    private var onAddButtonClickListener: ((Tree, Int) -> Unit)? = null

    override fun onBindViewHolder(holder: AdoptionViewHolder, position: Int) {
        println("test adoptionadapter 89")
        val tree = differ.currentList[position]
        holder.itemView.apply {
            if (holder.sessionManager.isLogin()) {
//                likeButton.visibility = View.VISIBLE
//                if (article.IsLiked == true) {
//                    likeButton.setImageResource(R.drawable.ic_baseline_favorite_24)
//                } else {
//                    likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
//                }
            }
//            likeButton.setOnClickListener {
//                val pos: Int = holder.adapterPosition
//                if (pos != RecyclerView.NO_POSITION) {
//                    onAddButtonClickListener?.let {
//                        it(article, pos)
//                    }
//                }
//            }
//            imageCard.load(article.Image) {
//                crossfade(true)
//                placeholder(R.drawable.ic_baseline_image_24)
//            }
            tree_name.text = tree.id.toString()
            tree_location.text = tree.forestId.toString()
//            tree_age.text = LocalDateTime.parse(tree.dateSeeded).toLocalDate().format(
//                    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.getDefault())
//            ).toString()

            setOnClickListener {
                onItemClickListener?.let { it(tree) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener: (Tree) -> Unit) {
        onItemClickListener = listener
    }
}