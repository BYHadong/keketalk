package zin.byh.org.keketalk.Util

import android.content.Context
import android.content.SharedPreferences

//간단한 SQLite
object SharedPreferenceUtil {

    fun getPreference(context: Context) : String? {
        val preferences : SharedPreferences = context.getSharedPreferences("keketalk", Context.MODE_PRIVATE)
        return preferences.getString("name", "")
    }

    fun savePreference(context: Context, value : String){
        val preferences : SharedPreferences = context.getSharedPreferences("keketalk", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("name", value)
        editor.apply()
    }

    fun removePreference(context: Context){
        val preferences : SharedPreferences = context.getSharedPreferences("keketalk", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.remove("name")
        editor.apply()
    }
}