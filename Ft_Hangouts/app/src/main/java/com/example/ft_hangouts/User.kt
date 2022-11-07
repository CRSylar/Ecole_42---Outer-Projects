package com.example.ft_hangouts

import android.graphics.Bitmap
import android.media.Image
import java.io.Serializable
import java.util.*

data class User(val id: Int,
                var name: String,
                var phoneNumber: String,
                var email: String,
                var proPic: String? = null,
                var birthDay: String? = null) : Serializable {

    val errorMsgUs: String = "Error: Required Field(s) cannot be empty"
    val errorMsgIt: String = "Errore: Campi Obbligatori non possono essere vuoti"

    private var _isValid: Boolean = false
    val isValid: Boolean
        get() = _isValid

    fun Validate() :String {
        if (name.isEmpty() || phoneNumber.isEmpty())
        {
            if (Locale.getDefault().toString() == "it_IT")
                return errorMsgIt
            return errorMsgUs
        }
        _isValid = true
        if (Locale.getDefault().toString() == "it_IT")
            return "$name Aggiunto"
        return "$name Added"
    }

    fun editValidate() :String {
        if (name.isEmpty() || phoneNumber.isEmpty())
        {
            if (Locale.getDefault().toString() == "it_IT")
                return errorMsgIt
            return errorMsgUs
        }
        _isValid = true
        if (Locale.getDefault().toString() == "it_IT")
            return "$name Modificato"
        return "$name Edited"
    }
}
