package com.example.ft_hangouts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ft_hangouts.adapter.MessageAdapter
import com.example.ft_hangouts.databinding.ActivityContactCardBinding

class ContactCardActivity: AppCompatActivity() {

    private lateinit var binding: ActivityContactCardBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (intent?.extras?.getString("currentTheme", "")) {
            "Void" -> theme.applyStyle(R.style.Theme_Ft_Hangouts_void, true)
            "Sith" -> theme.applyStyle(R.style.Theme_Ft_Hangouts_sith, true)
            "Sayan" -> theme.applyStyle(R.style.Theme_Ft_Hangouts, true)
        }
        if (RECEIVE_SMS_PERMISSION == 1)
            receiveMsg()
    }

    override fun onStart() {
        super.onStart()

        user =  intent?.getSerializableExtra("User") as User
        binding = ActivityContactCardBinding.inflate(layoutInflater)
        title = user.name

        if (user.proPic.toString() != "null"){
            try {
                binding.userImg?.setImageURI(Uri.parse(user.proPic))
            } catch (e: Error) {
            }
        }

        if (!(messages.containsKey(user.id)))
            messages[user.id] = mutableListOf<Messages>()


        setContentView(binding.root)

        recyclerView = binding.contactCardRecView
        recyclerView.adapter = MessageAdapter(user.id)

        val idx = messages[user.id]!!.size.minus(1)
        if (idx > -1)
            recyclerView.layoutManager?.scrollToPosition(idx)

        binding.infoBtn?.setOnClickListener { showInfoActivity() }
        binding.editContact?.setOnClickListener { editActivitySwitcher() }
        binding.rmvContact?.setOnClickListener { deleteContact() }
        binding.callContact?.setOnClickListener { callContact() }
        binding.sendBtn.setOnClickListener { sendSms() }
        binding.textSmsBox.setOnKeyListener(View.OnKeyListener{_, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                binding.sendBtn.performClick()
                return@OnKeyListener true
            }
            false
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun showInfoActivity() {
        val intento = Intent(this, ShowInfoActivity::class.java)
        intento.putExtra("currentTheme", intent?.extras?.getString("currentTheme", ""))
        intento.putExtra("User", user)
        startActivity(intento)
    }

    private fun deleteContact() {
        users.remove(user.id)
        finish()
    }

    private fun callContact() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:${user.phoneNumber}")
            startActivity(intent)
        }
        else
            Toast.makeText(this, "You don't have permission to Dial", Toast.LENGTH_LONG).show()
    }

    private fun sendSms() {
        val smsText =  binding.textSmsBox.text.toString()

        if (smsText.isNotEmpty() &&
                ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            SmsManager.getDefault().sendTextMessage(
                user.phoneNumber, null, smsText,
                null, null
            )
            binding.textSmsBox.setText("")

            messages[user.id]!!.add(Messages("Me", smsText))

            val idx = messages[user.id]!!.size - 1
            recyclerView.layoutManager?.scrollToPosition(idx)
            recyclerView.adapter?.notifyItemInserted(idx)
        }
        else if (smsText.isNotEmpty())
            Toast.makeText(this, "You don't have permission to send SMS", Toast.LENGTH_LONG).show()
    }


    private fun receiveMsg() {
        val br = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                for (sms in Telephony.Sms.Intents.getMessagesFromIntent(p1)) {
                    val idx = messages[user.id]!!.size - 1
                    recyclerView.layoutManager?.scrollToPosition(idx)
                    recyclerView.adapter?.notifyItemInserted(idx)
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }


    private fun editActivitySwitcher() {
        val intento = Intent(this, EditUserActivity::class.java)
        intento.putExtra("currentTheme", intent?.extras?.getString("currentTheme", ""))
        intento.putExtra("User", user)
        finish()
        startActivity(intento)
    }
}

