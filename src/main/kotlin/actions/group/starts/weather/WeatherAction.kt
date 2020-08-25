package actions.group.starts.weather

import actions.interfaces.CmdAction
import entity.Weather
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.*
import net.mamoe.mirai.message.GroupMessageEvent
import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType
import utils.moshi
import java.io.FileReader
import java.net.URLEncoder
import kotlin.math.roundToInt

object WeatherAction : CmdAction {
    override val prefix: String
        get() = "/天气"

    override fun helperText(): String {
        return "/天气 城市"
    }

    private var isLoad = false

    private val map = HashMap<String, Long>()

    @UnstableDefault
    private fun loadJson() {
        if (isLoad) {
            return
        }
        isLoad = true
        val text = FileReader("city.list.json").readText()
        val jsonElement = Json.parseJson(text).jsonArray

        for (element in jsonElement) {
            element.jsonObject["name"]?.primitive?.content?.let {
                val id = element.jsonObject["id"]?.double?.toLong()
                if (id != null) {
                    map.putIfAbsent(it.toLowerCase(), id)
                }
            }
        }
    }

    @UnstableDefault
    @ImplicitReflectionSerializer
    override suspend fun invoke(event: GroupMessageEvent, params: String) {
        if (params.trim().isBlank()) {
            event.quoteReply("请输入城市名称")
            return
        }

        val cityName = params.trim().toLowerCase()

//        if (params.startsWith("query")) {
//            val cmd = params.removePrefix("query")
//        }

        loadJson()

        println("map 中共有 ${map.count()}个元素")

        val weather = try {

            val url =
                if (map[cityName] != null) {
                    id(map[cityName]!!)
                } else if (cityName.toLongOrNull() != null) {
                    id(cityName.toLong())
                } else {
                    city(params)
                }
            println("尝试请求 $url")
            val str = httpClient.get<String>(url)
            val adapter = moshi.adapter(Weather::class.java)
            adapter.fromJson(str) ?: return
        } catch (e: Exception) {
            e.printStackTrace()
            try {
                val url = city(convertToChinesePinyin(params))
                println("尝试请求 $url")
                val str = httpClient.get<String>(url)
                val adapter = moshi.adapter(Weather::class.java)
                adapter.fromJson(str) ?: return
            } catch (e: Exception) {
                event.quoteReply("没找到对应城市信息")
                return
            }
        }

        val s = StringBuilder()
        val weatherBean = weather.weather[0]
        val (temp, _, temp_min, temp_max, _, _) = weather.main
        s.append("${weather.name}, ${weatherBean.description}")
        s.append("\n")
        s.append("${temp_min.fixTemp()}~${temp_max.fixTemp()}, 当前气温: ${temp.fixTemp()}")
        s.append("\n")
        s.append("经纬度:${weather.coord.lat},${weather.coord.lon}")

        event.quoteReply(s.trim().toString())
    }

    private fun convertToChinesePinyin(city: String): String {

        val hanyuPinyinOutputFormat = HanyuPinyinOutputFormat().apply {
            toneType = HanyuPinyinToneType.WITHOUT_TONE
        }
        return PinyinHelper.toHanYuPinyinString(city, hanyuPinyinOutputFormat, "", true)
    }

    private fun city(cityName: String): Url {
        val url = URLBuilder(parameters = ParametersBuilder().apply {
            this["q"] = URLEncoder.encode(cityName, "utf8").replace("+", "%20")
            this["appid"] = "cd2bfe940bb257a9d3c94310a45cf9f3"
            this["lang"] = "zh_cn"

        })
            .takeFrom("https://api.openweathermap.org/data/2.5/weather")
            .build()
        return url
    }


    private fun id(id: Long): Url {
        val url = URLBuilder(parameters = ParametersBuilder().apply {
            this["id"] = id.toString()
            this["appid"] = "cd2bfe940bb257a9d3c94310a45cf9f3"
            this["lang"] = "zh_cn"

        })
            .takeFrom("https://api.openweathermap.org/data/2.5/weather")
            .build()
        return url
    }


    private fun Double.fixTemp(): String {
        return (this - 272.15).roundToInt().toString()
    }
}