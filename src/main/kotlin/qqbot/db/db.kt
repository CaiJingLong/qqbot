package qqbot.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import qqbot.db.table.DenyTable

val db by lazy { Database.connect("jdbc:sqlite:qq.db", "org.sqlite.JDBC") }

fun initDb() {
    transaction {
        SchemaUtils.createMissingTablesAndColumns(DenyTable)
    }
}
