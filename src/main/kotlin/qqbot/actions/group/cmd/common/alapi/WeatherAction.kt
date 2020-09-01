package qqbot.actions.group.cmd.common.alapi

import io.ktor.http.*
import net.mamoe.mirai.message.GroupMessageEvent
import qqbot.constants.Constants
import qqbot.entity.alapi.Weather
import qqbot.utils.OKHttp
import qqbot.utils.toBean

object WeatherAction : ALCmdAction {
    override val prefix: String
        get() = "/天气"

    override fun helperText(): String {
        return "/天气 城市"
    }

    override fun supportGroupIds(): List<Long>? {
        return Constants.all
    }

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        if (params.trim().isEmpty()) {
            event.quoteReply("城市是必填项")
            return
        }

        val url = URLBuilder(
            parameters = ParametersBuilder().apply {
                append("city", params.trim())
            }
        )
            .takeFrom("https://v1.alapi.cn/api/tianqi/now")
            .buildString()

        val response = OKHttp.getString(url)

        val weather = response.toBean<Weather>()

        if (weather?.data == null) {
            event.quoteReply("暂不支持该城市")
            return
        }

        if (weather.data.city != params) {
            event.quoteReply("暂不支持该城市")
            return
        }

        weather.data.apply {
            val msg = """
                $city, $date $week
                $tem2 ~ $tem1 , 当前气温 $tem
                $win, $win_speed
                空气质量: $air_level, PM2.5 $air_pm25 
            """.trimIndent()
            event.quoteReply(msg)
        }

    }
}