package databaseclient.action

import apibuilder.database.gobo.*
import apibuilder.database.gobo.item.GoboItem
import apibuilder.database.gobo.AddGoboItem
import apibuilder.database.gobo.DeleteAllGobosItem
import databaseclient.action.interfaces.IGoboAction
import databaseclient.command.CommandSocketHandler
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object GoboAction: IGoboAction {
    private val logger: Logger = LoggerFactory.getLogger(GoboAction::class.java)

    @Synchronized
    override fun addItem(item: GoboItem) {
        try {
            val message = AddGoboItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while adding item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteAllItems() {
        try {
            val message = DeleteAllGobosItem().create()
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while adding item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteItem(item: GoboItem) {
        try {
            val message = DeleteGoboItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while adding item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getAllItems(): List<GoboItem> {
        return try {
            val message = GetAllGobosItem().create()
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            buildObjects(jsonArray = JSONArray(response.value.toString()))
        } catch (ex: Exception) {
            throw Exception("Error while adding item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun updateItemById(item: GoboItem) {
        try {
            val message = UpdateGoboByIdItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while adding item!\n${ex.message}")
        }
    }

    private fun buildObjects(jsonArray: JSONArray): List<GoboItem> {
        val items = mutableListOf<GoboItem>()

        jsonArray.forEach { item ->
            item as JSONObject
            items.add(GoboItem().toObject(json = item))
        }

        return items
    }
}