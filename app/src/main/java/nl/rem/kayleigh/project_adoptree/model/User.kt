package nl.rem.kayleigh.project_adoptree.model

//class User {
//    val firstname: String
//    val lastname: String
//    val username: String
//    val email: String
//    val password: String
//    val forgetToken: String
//    val role: Int
//    val createdAt: LocalDateTime
//
//    public User(String firstname, String lastname, String username, String email, String password, String forgetToken, Int role, LocalDateTime createdAt) {
//
//    }
//}

data class User(
    val firstname: String,
    val lastname: String,
    val username: String,
    val email: String,
    val password: String,
    val forgetToken: String,
    val role: Role,
    val createdAt: String
)

enum class Role(
    Admin: Int = 0,
    Adoptee: Int = 1
)

data class UserResponse(
    val username: String?,
    val AuthToken: String?
)

data class UserResponseRegister(
    val Success: Boolean?,
    val Message: String?
)

data class LoginResponse(
    val AuthToken: String,
)

data class RegisterResponse(
    val Success: Boolean,
    val Message: String,
)