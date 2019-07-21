package Extras

import android.util.Log
import com.github.kittinunf.fuel.httpGet
import java.nio.charset.Charset

class ThunkAPI {

    val apiURL = "http://10.0.2.2:5000/thunk"

    fun getThunk(): String {
        apiURL.httpGet().response {
            request, response, result ->
            Log.d("buzz", String(response.body().toByteArray()))
        }
        return "test"
    }

    fun postThunk(thunkToSend: String) {

    }
}