package com.bruno.startup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            if(editText.text.toString().isNotEmpty()){
                val userYob = Integer.parseInt(editText.text.toString())
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)

                val age = currentYear - userYob

                txt.text = "Your age is: ${age.toString()}"
            }
            else{
                txt.text = "Hello World!"
            }
        }
    }
}
