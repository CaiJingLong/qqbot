package actions.group.on

import actions.group.CandiesGroupAction
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.sendAsImageTo
import java.awt.image.BufferedImage
import java.awt.image.BufferedImageFilter
import java.io.File
import javax.imageio.ImageIO

object OnSenderTalkAction : CandiesGroupAction {

    private val senderIds = arrayOf<Long>(
        410496936,
        262990989
    )

    override suspend fun onInvoke(event: GroupMessageEvent) {
        if (senderIds.contains(event.sender.id)) {
            val file = File("static/200_fans.png")
            val image = ImageIO.read(file)
            println("准备发送文件, 文件大小: ${file.length()}, 宽高: ${image.width}x${image.height}")
            event.quoteReply("你好")
            event.sendImage(image)

        }

    }


}