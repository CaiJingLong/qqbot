package qqbot.db

import org.jetbrains.exposed.sql.Database

val db by lazy { Database.connect("jdbc:sqlite:qq.db", "org.sqlite.JDBC") }