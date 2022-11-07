package com.example.ft_hangouts

import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.ft_hangouts.databinding.ActivityAddUserBinding

class AddUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUserBinding
    private var proPic: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (intent?.extras?.getString("currentTheme", "")) {
            "Void" -> theme.applyStyle(R.style.Theme_Ft_Hangouts_void, true)
            "Sith" -> theme.applyStyle(R.style.Theme_Ft_Hangouts_sith, true)
            "Sayan" -> theme.applyStyle(R.style.Theme_Ft_Hangouts, true)
        }
        binding = ActivityAddUserBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.proPicBtn.setOnClickListener { addProPic() }
        binding.addUserBtn.setOnClickListener { userInstantiate() }

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 4242)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            proPic = data?.data
            contentResolver?.takePersistableUriPermission(
                proPic!!, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            if (proPic != null)
                binding.proPicBtn.setImageURI(proPic)
        }
    }

    private fun addProPic() {
        val gallery = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 100)
    }

    private fun userInstantiate() {
        val user = User(
            id++,
            binding.contactName.text.toString(),
            binding.phoneNumber.text.toString(),
            binding.emailText.text.toString(),
            proPic.toString(),
            binding.bday.text.toString()
        )
        addUsers(user)
        Toast.makeText(this,  user.Validate(), Toast.LENGTH_SHORT).show()
        if (user.isValid){
            finish()
        }
    }
}