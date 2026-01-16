package hr.ferit.jakovdrmic.easyclick.data.model


data class Sound(
    val id: String,
    val name: String,
    val resId: Int,
    val category: SoundCategory
)
