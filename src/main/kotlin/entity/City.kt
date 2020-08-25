package entity

data class City(
    val id: Int,
    val name: String,
    val state: String,
    val country: String,
    val coord: CoordBean
) {

    data class CoordBean(
        val lon: Double,
        val lat: Double
    )
}