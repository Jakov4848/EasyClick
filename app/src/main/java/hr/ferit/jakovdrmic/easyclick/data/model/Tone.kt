package hr.ferit.jakovdrmic.easyclick.data.model

data class Tone(
    val id: String = "",
    val name: String = "",
    val fileUrl: String = "",
    val category: ToneCategory = ToneCategory.CLASSIC,
    val bpmCompatible: Boolean = true
)
