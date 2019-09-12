package databaseclient

import databaseclient.command.CommandSocketHandler
import enumstorage.update.ApplicationName
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object DatabaseClientRunner {
    private val logger: Logger = LoggerFactory.getLogger(DatabaseClientRunner::class.java)

    @Volatile
    private var runApplication = true

    @Synchronized
    fun isRunnable(): Boolean = runApplication

    fun stop() {
        logger.info("Stopping application")
        runApplication = false

        CommandSocketHandler.closeSockets()
    }

    fun getUpdateInformation(): JSONObject = UpdateInformation.getAsJson(applicationName = ApplicationName.DatabaseClient.name)
}