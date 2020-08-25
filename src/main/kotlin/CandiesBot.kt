import actions.group.HelpAction
import actions.group.cmd.admin.MuteAction
import actions.group.cmd.admin.MuteAllAction
import actions.group.cmd.admin.UnMuteAction
import actions.group.at.AutoReplyAction
import actions.group.cmd.common.GirlAction
import actions.group.cmd.common.weather.WeatherAction
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.long
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.event.*
import net.mamoe.mirai.join
import java.io.FileReader

suspend fun main() {
    val json = FileReader("config.json").use {
        Json.parseJson(it.readText()).jsonObject
    }

    val qq = json["qq"]?.long

    val password = json["password"]?.primitive?.content

    checkNotNull(qq)
    checkNotNull(password)

    val bot = Bot( // JVM 下也可以不写 `QQAndroid.` 引用顶层函数
        qq,
        password
    ) {
        // 覆盖默认的配置
        fileBasedDeviceInfo("device.json") // 使用 "device.json" 保存设备信息
        // networkLoggerSupplier = { SilentLogger } // 禁用网络层输出
    }.alsoLogin()

    registerActions()

    bot.messageDSL()
    bot.join()//等到直到断开连接
}

/**
 * 在这里提供所有的action
 */
private val cmdActions = arrayOf(
    HelpAction,
//    PubCmdAction,
    WeatherAction,
    GirlAction,
    MuteAllAction,
    MuteAction,
    UnMuteAction,
)

private val atActions = arrayOf(
    AutoReplyAction
)

/**
 * 注册方法
 */
private fun registerActions() {
    for (action in cmdActions) {
        HelpAction.registerAction(action)
    }
}

/**
 * 使用 dsl 监听消息事件
 *
 * @see subscribeFriendMessages
 * @see subscribeMessages
 * @see subscribeGroupMessages
 * @see subscribeTempMessages
 *
 * @see MessageSubscribersBuilder
 */
fun Bot.messageDSL() {

    subscribeGroupMessages {
        for (action in HelpAction.actions) {
            startsWith(action.prefix, onEvent = action::invoke)

            if (action.prefixAlias().isNotEmpty()) {
                for (prefixAlias in action.prefixAlias()) {
                    startsWith(prefixAlias, onEvent = action::invoke)
                }
            }
        }

        for (action in atActions) {
            atBot {
                action.invoke(this)
            }
        }

        always {
//            OnSenderTalkAction.invoke(this)
        }

    }
}