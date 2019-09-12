package databaseclient.action

import apibuilder.database.show.*
import databaseclient.action.interfaces.IDdfDatabaseAction
import databaseclient.command.CommandSocketHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ShowAction: IDdfDatabaseAction {
    private val logger: Logger = LoggerFactory.getLogger(ShowAction::class.java)

    @Synchronized
    override fun getEnabledShow(requestId: Int?): String {
        return try {
            val message = GetEnabledShowItem().create()
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            response.value.toString()
        } catch (ex: Exception) {
            throw Exception("Error while requesting enabled DDF database!\n${ex.message}")
        }
    }

    @Synchronized
    override fun enableShow(show: String, requestId: Int?) {
        try {
            val message = EnableShowItem().create(show = show)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while enabling DDF database!\n${ex.message}")
        }
    }

    @Synchronized
    override fun newShow(show: String, requestId: Int?) {
        try {
            val message = AddShowItem().create(show = show)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while adding DDF database!\n${ex.message}")
        }
    }

    @Synchronized
    override fun renameShow(show: String, value: String, requestId: Int?) {
        try {
            val message = RenameShowItem().create(show = show, value = value)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while renaming DDF database !\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteShow(show: String, requestId: Int?) {
        try {
            val message = DeleteShowItem().create(show = show)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while deleting DDF database!\n${ex.message}")
        }
    }

    @Synchronized
    override fun isShowAvailable(show: String, requestId: Int?): Boolean {
        return try {
            val message = IsShowAvailableItem().create(show = show)
            val response = CommandSocketHandler.send(message = message)
            response.value.toString().toBoolean()
        } catch (ex: Exception) {
            throw Exception("Error while requesting availability of DDF database!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getAllShows(requestId: Int?): List<String> {
        return try {
            val message = GetAllShowsItem().create()
            val response = CommandSocketHandler.send(message = message)
            response.value.toString()
                    .replace("[", "")
                    .replace("]", "")
                    .split(",")
        } catch (ex: Exception) {
            logger.error("Error while requesting all DDF databases!\n${ex.message}")
            mutableListOf()
        }
    }
}