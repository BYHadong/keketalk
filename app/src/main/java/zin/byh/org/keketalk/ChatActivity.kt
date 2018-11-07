package zin.byh.org.keketalk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.google.firebase.database.*
import zin.byh.org.keketalk.Util.SharedPreferenceUtil
import zin.byh.org.keketalk.adapter.RecyclerAdapter
import zin.byh.org.keketalk.data.Chat
import android.view.inputmethod.EditorInfo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class ChatActivity : AppCompatActivity() {

    lateinit var chatList : RecyclerView
    lateinit var chatMessageBox : LinearLayout
    lateinit var sendMessageText : EditText

    val TAG = "ChatActivity"
    val chating = ArrayList<Chat>()
    val database = FirebaseDatabase.getInstance().getReference()
    val adapter = RecyclerAdapter(chating)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        sendMessageText = findViewById(R.id.sendMessageText)
        chatList = findViewById(R.id.chatList)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true

        sendMessageText.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                when (actionId) {
                    EditorInfo.IME_ACTION_SEND ->{
                        chating()
                    }
                    else -> {
                        return false
                    }
                }
                return true
            }

        })

        chatList.layoutManager = layoutManager
        chatList.adapter = adapter


        database.child("chat").addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG+"Cancelled", "onCancelled")
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.d(TAG+"DataChange", "onDataChange")
                val subChat = ArrayList<Chat>()
                chating.clear()
                subChat.clear()
                for (data in p0.children){
                    subChat.add(data.getValue(Chat::class.java)!!)
                }
                for (index in subChat.size-1 downTo 0){
                    chating.add(subChat.get(index))
                    adapter.notifyDataSetChanged()
                    Log.d(TAG, "${chating.size}")
                    Log.d(TAG, "${chating.get(adapter.itemCount - 1).chatMessage} :: ${chating.get(adapter.itemCount -1).chatName}")
                }
                chatList.scrollToPosition(0)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.get_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.logout -> {
                SharedPreferenceUtil.removePreference(this@ChatActivity)
                finish()
                return true
            }
            R.id.deletData -> {
                database.child("chat").removeValue()
                Toast.makeText(this@ChatActivity, "채팅 내용 삭제", Toast.LENGTH_SHORT).show()
                chating.clear()
                adapter.notifyDataSetChanged()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun chating(){
        val name = SharedPreferenceUtil.getPreference(this@ChatActivity)!!
        val date = System.currentTimeMillis()
        val dateFormet = Date(date)
        val dayFormet = SimpleDateFormat("yyyy/MM/dd\nHH:mm", Locale.KOREAN)
        val time = dayFormet.format(dateFormet)
        val chat = Chat(sendMessageText.text.toString(), name, time)
        database.child("chat").child(name).push().setValue(chat)
        sendMessageText.text = null

    }

//    private fun chating() {
//        // Create new post at /user-posts/$userid/$postid and at
//        // /posts/$postid simultaneously
//        if(sendMessageText.text.toString().equals("")){
//            Snackbar.make(window.decorView.rootView, "텍스트를 입력하세요", Snackbar.LENGTH_SHORT).show()
//            return
//        }
//
//        val name = SharedPreferenceUtil.getPreference(this@ChatActivity)!!
//        val date = System.currentTimeMillis()
//        val dateFormet = Date(date)
//        val dayFormet = SimpleDateFormat("yyyy/MM/dd\nHH:mm", Locale.KOREAN)
//        val time = dayFormet.format(dateFormet)
//        val key = database.child("chat").push().getKey()
//        val chat = Chat(sendMessageText.text.toString(), name, time)
//        val chatValues = chat.toMap()
//
//        val childUpdates = HashMap<String, Any>()
//        childUpdates.put("/chat/$key", chatValues)
//        database.updateChildren(childUpdates)
//
//        sendMessageText.text = null
//
//    }
}
