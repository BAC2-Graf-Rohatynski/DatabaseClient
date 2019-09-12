package databaseclient.action

import apibuilder.database.manufacturer.*
import apibuilder.database.manufacturer.item.ManufacturerItem
import databaseclient.action.interfaces.IManufacturerAction
import databaseclient.command.CommandSocketHandler
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ManufacturerAction: IManufacturerAction {
    private val logger: Logger = LoggerFactory.getLogger(ManufacturerAction::class.java)

    @Synchronized
    override fun addItem(item: ManufacturerItem) {
        try {
            val message = AddManufacturerItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while adding item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteAllItems() {
        try {
            val message = DeleteAllManufacturersItem().create()
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while deleting all items!\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteItem(item: ManufacturerItem) {
        try {
            val message = DeleteManufacturerItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while deleting item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getAllItems(): List<ManufacturerItem> {
        return try {
            val message = GetAllManufacturersItem().create()
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            buildObjects(jsonArray = JSONArray(response.value))
        } catch (ex: Exception) {
            throw Exception("Error while getting all items!\n${ex.message}")
        }
    }

    @Synchronized
    override fun updateItemById(item: ManufacturerItem) {
        try {
            val message = UpdateManufacturerByIdItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while updating item!\n${ex.message}")
        }
    }

    private fun buildObjects(jsonArray: JSONArray): List<ManufacturerItem> {
        val items = mutableListOf<ManufacturerItem>()

        jsonArray.forEach { item ->
            item as JSONObject
            items.add(ManufacturerItem().toObject(json = item))
        }

        return items
    }
}