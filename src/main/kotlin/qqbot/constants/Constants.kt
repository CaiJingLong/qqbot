package qqbot.constants

object Constants {

    private const val bigGroupId = 181398081L
    private const val testGroupId = 906936101L
    private const val adminGroupId = 656238669L

    val testGroups = listOf<Long>(
        testGroupId,
        adminGroupId
    )

    val all: List<Long> = listOf(
        bigGroupId,
        testGroupId,
        adminGroupId,
    )

    val onlyBig = listOf<Long>(
        bigGroupId
    )

    val botQQ = listOf<Long>(
        2300406668, // 小宝
        3090077983, // 祖安
        2357621290, // 小拉面
        2854196310, // Q群管家
    )
}