package hr.ferit.jakovdrmic.easyclick.data.model

data class Note(
    val id: String = "",
    val text: String = "",
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
