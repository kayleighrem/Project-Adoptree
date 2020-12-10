package nl.rem.kayleigh.project_adoptree.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.repository.AdoptionRepository
import nl.rem.kayleigh.project_adoptree.repository.TreeRepository

//class AdoptionAdapter(private var trees: List<Tree>) : RecyclerView.Adapter<AdoptionAdapter.ViewHolder>() {
class AdoptionAdapter : RecyclerView.Adapter<AdoptionAdapter.ViewHolder>() {
    var treeRepository: TreeRepository = TreeRepository()
    var adoptionRepository: AdoptionRepository = AdoptionRepository()
    val trees: MutableList<Tree> = ArrayList()
    private var onItemClickListener: ((Tree) -> Unit)? = null
    val notassigned: String = "null"

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTreeName: TextView = itemView.findViewById(R.id.tree_name)
        val itemTreeAge: TextView = itemView.findViewById(R.id.tree_age)
        val itemTreeLocation: TextView = itemView.findViewById(R.id.tree_location)

        init {
            println("test adapter 32")
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "You clicked on item # ${position + 1}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        println("test adapter 41")
        adoptionRepository = adoptionRepository
        CoroutineScope(Dispatchers.IO).launch {
            var trees = adoptionRepository.getAllTrees(notassigned)
            println(trees)
        }
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_adoption_tree_card,
                    parent,
                    false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("test adapter 56")
        holder.itemTreeName.text = trees[position].id.toString()
        holder.itemTreeAge.text = trees[position].dateSeeded
        holder.itemTreeLocation.text = trees[position].forestId.toString()
    }

    override fun getItemCount(): Int {
        return trees.size
    }

    fun setOnItemClickListener(listener: (Tree) -> Unit) {
        onItemClickListener = listener
    }
}