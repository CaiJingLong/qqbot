package qqbot.entity.alapi

data class Soul(
    val code: Int,
    val msg: String,
    val data: DataBean,
    val author: AuthorBean
) {

    data class DataBean(val title: String)

    data class AuthorBean(
        val name: String,
        val desc: String
    )
}