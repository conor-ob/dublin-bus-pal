package ie.dublinbuspal.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {

    @Deprecated("Migration broke during update to 2.0.0, fallbackToDestructiveMigration is used until database version 4")
    val MIGRATION_1_2 = object : Migration(1, 2) {

        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `default_stops` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, PRIMARY KEY(`id`))")
            database.execSQL("CREATE TABLE IF NOT EXISTS `dublin_bus_stops` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `routes` TEXT NOT NULL, PRIMARY KEY(`id`))")
            database.execSQL("CREATE TABLE IF NOT EXISTS `go_ahead_dublin_stops` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `routes` TEXT NOT NULL, PRIMARY KEY(`id`))")
            database.execSQL("CREATE TABLE IF NOT EXISTS `default_routes` (`id` TEXT NOT NULL, `origin` TEXT NOT NULL, `destination` TEXT NOT NULL, PRIMARY KEY(`id`))")
            database.execSQL("CREATE TABLE IF NOT EXISTS `go_ahead_dublin_routes` (`id` TEXT NOT NULL, `origin` TEXT NOT NULL, `destination` TEXT NOT NULL, PRIMARY KEY(`id`))")
            database.execSQL("CREATE TABLE IF NOT EXISTS `favourites` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `routes` TEXT NOT NULL, `order` INTEGER NOT NULL, PRIMARY KEY(`id`))")
            database.execSQL("CREATE TABLE IF NOT EXISTS `persister_last_updated` (`id` TEXT NOT NULL, `last_updated` INTEGER NOT NULL, PRIMARY KEY(`id`))")
            database.execSQL("INSERT INTO `favourites` (`id`, `name`, `routes`, `order`) SELECT `favourite_id`, `favourite_custom_name`, `favourite_custom_routes`, `favourite_order` FROM `tb_favourites`")
            database.execSQL("DROP TABLE `tb_bus_stops`")
            database.execSQL("DROP TABLE `tb_routes`")
            database.execSQL("DROP TABLE `tb_bus_stop_service`")
            database.execSQL("DROP TABLE `tb_route_service`")
            database.execSQL("DROP TABLE `tb_favourites`")
            database.execSQL("DROP TABLE `tb_unchecked_bus_stop_service`")
        }

    }

    val MIGRATION_3_4 = object : Migration(3, 4) {

        override fun migrate(database: SupportSQLiteDatabase) {
            // nothing to do
        }

    }

}
