package com.example.ft_hangouts

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ft_hangouts.databinding.ActivityShowInfoBinding

class ShowInfoActivity: AppCompatActivity() {

    private lateinit var binding: ActivityShowInfoBinding
    private lateinit var user: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user =  intent?.getSerializableExtra("User") as User
        when (intent?.extras?.getString("currentTheme", "")) {
            "Void" -> theme.applyStyle(R.style.Theme_Ft_Hangouts_void, true)
            "Sith" -> theme.applyStyle(R.style.Theme_Ft_Hangouts_sith, true)
            "Sayan" -> theme.applyStyle(R.style.Theme_Ft_Hangouts, true)
        }
        binding = ActivityShowInfoBinding.inflate(layoutInflater)

        binding.contactName.text = user.name
        binding.phoneNumber.text = user.phoneNumber
        binding.emailText.text = user.email
        binding.bday.text = user.birthDay
        if (user.proPic.toString() != "null"){
            try {
                binding.userImg.setImageURI(Uri.parse(user.proPic))
            } catch (e: Error) {
                Log.d("PORCO", e.toString())
            }
        }

        setContentView(binding.root)

        binding.backBtn.setOnClickListener{ closeActiviy()}
    }

    private fun closeActiviy() {
        finish()
    }
}