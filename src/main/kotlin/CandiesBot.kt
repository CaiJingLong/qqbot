import actions.group.HelpAction
import actions.group.at.AutoReplyAction
import actions.group.cmd.admin.KickAction
import actions.group.cmd.admin.MuteAction
import actions.group.cmd.admin.MuteAllAction
import actions.group.cmd.admin.UnMuteAction
import actions.group.cmd.common.GirlAction
import actions.group.cmd.common.alapi.AcgAction
import actions.group.cmd.common.alapi.DogAction
import actions.group.cmd.common.alapi.QinghuaAction
import actions.group.cmd.common.alapi.SoulAction
import actions.group.cmd.common.weather.WeatherAction
import entity.LoginConfig
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.event.*
import net.mamoe.mirai.join
import utils.moshi
import java.io.FileReader


suspend fun main() {

    val json = FileReader("config.json").use {
//        Json.decodeFromString<LoginConfig>(it.readText())
        val adapter = moshi.adapter(LoginConfig::class.java)
        adapter.fromJson(it.readText())
    } ?: throw RuntimeException("登录失败")

    val (qq, password) = json

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

    // 日常小功能
    WeatherAction,
    GirlAction,
    SoulAction,
    QinghuaAction,
    DogAction,
    AcgAction,

    /// 管理相关的
    MuteAllAction,
    MuteAction,
    UnMuteAction,
    KickAction,
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