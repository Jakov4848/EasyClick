package hr.ferit.jakovdrmic.easyclick.data.model

data class MetronomeSettings(
    val bpm: Int = 120,
    val selectedToneId: String = "",
    val volume: Float = 1.0f,
    val isAccentEnabled: Boolean = false
)
