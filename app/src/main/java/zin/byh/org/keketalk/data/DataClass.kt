package zin.byh.org.keketalk.data

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties



data class User(val userName: String="", val userId: String="", val userPassword: String="")
data class Chat(val chatMessage: String = "", val chatName: String = "", val chatData: String = "")
//{
//
//    var starCount = 0
//
//    @Exclude
//    fun toMap(): Map<String, Any> {
//        val result = HashMap<String, Any>()
//        result.put("chatMessage", chatMessage)
//        result.put("chatName", chatName)
//        result.put("chatData", chatData)
//        result.put("starCount", starCount)
//        return result
//    }
//}