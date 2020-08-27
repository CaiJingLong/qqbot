package qqbot

import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.closeAndJoin
import net.mamoe.mirai.event.*
import net.mamoe.mirai.join
import qqbot.actions.group.HelpAction
import qqbot.actions.group.at.AutoReplyAction
import qqbot.actions.group.cmd.admin.*
import qqbot.actions.group.cmd.common.GirlAction
import qqbot.actions.group.cmd.common.alapi.*
import qqbot.actions.group.on.KeywordAction
import qqbot.actions.group.on.TipChangeNickAction
import qqbot.actions.group.on.events.GroupMuteAction
import qqbot.actions.group.on.events.JoinHandleAction
import qqbot.actions.group.on.events.WelcomeAction
import qqbot.actions.interfaces.OnEventAction
import qqbot.db.db
import qqbot.db.initDb
import qqbot.entity.LoginConfig
import qqbot.utils.moshi
import java.io.FileReader
import java.lang.management.ManagementFactory

lateinit var bot: Bot

suspend fun main() {
    val name = ManagementFactory.getRuntimeMXBean().name
    val pid = name.split("@").toTypedArray()[0]
    println("当前进程名: $name, pid = $pid")

    println("db version: ${db.version}, url = ${db.url}")
    initDb()

    launchBot()
}

suspend fun relaunchBot() {
    launchBot()
}

private suspend fun launchBot() {
    val json = FileReader("config.json").use {
        val adapter = moshi.adapter(LoginConfig::class.java)
        adapter.fromJson(it.readText())
    } ?: throw RuntimeException("登录失败")

    val (qq, password) = json

    checkNotNull(qq)
    checkNotNull(password)

    bot = Bot( // JVM 下也可以不写 `QQAndroid.` 引用顶层函数
        qq,
        password
    ) {
        // 覆盖默认的配置
        fileBasedDeviceInfo("device.json") // 使用 "device.json" 保存设备信息
        // networkLoggerSupplier = { SilentLogger } // 禁用网络层输出
    }

    bot.alsoLogin()

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
//    TestSendImage,
//    WeiboHotAction,

    /// 管理相关的
    MuteAllAction,
    MuteAction,
    UnMuteAction,
    KickAction,
    LoginAction,
//    DenyAction, // 有问题, 暂时先不用, 等1.3.0
)

private val atActions = arrayOf(
    AutoReplyAction
)

private val alwaysActions = arrayOf(
    TipChangeNickAction(),
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
            for (alwaysAction in alwaysActions) {
                alwaysAction.invoke(this)
            }

            KeywordAction.invoke(this)
        }

    }

    subscribeAction(WelcomeAction)
    subscribeAction(GroupMuteAction)
    subscribeAction(JoinHandleAction)
}

inline fun <reified T : Event> Bot.subscribeAction(action: OnEventAction<T>) {
    subscribeAlways(action::invoke)
}