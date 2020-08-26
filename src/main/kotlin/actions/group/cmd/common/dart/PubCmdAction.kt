package actions.group.cmd.common.dart

import HTMLParser
import actions.interfaces.CmdAction
import net.mamoe.mirai.message.GroupMessageEvent
import java.net.URL

object PubCmdAction : CmdAction {

    override val prefix: String = "/pub"

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        val pkgUrl = URL("https://pub.flutter-io.cn/packages?q=$params")
        val html = pkgUrl.readText()
        val packages = HTMLParser.getElementsByClass(html, "packages-item")
        if (packages.size > 0) {
            val p = packages.first();

            val name = HTMLParser.getElementsByClass(p.html(), "packages-title")[0].child(0).text()

            val link =
                "https://pub.flutter-io.cn" + HTMLParser.getElementsByClass(p.html(), "packages-title")[0].child(0)
                    .attr("href")

            val scoreHtml = HTMLParser.getElementsByClass(p.html(), "packages-scores")[0]
            val scores = HTMLParser.getElementsByClass(scoreHtml.html(), "packages-score-value-number")

            val description = HTMLParser.getElementsByClass(p.html(), "packages-description").text()

            val version = HTMLParser.getElementsByClass(p.html(), "packages-metadata-block")[0].child(0).text()

            val time = HTMLParser.getElementsByClass(p.html(), "packages-metadata-block")[0].child(1).text()

            val platforms = HTMLParser.getElementsByClass(p.html(), "-pub-tag-badge")

            val s = StringBuilder()
            s.appendLine("包名 ：$name")
            s.appendLine("链接 ：$link")
            s.appendLine("喜欢 ：${scores[0].text()}")
            s.appendLine("Pub Point ：${scores[1].text()}/110")
            s.appendLine("流行度 ：${scores[2].text()}")
            s.appendLine("描述 ：$description")
            s.appendLine("版本 ：$version")
            s.appendLine("时间 ：$time")
            platforms.map {
                val main = HTMLParser.getElementsByClass(it.html(), "tag-badge-main")
                val sub = HTMLParser.getElementsByClass(it.html(), "tag-badge-sub")
                s.appendLine("${main[0].text()} ：${sub.text()}")
            }
            event.reply(s.trim().toString())
        }
    }

    override fun helperText(): String {
        return "/pub 包名: 查看pub 包的信息"
    }
}