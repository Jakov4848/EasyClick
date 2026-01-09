package hr.ferit.jakovdrmic.easyclick.data.model

data class Tone(
    val id: String = "",
    val name: String = "",
    val fileUrl: String = "",  // Firebase storage url
    val category: ToneCategory = ToneCategory.CLASSIC, //"Classic", "Modern", "Wood"
    val bpmCompatible: Boolean = true
)
