package databaseclient.action

import apibuilder.database.color.*
import apibuilder.database.color.item.ColorItem
import apibuilder.database.color.AddColorItem
import apibuilder.database.color.DeleteAllColorsItem
import databaseclient.action.interfaces.IColorAction
import databaseclient.command.CommandSocketHandler
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ColorAction: IColorAction {
    private val logger: Logger = LoggerFactory.getLogger(ColorAction::class.java)

    @Synchronized
    override fun addItem(item: ColorItem) {
        try {
            val message = AddColorItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while adding item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteAllItems() {
        try {
            val message = DeleteAllColorsItem().create()
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while deleting all items!\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteItem(item: ColorItem) {
        try {
            val message = DeleteColorItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while deleting item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getAllItems(): List<ColorItem> {
        return try {
            val message = GetAllColorsItem().create()
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            buildObjects(jsonArray = JSONArray(response.value))
        } catch (ex: Exception) {
            throw Exception("Error while getting all items!\n${ex.message}")
        }
    }

    @Synchronized
    override fun updateItemById(item: ColorItem) {
        try {
            val message = UpdateColorByIdItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while updating item!\n${ex.message}")
        }
    }

    private fun buildObjects(jsonArray: JSONArray): List<ColorItem> {
        val items = mutableListOf<ColorItem>()

        jsonArray.forEach { item ->
            item as JSONObject
            items.add(ColorItem().toObject(json = item))
        }

        return items
    }
}