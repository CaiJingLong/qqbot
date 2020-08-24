package entity

data class Weather(
    val coord: CoordBean,
    val weather: List<WeatherBean>,
    val base: String,
    val main: MainBean,
    val visibility: Int,
    val wind: WindBean,
    val clouds: CloudsBean,
    val dt: Int,
    val id: Int,
    val name: String,
    val cod: Int
) {

    data class CoordBean(
        val lon: Double,
        val lat: Double
    )

    data class WeatherBean(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
    )

    data class MainBean(
        val temp: Double,
        val feels_like: Double,
        val temp_min: Double,
        val temp_max: Double,
        val pressure: Int,
        val humidity: Int
    )

    data class WindBean(
        val speed: Double,
        val deg: Int
    )

    data class CloudsBean(val all: Int)

}
