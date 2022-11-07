package com.example.ft_hangouts

import java.io.Serializable

data class Messages(val sender: String = "",
                    val msgBody: String = "") : Serializable   {

}
