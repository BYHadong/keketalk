package zin.byh.org.keketalk.adapter

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.chatmessage.view.*
import zin.byh.org.keketalk.R
import zin.byh.org.keketalk.data.Chat
import zin.byh.org.keketalk.data.User

class RecyclerAdapter(val chat : ArrayList<Chat>) : RecyclerView.Adapter<RecyclerAdapter.RecyclerHolper>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolper {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chatmessage, parent, false)
        return RecyclerHolper(itemView)
    }

    override fun getItemCount(): Int {
        return chat.size
    }

    override fun onBindViewHolder(holder: RecyclerHolper, position: Int) {
        holder.chatMessage!!.text = chat.get(position).chatMessage
        holder.chatMessageName!!.text = chat.get(position).chatName
        holder.chatProfile!!.clipToOutline = true
        holder.chatProfile!!.setOnClickListener {
            Toast.makeText(holder.itemView.context, "${position + 1} 번 이미지", Toast.LENGTH_SHORT).show()
        }
        holder.chatDelete!!.setOnClickListener {
            FirebaseDatabase.getInstance().getReference("chat").setValue(null)
            Toast.makeText(holder.itemView.context, "${position} 번 이미지", Toast.LENGTH_SHORT).show()
        }
        holder.dataText!!.text = chat.get(position).chatData
        Log.d("RecyclerView", position.toString())
        Log.d("RecyclerView", chat.get(position).chatMessage)
        Log.d("RecyclerView", chat.get(position).chatName)
    }

    class RecyclerHolper(itemView: View, var chatMessage : TextView? = null,  var chatMessageName : TextView? = null,
                         var chatProfile : ImageView? = null, var dataText : TextView? = null, var chatDelete : ImageButton? = null) : RecyclerView.ViewHolder(itemView) {
        init {
            chatMessage  = itemView.findViewById(R.id.chatMessage)
            chatMessageName = itemView.findViewById(R.id.chatMessageName)
            chatProfile = itemView.findViewById(R.id.chatProfile)
            dataText = itemView.findViewById(R.id.dataText)
            chatDelete = itemView.findViewById(R.id.chatDelete)
        }

    }

}


