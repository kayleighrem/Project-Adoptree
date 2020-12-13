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
import kotlinx.android.synthetic.main.fragment_adoption.view.*
import kotlinx.android.synthetic.main.item_adoption_tree_card.view.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import java.util.*

class AdoptionAdapter : RecyclerView.Adapter<AdoptionAdapter.ViewHolder>()  {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sessionManager: SessionManager = SessionManager(itemView.context)

//        fun bind(tree: Tree, listener: (Tree) -> Unit) = with(itemView) {
//            println("test bind?")
//            tree_age.text = tree.dateSeeded
//            tree_name.text = tree.forestId.toString()
//            setOnClickListener { listener(tree) }
//        }
//        init {
//            println("test adapter 32")
//            itemView.setOnClickListener {
//                val position: Int = adapterPosition
//                Toast.makeText(itemView.context, "You clicked on item # ${position + 1}", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    fun test() {
        println("test adapter?????")

    }

    private val differCallback = object : DiffUtil.ItemCallback<Tree>() {
        override fun areItemsTheSame(oldItem: Tree, newItem: Tree): Boolean {
            println("test items same")
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tree, newItem: Tree): Boolean {
            println("test contents same")
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        println("test adapter 41")
//        adoptionRepository = adoptionRepository
//        CoroutineScope(Dispatchers.IO).launch {
////            trees = adoptionRepository.getAllTrees()
////
////            println("trees: "+ trees)
//        }
        return ViewHolder(
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("test adoptionadapter 89")
        val tree = differ.currentList[position]
        println("tree???? = " + tree)
        holder.itemView.apply {
            if (holder.sessionManager.isLogin()) {
                println("test if login? ")
                // TODO if login
            }
//            likeButton.setOnClickListener {
//                val pos: Int = holder.adapterPosition
//                if (pos != RecyclerView.NO_POSITION) {
//                    onAddButtonClickListener?.let {
//                        it(article, pos)
//                    }
//                }
//            }
            tv_filter.text = "hola"
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
        println("test itemcount")
        println(differ.currentList.size)
        return differ.currentList.size
    }

    fun setOnItemClickListener(trees: List<Tree>, listener: (Tree) -> Unit) {
        println("test itemclick")
        onItemClickListener = listener
    }

    fun setOnItemClickListener(listener: (Tree) -> Unit) {
        println("test onclick?")
        onItemClickListener = listener
    }


    fun setOnLikedButtonClickListener(listener: (Tree, Int) -> Unit) {
        onAddButtonClickListener = listener
    }
}