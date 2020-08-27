package qqbot.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


inline fun <reified T> String.toBean(): T? {
    return moshi.adapter(T::class.java).fromJson(this)
}