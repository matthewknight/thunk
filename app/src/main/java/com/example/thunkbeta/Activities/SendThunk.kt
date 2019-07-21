package com.example.thunkbeta.Activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.thunkbeta.R
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost

import kotlinx.android.synthetic.main.activity_send_thunk.*
import org.json.JSONObject


class SendThunk : AppCompatActivity() {

    val apiURL = "http://10.0.2.2:5000/thunk"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_thunk)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sendThunkButton: Button = findViewById(R.id.sendThunkButton)
        val thunkContent: TextView = findViewById(R.id.thunkEditor)

        sendThunkButton.setOnClickListener {
            uploadThunk(thunkContent.text.toString())
        }
    }

    fun uploadThunk(thunkContent: String) {
        val bodyJson = JSONObject()
        bodyJson.put("thunkContent", thunkContent.trim())

        apiURL.httpPost()
            .body(bodyJson.toString())
            .header(mapOf("Content-Type" to "application/json"))
            .response {
                request, response, result ->
            if (response.statusCode == 201) {
                Toast.makeText(this, "Thunk deposited", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to send Thunk", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
