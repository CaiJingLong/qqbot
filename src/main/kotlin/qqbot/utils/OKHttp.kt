package qqbot.utils

import okhttp3.*
import qqbot.entity.LoginConfig
import java.util.*

object OKHttp {

    data class GithubAuth(val githubName: String, val githubToken: String) {
        fun toHeader(): Headers {
            val name = "$githubName:$githubToken"
            val base64Result = Base64.getEncoder().encodeToString(name.toByteArray())

            return Headers.Builder()
                .add("authorization", "Basic $base64Result")
                .build()
        }
    }

    private val client = OkHttpClient.Builder()
        .build()

    fun getString(url: String, headers: Headers? = null): String {
        val request = Request.Builder()
            .url(url)
            .apply {
                if (headers != null) {
                    headers(headers)
                }
            }
            .build()

        val response = client.newCall(request)
            .execute()

        return response.body!!.string()
    }

    fun getString(url: HttpUrl, headers: Headers? = null): String {
        return getString(url.toString(), headers)
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

fun LoginConfig.toGithubAuth(): OKHttp.GithubAuth {
    return OKHttp.GithubAuth(githubName, githubToken)
}