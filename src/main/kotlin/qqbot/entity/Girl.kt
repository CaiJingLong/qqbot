package qqbot.entity

data class Girl(
    val counts: Int,
    val data: List<DataBean>,
    val status: Int
) {

    data class DataBean(
        val _id: String,
        val author: String,
        val category: String,
        val createdAt: String,
        val desc: String,
        val images: List<String>,
        val likeCounts: Int,
        val publishedAt: String,
        val stars: Int,
        val title: String,
        val type: String,
        val url: String,
        val views: Int
    )
}