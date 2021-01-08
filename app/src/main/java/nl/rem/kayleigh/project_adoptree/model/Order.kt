package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable

data class Order(
        val createdAt: String?,
        val id: Int?,
        val orderLines: List<OrderLine>?,
        val orderStatus: OrderStatusEnum?,
        val paymentRedirectLink: String?, // redirect link for after mollie payment
        val paymentStatus: PaymentStatusEnum?,
        var userId: Int?
): Serializable

data class OrderLine(
        val id: Int?,
        val orderId: Int?,
        val price: Double?,
        val orderLineStatus: OrderStatusEnum?,
        var productId: Int?,
        val quantity: Int?,
        val vat: Int?
): Serializable

data class OrderResponse(
    val id: Int?,
    val paymentLink: String? // link to mollie
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
