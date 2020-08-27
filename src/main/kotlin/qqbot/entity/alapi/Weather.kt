package qqbot.entity.alapi

data class Weather(
    val code: Int,
    val msg: String,
    val data: DataBean,
    val author: AuthorBean
) {

    data class DataBean(
        val cityid: String,
        val date: String,
        val week: String,
        val update_time: String,
        val city: String,
        val cityEn: String,
        val country: String,
        val countryEn: String,
        val wea: String,
        val wea_img: String,
        val tem: String,
        val tem1: String,
        val tem2: String,
        val win: String,
        val win_speed: String,
        val win_meter: String,
        val humidity: String,
        val visibility: String,
        val pressure: String,
        val air: String,
        val air_pm25: String,
        val air_level: String,
        val air_tips: String,
        val alarm: AlarmBean
    ) {

        data class AlarmBean(
            val alarm_type: String,
            val alarm_level: String,
            val alarm_content: String
        )
    }

    data class AuthorBean(
        val name: String,
        val desc: String
    )
}