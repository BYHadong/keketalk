package zin.byh.org.keketalk

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import zin.byh.org.keketalk.Util.SharedPreferenceUtil
import zin.byh.org.keketalk.adapter.RecyclerAdapter
import zin.byh.org.keketalk.data.Chat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {

    lateinit var chatList: RecyclerView
    lateinit var chatMessageBox: LinearLayout
    lateinit var sendMessageText: EditText

    val TAG = "ChatActivity"
    val chating = ArrayList<Chat>()
    val database = FirebaseDatabase.getInstance().getReference()
    val adapter = RecyclerAdapter(chating, this@ChatActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        sendMessageText = findViewById(R.id.sendMessageText)
        chatList = findViewById(R.id.chatList)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true

        sendMessageText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                when (actionId) {
                    EditorInfo.IME_ACTION_SEND -> {
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

        database.child("chat").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG + "Cancelled", "onCancelled")
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.d(TAG + "DataChange", "onDataChange")
                val subChat = ArrayList<Chat>()
                chating.clear()
                subChat.clear()
                for (data in p0.children) {
                    subChat.add(data.getValue(Chat::class.java)!!)
                }
                for (index in subChat.size - 1 downTo 0) {
                    chating.add(subChat.get(index))
                    adapter.notifyDataSetChanged()
                    Log.d(TAG, "${chating.size}")
                    Log.d(TAG, "${chating.get(adapter.itemCount - 1).chatMessage} :: ${chating.get(adapter.itemCount - 1).chatName}")
                }
                chatList.scrollToPosition(0)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.get_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.logout -> {
                SharedPreferenceUtil.removePreference(this@ChatActivity, "name")
                startActivity(Intent(this@ChatActivity, LoginActivity::class.java))
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

    fun chating() {
        val name = SharedPreferenceUtil.getPreference(this@ChatActivity, "name")!!
        val time = SimpleDateFormat("aa hh:mm", Locale.KOREAN).format(Date(System.currentTimeMillis()))
        val dayLine = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREAN).format(Date(System.currentTimeMillis()))
        val chat = Chat(sendMessageText.text.toString(), name, dayLine + " || " + time)
        if(sendMessageText.text.toString() != ""){
            database.child("chat").push().setValue(chat)
            sendMessageText.text = null
        } else {
            Snackbar.make(window.decorView.rootView, "채팅을 적어주세요.", Snackbar.LENGTH_SHORT).show()
        }


    }
}
