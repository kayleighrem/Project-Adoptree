package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable
import java.util.*

data class Product(
        val categoryId: Int?,
        val createdAt: String?,
        val description: String?,
        val id: Int?,
        val isUpForAdoption: Boolean?,
        val name: String?,
        val price: Double?,
        val stock: Int?,
        val vatRateId: Int?
): Serializable

data class OrderProduct (
        val id: Int?,
        val product: Product?,
        var quantity: Int?,
        var isSignActivated: Boolean? = false
) : Serializable