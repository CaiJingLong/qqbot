package utils

import okhttp3.OkHttpClient
import okhttp3.Request

object OKHttp {

    val client = OkHttpClient.Builder()
        .build()

    fun getString(url: String): String {
        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request)
            .execute()

        return response.body!!.string()
    }

    fun getLocationUrl(url: String): String {
        val newClient = client.newBuilder()
            .followRedirects(false)
            .build()

        val req = Request.Builder()
            .url(url)
            .build()
        val response = newClient.newCall(req)
            .execute()

        return response.header("Location", "")!!
    }
}
