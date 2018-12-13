package zin.byh.org.keketalk.Util

import android.content.Context
import android.content.SharedPreferences

//간단한 SQLite
object SharedPreferenceUtil {

    fun getPreference(context: Context, key: String): String? {
        val preferences: SharedPreferences = context.getSharedPreferences("keketalk", Context.MODE_PRIVATE)
        return preferences.getString(key, "")
    }

    fun savePreference(context: Context, key: String, value: String) {
        val preferences: SharedPreferences = context.getSharedPreferences("keketalk", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun removePreference(context: Context, key: String) {
        val preferences: SharedPreferences = context.getSharedPreferences("keketalk", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.remove(key)
        editor.apply()
    }
}