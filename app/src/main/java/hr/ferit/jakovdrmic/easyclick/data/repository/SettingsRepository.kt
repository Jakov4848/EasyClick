package hr.ferit.jakovdrmic.easyclick.data.repository

import android.content.Context
import hr.ferit.jakovdrmic.easyclick.data.model.MetronomeSettings


class SettingsRepository(context: Context) {

    private val prefs = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)

    fun saveSettings(settings: MetronomeSettings) {
        prefs.edit()
            .putInt("bpm", settings.bpm)
            .putString("selectedToneId", settings.selectedToneId)
            .putFloat("volume", settings.volume)
            .putBoolean("isAccentEnabled", settings.isAccentEnabled)
            .apply()
    }

    fun getSettings(): MetronomeSettings {
        return MetronomeSettings(
            bpm = prefs.getInt("bpm", 120),
            selectedToneId = prefs.getString("selectedToneId", "") ?: "",
            volume = prefs.getFloat("volume", 1.0f),
            isAccentEnabled = prefs.getBoolean("isAccentEnabled", false)
        )
    }
}