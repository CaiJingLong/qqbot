package entity

data class Hello(
    val code: Int,
    val min_id: Int,
    val msg: String,
    val data: List<DataBean>
) {

    data class DataBean(
        val imgsrc: String,
        val content: String,
        val likenum: String,
        val categoryid: Int
    )
}