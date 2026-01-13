package hr.ferit.jakovdrmic.easyclick.data.model

data class Sound(
    val id: String = "",
    val name: String = "",
    val fileUrl: String = "",
    val category: SoundCategory = SoundCategory.CLASSIC,
    val bpmCompatible: Boolean = true
)
