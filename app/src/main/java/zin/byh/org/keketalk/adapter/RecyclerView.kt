package zin.byh.org.keketalk.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import zin.byh.org.keketalk.R
import zin.byh.org.keketalk.data.Chat

class RecyclerAdapter(val chat: ArrayList<Chat>, val context: Context) : RecyclerView.Adapter<RecyclerAdapter.RecyclerHolper>() {
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
        holder.dataText!!.text = chat.get(position).chatTime
    }

    class RecyclerHolper(itemView: View, var chatMessage: TextView? = null, var chatMessageName: TextView? = null,
                         var chatProfile: ImageView? = null, var dataText: TextView? = null) : RecyclerView.ViewHolder(itemView) {
        init {
            chatMessage = itemView.findViewById(R.id.chatMessage)
            chatMessageName = itemView.findViewById(R.id.chatMessageName)
            chatProfile = itemView.findViewById(R.id.chatProfile)
            dataText = itemView.findViewById(R.id.dataText)
        }

    }

}


