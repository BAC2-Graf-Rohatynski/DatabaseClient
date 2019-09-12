package databaseclient.action

import apibuilder.database.device.*
import apibuilder.database.device.item.DeviceItem
import apibuilder.database.device.AddDeviceItem
import apibuilder.database.device.DeleteAllDevicesItem
import databaseclient.action.interfaces.IDeviceAction
import databaseclient.command.CommandSocketHandler
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object DeviceAction: IDeviceAction {
    private val logger: Logger = LoggerFactory.getLogger(DeviceAction::class.java)

    @Synchronized
    override fun addItem(item: DeviceItem) {
        try {
            val message = AddDeviceItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while adding item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteAllItems() {
        try {
            val message = DeleteAllDevicesItem().create()
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while deleting all items!\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteItem(item: DeviceItem) {
        try {
            val message = DeleteDeviceItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while deleting item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getAllItems(): List<DeviceItem> {
        return try {
            val message = GetAllDevicesItem().create()
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            buildObjects(jsonArray = JSONArray(response.value))
        } catch (ex: Exception) {
            throw Exception("Error while getting all items!\n${ex.message}")
        }
    }

    @Synchronized
    override fun updateItemById(item: DeviceItem) {
        try {
            val message = UpdateDeviceByIdItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while updating item!\n${ex.message}")
        }
    }

    private fun buildObjects(jsonArray: JSONArray): List<DeviceItem> {
        val items = mutableListOf<DeviceItem>()

        jsonArray.forEach { item ->
            item as JSONObject
            items.add(DeviceItem().toObject(json = item))
        }

        return items
    }
}