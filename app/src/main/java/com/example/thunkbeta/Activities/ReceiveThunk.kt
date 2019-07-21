package com.example.thunkbeta.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import com.example.thunkbeta.R
import com.github.kittinunf.fuel.httpGet

import kotlinx.android.synthetic.main.activity_receive_thunk.*

class ReceiveThunk : AppCompatActivity() {

    val apiURL = "http://10.0.2.2:5000/thunk"

    private lateinit var getThunkButton: Button
    private lateinit var thunkView: TextView
    private lateinit var likeThunkButton: ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_thunk)
        setSupportActionBar(toolbar)
        getNewThunk()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getThunkButton = findViewById(R.id.getThunkButton)
        thunkView = findViewById(R.id.thunkView)
        likeThunkButton = findViewById(R.id.likeThunkButton)


        getThunkButton.setOnClickListener {
            getNewThunk()
        }

        likeThunkButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(this,"Thunk saved to favourites!", Toast.LENGTH_SHORT).show()
                val intent = Intent()
                intent.putExtra("ThunkText", thunkView.text.trim('"'))
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    fun getNewThunk() {
        apiURL.httpGet().response {
                request, response, result ->
            likeThunkButton.isChecked = false
            if (response.statusCode == 200) {
                val thunkContent = String(response.body().toByteArray())
                thunkView.text = "\"$thunkContent\""
            } else {
                Toast.makeText(this, "Failed to get Thunk", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
