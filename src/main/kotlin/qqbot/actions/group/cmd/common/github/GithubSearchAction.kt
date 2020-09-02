package qqbot.actions.group.cmd.common.github

import net.mamoe.mirai.message.GroupMessageEvent
import okhttp3.HttpUrl.Companion.toHttpUrl
import qqbot.actions.interfaces.CmdAction
import qqbot.constants.Constants
import qqbot.entity.github.Repo
import qqbot.entity.github.SearchResult
import qqbot.loginConfig
import qqbot.utils.OKHttp
import qqbot.utils.toBean
import qqbot.utils.toGithubAuth

object GithubSearchAction : CmdAction {

    override fun supportGroupIds(): List<Long>? {
        return Constants.testGroups
    }

    override val prefix: String
        get() = "/github"

    override fun helperText(): String {
        return "/github 库名,  owner/repo 为强匹配, 没有则报错,  不包含repo为搜索"
    }

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        val keyword = params.trim()
        if (keyword.isEmpty()) {
            event.reply("啥都没有, 给你搜个寂寞?")
            return
        }

        try {
            keyword.filter { it == '/' }.apply {
                when {
                    count() == 1 -> {
                        showDetail(event, keyword)
                    }
                    count() > 2 -> {
                        event.reply("斜杠太多了, 这样不太行")
                    }
                    else -> {
                        showSearchResult(event, keyword)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private suspend fun showSearchResult(event: GroupMessageEvent, keyword: String) {
        val httpUrl = "https://api.github.com/search/repositories"
            .toHttpUrl()
            .newBuilder()
            .addQueryParameter("q", keyword)
            .build()

        val body = OKHttp.getString(httpUrl, loginConfig.toGithubAuth().toHeader())

        val searchResult = body.toBean<SearchResult>() ?: return

        val str = searchResult.items.subList(0, 10)
            .joinToString("\n\n") {
                """${it.name} ${it.html_url}
                        |star: ${it.stargazers_count}
                        |open issues: ${it.open_issues_count}
                """.trimMargin()
            }

        event.reply(str)
    }

    private suspend fun showDetail(event: GroupMessageEvent, keyword: String) {
        val httpUrl = "https://api.github.com/repos/"
            .toHttpUrl()
            .newBuilder()
            .addPathSegments(keyword)
            .build()

        val body = OKHttp.getString(httpUrl, loginConfig.toGithubAuth().toHeader())
        val repo = body.toBean<Repo>() ?: return
        repo.apply {
            event.reply(
                """$name $html_url
                    |说明: $description
                    |star: $stargazers_count
                    |open issues: $open_issues $issues_url
            """.trimMargin()
            )
        }
    }
}