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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class AdoptionAdapter : RecyclerView.Adapter<AdoptionAdapter.ViewHolder>()  {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_adoption_tree_card,
                    parent,
                    false
            )
        )
    }

    private var onItemClickListener: ((Tree) -> Unit)? = null
    private var onAddButtonClickListener: ((Tree, Int) -> Unit)? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tree = differ.currentList[position]
        holder.itemView.apply {
            if (holder.sessionManager.isLogin()) {
                // TODO if login
            }
            tree_name.text = tree.id.toString()
            tree_location.text = tree.forestId.toString()
            tree_age.text = LocalDateTime.parse(tree.dateSeeded).toLocalDate().format(
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.getDefault())
            ).toString()

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

    fun setOnLikedButtonClickListener(listener: (Tree, Int) -> Unit) {
        onAddButtonClickListener = listener
    }
}