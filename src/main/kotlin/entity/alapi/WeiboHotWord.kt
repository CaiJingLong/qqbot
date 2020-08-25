package entity.alapi

data class WeiboHotWord(
    val code: Int,
    val msg: String,
    val data: List<DataBean>,
    val author: AuthorBean
) {

    data class DataBean(
        val hot_word: String,
        val hot_word_num: String
    )

    data class AuthorBean(
        val name: String,
        val desc: String
    )
}