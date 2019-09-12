package databaseclient.action

import apibuilder.database.image.*
import apibuilder.database.image.item.ImageItem
import apibuilder.database.image.AddImageItem
import apibuilder.database.image.DeleteAllImagesItem
import databaseclient.action.interfaces.IImageAction
import databaseclient.command.CommandSocketHandler
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ImageAction: IImageAction {
    private val logger: Logger = LoggerFactory.getLogger(ImageAction::class.java)

    @Synchronized
    override fun addItem(item: ImageItem) {
        try {
            val message = AddImageItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while adding item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteAllItems() {
        try {
            val message = DeleteAllImagesItem().create()
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while adding item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteItem(item: ImageItem) {
        try {
            val message = DeleteImageItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while adding item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getAllItems(): List<ImageItem> {
        return try {
            val message = GetAllImagesItem().create()
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            buildObjects(jsonArray = JSONArray(response.value.toString()))
        } catch (ex: Exception) {
            throw Exception("Error while adding item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun updateItemById(item: ImageItem) {
        try {
            val message = UpdateImageByIdItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while adding item!\n${ex.message}")
        }
    }

    private fun buildObjects(jsonArray: JSONArray): List<ImageItem> {
        val items = mutableListOf<ImageItem>()

        jsonArray.forEach { item ->
            item as JSONObject
            items.add(ImageItem().toObject(json = item))
        }

        return items
    }
}