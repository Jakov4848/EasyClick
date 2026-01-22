package hr.ferit.jakovdrmic.easyclick.data.preferences

import android.content.Context

object SoundPreferences {

    private const val PREFS_NAME = "sound_prefs"
    private const val KEY_SELECTED_SOUND = "selected_sound"
    private const val KEY_FAVORITES = "favorites"

    fun saveSelectedSound(context: Context, soundId: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_SELECTED_SOUND, soundId)
            .apply()
    }

    fun loadSelectedSound(context: Context): String? =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_SELECTED_SOUND, null)

    fun saveFavorites(context: Context, favorites: Set<String>) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putStringSet(KEY_FAVORITES, favorites)
            .apply()
    }

    fun loadFavorites(context: Context): Set<String> =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
}
