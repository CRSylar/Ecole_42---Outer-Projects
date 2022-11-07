package com.example.ft_hangouts

import android.content.*
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.ft_hangouts.adapter.ItemAdapter
import com.example.ft_hangouts.database.MyDbHelper
import com.example.ft_hangouts.databinding.ActivityMainBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

var users = mutableMapOf<Int, User>()
val messages = mutableMapOf<Int, MutableList<Messages> >()
var id : Int = 0
var RECEIVE_SMS_PERMISSION: Int = 0

fun addUsers(user: User) {
    val test = user.Validate()
    if (test != user.errorMsgUs && test != user.errorMsgIt)
        users[user.id] = user
}


class MainActivity : AppCompatActivity(), LifecycleObserver {

    lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding : ActivityMainBinding
    private lateinit var dbRead : SQLiteDatabase
    private lateinit var dbWrite : SQLiteDatabase


    private val themeKey = "currentTheme"
    private val timeKey = "savedTime"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        var helper = MyDbHelper(applicationContext)
        dbRead = helper.readableDatabase
        dbWrite = helper.writableDatabase

        sharedPreferences = getSharedPreferences(
            "ThemePref", Context.MODE_PRIVATE
        )
        when (sharedPreferences.getString(themeKey, "Sayan")) {
            "Void" -> theme.applyStyle(R.style.Theme_Ft_Hangouts_void, true)
            "Sith" -> theme.applyStyle(R.style.Theme_Ft_Hangouts_sith, true)
            "Sayan" -> theme.applyStyle(R.style.Theme_Ft_Hangouts, true)
        }

        if ((ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) ||
            (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) ||
            (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED))
                {
            ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.CALL_PHONE), 42)
        }
        else{
            RECEIVE_SMS_PERMISSION = 1
            receiveMsg()
        }

        users.clear()
        users = helper.getAll()
        id = users.size
        Log.d("PORCOid", id.toString())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 42 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            RECEIVE_SMS_PERMISSION = 1
            receiveMsg()
        }
    }

    private fun receiveMsg() {
        val br = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                for (sms in Telephony.Sms.Intents.getMessagesFromIntent(p1)) {
                    val found: Set<Int> = users.filterValues {  it.phoneNumber == sms.originatingAddress }.keys

                    if (found.isNotEmpty()) {
                        if (messages.containsKey(found.first()))
                            messages[found.first()]!!
                                .add(Messages(sms.originatingAddress.toString(),
                                    sms.displayMessageBody))
                        else
                            messages[found.first()] = mutableListOf<Messages>(
                                Messages(sms.originatingAddress.toString(),
                                    sms.displayMessageBody))
                    }
                    else {
                        users[id] = User(
                            id,
                            sms.originatingAddress.toString(),
                            sms.originatingAddress.toString(),
                            "",
                            null,
                            null)
                        if (messages.containsKey(id))
                            messages[id]!!
                                .add(Messages(sms.originatingAddress.toString(),
                                    sms.displayMessageBody))
                        else
                            messages[id] = mutableListOf<Messages>(
                                Messages(sms.originatingAddress.toString(),
                                    sms.displayMessageBody))
                        id++
                        val idx = users.size - 1
                        recyclerView.adapter?.notifyItemInserted(idx)
                    }
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }


    override fun onStart() {
        super.onStart()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        recyclerView.adapter = ItemAdapter(sharedPreferences)

        binding.addUserBtn.setOnClickListener{ addUserActivitySwitch() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
        sharedPreferences.edit().putString(timeKey,
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("M/d/y H:m:ss"))
                .toString()).apply()
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeGround() {
        val toast = getSharedPreferences("ThemePref", Context.MODE_PRIVATE).getString(timeKey, "")
        if (!toast.isNullOrEmpty())
            Toast.makeText(this, toast , Toast.LENGTH_LONG).show()
        getSharedPreferences("ThemePref", Context.MODE_PRIVATE).edit().remove(timeKey).apply()
    }


    override fun onStop() {
        super.onStop()
        dbRead.execSQL("DELETE FROM USERS")
        users.map {
            Log.d("PORCOuffa", it.value.name)
            val cv = ContentValues()
            cv.put("ID", it.value.id)
            cv.put("NAME", it.value.name)
            cv.put("PHONENUMBER", it.value.phoneNumber)
            cv.put("EMAIL", it.value.email)
            cv.put("PROPIC", it.value.proPic)
            cv.put("BIRTHDAY", it.value.birthDay)
            dbRead.insert("USERS", null, cv)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.layout_menu, menu)

        return true
    }

    private fun addUserActivitySwitch() {
        val intent = Intent(this, AddUserActivity::class.java)

        intent.putExtra(themeKey, sharedPreferences.getString(themeKey, ""))
        startActivity(intent)
    }

    fun changeTheme(item: android.view.MenuItem) {
        when (item.toString()) {
            "Void" -> sharedPreferences.edit().putString( themeKey, "Void" ).apply()
            "Sith" -> sharedPreferences.edit().putString( themeKey, "Sith" ).apply()
            "Sayan" -> sharedPreferences.edit().putString(themeKey, "Sayan" ).apply()
        }
        val intent = intent
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        startActivity(intent)
    }
}