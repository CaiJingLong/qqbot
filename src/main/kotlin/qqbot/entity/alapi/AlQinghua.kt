package qqbot.entity.alapi

data class AlQinghua(
    val code: Int,
    val msg: String,
    val data: DataBean,
    val author: AuthorBean
) {

    data class DataBean(val content: String)

    data class AuthorBean(
        val name: String,
        val desc: String
    )
}