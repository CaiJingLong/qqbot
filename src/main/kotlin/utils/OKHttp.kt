package utils

import okhttp3.OkHttpClient
import okhttp3.Request

object OKHttp {

    private val client = OkHttpClient.Builder()
        .build()

    fun getString(url: String): String {
        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request)
            .execute()

        return response.body!!.string()
    }
}
