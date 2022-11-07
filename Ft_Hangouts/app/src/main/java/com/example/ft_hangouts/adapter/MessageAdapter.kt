package com.example.ft_hangouts.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ft_hangouts.*

class MessageAdapter(private val id: Int) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val MESSAGE_SELF_VIEW = 0
    private val MESSAGE_OTHER_VIEW = 1
    private val user : User? = users[id]
    private val msgList = messages[id]

    inner class MessageViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindMessage(message: Messages?, user: User?) {
            val textView: TextView
            if (message != null) {
                with (message) {
                    textView =
                        if (sender == user?.phoneNumber)
                            itemView.findViewById<TextView>(R.id.otherMessageText)
                        else
                            itemView.findViewById<TextView>(R.id.selfMessageText)
                    textView.text = message.msgBody
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = if (msgList?.get(position)?.sender  == user?.phoneNumber )
        MESSAGE_OTHER_VIEW else MESSAGE_SELF_VIEW

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutId = if (viewType == MESSAGE_SELF_VIEW) R.layout.item_message_self
                        else R.layout.item_message_other

        val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bindMessage(msgList?.get(position), user)
    }

    override fun getItemCount(): Int {
        if (msgList != null) {
            return msgList.size
        }
        return 0
    }


}