package Extras

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.thunkbeta.R
import ThunkViewHolder
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ThunksAdapter(val context: Context,
                    val thunks: List<Thunk>): RecyclerView.Adapter<ThunkViewHolder>() {
    override fun getItemCount(): Int = thunks.size

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ThunkViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.thunk_view_holder, parent, false)
        val holder = ThunkViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ThunkViewHolder, i: Int) {
        holder.thunkContent.text = thunks[i].content

        val dt = Instant.ofEpochSecond(thunks[i].dateFetched).atZone(ZoneId.systemDefault()).toLocalDateTime()

        val dateAddedPrefix = context.getString(R.string.listSavedOnPrefix)
        holder.thunkDate.text = "$dateAddedPrefix ${dt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}"
    }
}