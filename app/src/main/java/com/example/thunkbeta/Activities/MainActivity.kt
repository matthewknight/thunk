package com.example.thunkbeta.Activities

import Extras.Thunk
import Extras.ThunkItemClickListener
import Extras.ThunksAdapter
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.thunkbeta.R
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import java.io.InputStreamReader
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var thunkRecyclerView: RecyclerView
    lateinit var listAdapter: ThunksAdapter
    var newThunk: Thunk? = null

    var savedThunks: MutableList<Thunk> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sendThunkButton: Button = findViewById(R.id.sendThunkButton)
        val receiveThunkButton: Button = findViewById(R.id.receiveThunkButton)
        val thunkEmoji: ImageView = findViewById(R.id.thunkEmoji)

        thunkRecyclerView = findViewById(R.id.thunkRecyclerView)

        val layoutManager = LinearLayoutManager(this)
        thunkRecyclerView.layoutManager = layoutManager

        listAdapter = ThunksAdapter(this, savedThunks)
        thunkRecyclerView.adapter = listAdapter

        sendThunkButton.setOnClickListener {
            val intent = Intent(this, SendThunk::class.java)
            startActivity(intent)
        }

        receiveThunkButton.setOnClickListener {
            val intent = Intent(this, ReceiveThunk::class.java)
            startActivityForResult(intent, 1)
        }

        thunkRecyclerView.addOnItemTouchListener(ThunkItemClickListener(this, thunkRecyclerView, object : ThunkItemClickListener.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                // Initialize a new instance of
                val tappedThunkModal = AlertDialog.Builder(this@MainActivity)
                tappedThunkModal.setMessage(savedThunks.get(position).content)

                tappedThunkModal.setPositiveButton("Delete"){ _, _ ->
                    savedThunks.removeAt(position)
                    listAdapter.notifyDataSetChanged()
                }

                tappedThunkModal.setNegativeButton("Share"){ _, _ ->
                    val message: Intent = Intent( Intent.ACTION_VIEW, Uri.parse( "sms:" + "" ))
                    message.putExtra( "sms_body", savedThunks.get(position).content)
                    startActivity(message)
                }

                tappedThunkModal.setNeutralButton("Cancel"){ _, _ -> }
                val dialog: AlertDialog = tappedThunkModal.create()
                dialog.show()
            }
            override fun onItemLongClick(view: View?, position: Int) {}
        }))


        val rotateAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )

        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.duration = 1300
        rotateAnimation.repeatCount = Animation.INFINITE

        thunkEmoji.startAnimation(rotateAnimation)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            val unixTime: Long = System.currentTimeMillis() / 1000
            val newlySavedThunk = Thunk(data.getStringExtra("ThunkText"), unixTime)
            newThunk = newlySavedThunk
        }
    }

    override fun onStart() {
        try {
            savedThunks.clear()
            super.onStart()
            val file = openFileInput("saved_thunks.json")
            val reader = JsonReader(InputStreamReader(file))

            if (newThunk == null) {
                savedThunks.addAll(Gson().fromJson<Array<Thunk>>(reader, Array<Thunk>::class.java))
            } else {
                savedThunks.addAll(Gson().fromJson<Array<Thunk>>(reader, Array<Thunk>::class.java))
                savedThunks.add(newThunk!!)
                newThunk = null
            }

            savedThunks.sortByDescending { it.dateFetched }
            listAdapter.notifyDataSetChanged()
        } catch (e: Exception) { }
    }

    override fun onStop() {
        super.onStop()
        val file = openFileOutput("saved_thunks.json", Context.MODE_PRIVATE)
        val jsonisedThunks = Gson().toJson(savedThunks)
        file.write(jsonisedThunks.toByteArray())

        try {
            Log.d("Buzz", savedThunks.get(0).content)
        } catch (e: Exception) { }

    }
}
