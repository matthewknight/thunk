import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.thunkbeta.R

class ThunkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val thunkContent: TextView = view.findViewById(R.id.thunkItemText)
    val thunkDate: TextView = view.findViewById(R.id.thunkItemDate)
}