package zin.byh.org.keketalk.data


data class User(val userName: String = "", val userId: String = "", val userPassword: String = "")
data class Chat(val chatMessage: String = "", val chatName: String = "",
                val chatTime: String = "")