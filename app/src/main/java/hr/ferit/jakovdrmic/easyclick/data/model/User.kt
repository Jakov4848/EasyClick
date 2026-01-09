package hr.ferit.jakovdrmic.easyclick.data.model

data class User(
    val userId: String = "",
    val email: String = "",
    val favoriteTones: List<String> = emptyList()
)
