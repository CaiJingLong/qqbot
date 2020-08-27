package qqbot.db.table

import org.jetbrains.exposed.sql.Table

object DenyTable : Table() {

    val qq = long("qq")
    val group = long("group")
    val by = long("by")
    val byNick = varchar("byNick", 50)
    val insertDate = long("timestamp")

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(qq, group)

}