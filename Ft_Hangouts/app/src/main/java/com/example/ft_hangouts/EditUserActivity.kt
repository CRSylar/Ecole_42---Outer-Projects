package com.example.ft_hangouts

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.ft_hangouts.databinding.ActivityEditUserBinding

class EditUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditUserBinding
    private lateinit var user: User
    private var proPic: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user =  intent?.getSerializableExtra("User") as User
        when (intent?.extras?.getString("currentTheme", "")) {
            "Void" -> theme.applyStyle(R.style.Theme_Ft_Hangouts_void, true)
            "Sith" -> theme.applyStyle(R.style.Theme_Ft_Hangouts_sith, true)
            "Sayan" -> theme.applyStyle(R.style.Theme_Ft_Hangouts, true)
        }
        binding = ActivityEditUserBinding.inflate(layoutInflater)

        binding.contactName.setText(user.name)
        binding.phoneNumber.setText(user.phoneNumber)
        binding.emailText.setText(user.email)
        binding.bday.setText(user.birthDay)
        if (user.proPic.toString() != "null"){
            try {
                binding.proPicBtn.setImageURI(Uri.parse(user.proPic))
            } catch (e: Error) {
                Log.d("PORCO", e.toString())
            }
        }

        setContentView(binding.root)

        binding.editUserBtn.setOnClickListener { editUser() }
        binding.proPicBtn.setOnClickListener { editProPic() }

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 4242)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            proPic = data?.data
            contentResolver?.takePersistableUriPermission(
                proPic!!, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            if (proPic != null){
                users[user.id]!!.proPic = proPic.toString()
                binding.proPicBtn.setImageURI(proPic)
            }
        }
    }

    private fun editProPic() {
        val gallery = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 100)
    }

    private fun editUser() {
        users[user.id]!!.name = binding.contactName.text.toString()
        users[user.id]!!.phoneNumber = binding.phoneNumber.text.toString()
        users[user.id]!!.email = binding.emailText.text.toString()

        Toast.makeText(this,  user.editValidate(), Toast.LENGTH_SHORT).show()
        if (user.isValid){
            finish()
        }
    }
}