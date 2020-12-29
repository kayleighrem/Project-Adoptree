package nl.rem.kayleigh.project_adoptree.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_adoption_tree_card.view.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Product
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class AdoptionAdapter :
        RecyclerView.Adapter<AdoptionAdapter.ViewHolder>()  {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sessionManager: SessionManager = SessionManager(itemView.context)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
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

    private var onAdoptButtonClickListener: ((Product, Int) -> Unit)? = null
    private var onInfoButtonClickListener: ((Product, Int) -> Unit)? = null
    private var onNextButtonClickListener: ((Product, Int) -> Unit)? = null


    //    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = differ.currentList[position]

        holder.itemView.apply {
            if (holder.sessionManager.isLogin()) {
                // TODO if login
            }
            tree_name.text = product.name.toString()
            tree_price.text =  product.price.toString()
            tree_location.text = product.createdAt
            tree_age2.text = LocalDateTime.parse(product.createdAt).toLocalDate().format(
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.getDefault())
            )
//            tv_tree_item_description.text = product.description.toString()
//            tree_age.text = LocalDateTime.parse(product.createdAt).toLocalDate().format(
//                    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.getDefault())
//            )

            btn_info.setOnClickListener {
                println("info clicked :) ")
                val pos: Int = holder.adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onInfoButtonClickListener?.let {
                        it(product, pos)
                    }
                }
            }

            btn_adopt.setOnClickListener {
                println("adopt clicked :) ")
                val pos: Int = holder.adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onAdoptButtonClickListener?.let {
                        it(product, pos)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    
    fun setOnAdoptButtonClickListener(listener: (Product, Int) -> Unit) {
        onAdoptButtonClickListener = listener
    }

    fun setOnInfoButtonClickListener(listener: (Product, Int) -> Unit) {
        onInfoButtonClickListener = listener
    }

    fun setOnNextButtonClickListener(listener: (Product, Int) -> Unit) {
        onNextButtonClickListener = listener
    }
}