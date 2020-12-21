package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable

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

data class Category(
    val description: String?,
    val id: Int?,
    val name: String?
): Serializable

data class Order(
    val createdAt: String?,
    val id: Int?,
    val orderLines: List<OrderLine>?,
    val orderStatus: OrderStatusEnum?,
    val paymentStatus: PaymentStatusEnum?,
    val userId: Int?
): Serializable

data class OrderLine(
    val id: Int?,
    val orderId: Int?,
    val price: Double?,
    val productId: Int?,
    val quantity: Int?,
    val vat: Int?
): Serializable

data class VatRate(
    val countryId: Int?,
    val id: Int?,
    val rate: Int?,
    val type: String?
): Serializable

data class OrderCreate(
    val id: Int?,
    val paymentLink: String?
): Serializable

enum class PaymentStatusEnum(
    PAID: Int = 0,
    REFUND: Int = 1,
    OPEN: Int = 2,
    EXPIRED: Int = 3,
    CANCELED: Int = 4,
    FAILED: Int = 5,
    PAYOUT: Int = 6,
    CHARGEBACK: Int = 7
): Serializable

enum class OrderStatusEnum (
    UNPROCESSED: Int = 0,
    PROCESSING: Int = 1,
    PROCESSED: Int = 2,
    CANCELED: Int = 3
): Serializable