package nl.rem.kayleigh.project_adoptree.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_adoption_overview.view.*
import kotlinx.android.synthetic.main.item_adoption_tree_card.view.*
import kotlinx.android.synthetic.main.item_adoption_tree_card.view.tree_age
import kotlinx.android.synthetic.main.item_adoption_tree_card.view.tree_name
import kotlinx.android.synthetic.main.item_adoption_tree_card_overview.view.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.OrderProduct
import nl.rem.kayleigh.project_adoptree.model.Product
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.OrderViewModel
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.ViewHolder>()  {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sessionManager: SessionManager = SessionManager(itemView.context)
    }

    private val differCallback = object : DiffUtil.ItemCallback<OrderProduct>() {
        override fun areItemsTheSame(oldItem: OrderProduct, newItem: OrderProduct): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OrderProduct, newItem: OrderProduct): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_adoption_tree_card_overview,
                        parent,
                        false
                )
        )
    }

    private var onPayButtonClickListener: ((Product, Int) -> Unit)? = null
    private var onPlusButtonClickListener: ((OrderProduct, Int) -> Unit)? = null
    private var onMinusButtonClickListener: ((OrderProduct, Int) -> Unit)? = null
    private var onRemoveButtonClickListener: ((OrderProduct, Int) -> Unit)? = null

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = differ.currentList[position]

        holder.itemView.apply {
            if (holder.sessionManager.isLogin()) {
                // TODO if login
            }
            tree_name.text = product.product?.name.toString()
            tree_status.text = product.product?.id.toString()
            amount_price.text = product.product?.price.toString()
            amount_number.text = product.quantity.toString()
            tree_age.text = LocalDateTime.parse(product.product?.createdAt).toLocalDate().format(
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.getDefault())
            )

            btn_minus.setOnClickListener {
                val pos: Int = holder.adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onMinusButtonClickListener?.let {
                        it(product, pos)
                    }
                }
                amount_number.text = product.quantity.toString()
            }

            btn_plus.setOnClickListener {
                val pos: Int = holder.adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onPlusButtonClickListener?.let {
                        it(product, pos)
                    }
                }
                amount_number.text = product.quantity.toString()
            }

            btn_remove.setOnClickListener {
                val pos: Int = holder.adapterPosition
                val builder = AlertDialog.Builder(this.context)
                builder.setMessage("Are you sure you want to Delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { dialog, id ->
                            // Delete selected note from database
                            if (pos != RecyclerView.NO_POSITION) {
                                onRemoveButtonClickListener?.let {
                                    it(product, pos)
                                }
                            }
                            notifyDataSetChanged()
                            return@setPositiveButton
                        }
                        .setNegativeButton("No") { dialog, id ->
                            // Dismiss the dialog
                            dialog.dismiss()
                        }
                val alert = builder.create()
                alert.show()
            }

            cb_personal_sign.setOnClickListener {
                if (cb_personal_sign.isChecked) { // If the user wants a personal sign
                    product.isSignActivated = true
                }
                if (!cb_personal_sign.isChecked) { // If the user doesn't want a personal sign anymore
                    product.isSignActivated = false
                }
            }
        }
    }

    fun setOnRemoveButtonClickListener(listener: (OrderProduct, Int) -> Unit) {
        onRemoveButtonClickListener = listener
    }

    fun setOnMinusButtonClickListener(listener: (OrderProduct, Int) -> Unit) {
        onMinusButtonClickListener = listener
    }

    fun setOnPlusButtonClickListener(listener: (OrderProduct, Int) -> Unit) {
        onPlusButtonClickListener = listener
    }

    fun setOnPayButtonClickListener(listener: (Product, Int) -> Unit) {
        onPayButtonClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}