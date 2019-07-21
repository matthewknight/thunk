package Extras

import android.app.Activity
import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import java.lang.ref.WeakReference
import java.net.URL
import java.nio.charset.Charset
//
//class ThunkDownloader(val callingActivity: Activity, val thunkView: TextView) : AsyncTask<Void, Void, AsyncTaskResult<Thunk>>() {
//    override fun doInBackground(vararg params: Void): AsyncTaskResult<Thunk> {
//        val getResult: AsyncTaskResult<Thunk> = AsyncTaskResult()
//        try {
//            val parsedThunk = Thunk(URL("http://10.0.2.2:5000/thunk").readText(Charset.defaultCharset()))
//            Log.d("buzz", parsedThunk.content)
//            getResult.result = parsedThunk
//        }
//        catch (e: Exception) {
//            getResult.exception = e
//        }
//        return getResult
//    }
//
//    override fun onPostExecute(getResult: AsyncTaskResult<Thunk>) {
//        super.onPostExecute(getResult)
//
//        if (getResult.hasException()) {
//            Toast.makeText(callingActivity, "Failed to get Thunk", Toast.LENGTH_SHORT).show()
//        } else {
//            thunkView.text = getResult.result.content
//        }
//    }
//}