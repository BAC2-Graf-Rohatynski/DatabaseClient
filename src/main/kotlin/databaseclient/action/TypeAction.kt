package databaseclient.action

import apibuilder.database.type.*
import apibuilder.database.type.item.TypeItem
import apibuilder.database.type.AddTypeItem
import apibuilder.database.type.DeleteAllTypesItem
import databaseclient.action.interfaces.ITypeAction
import databaseclient.command.CommandSocketHandler
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object TypeAction: ITypeAction {
    private val logger: Logger = LoggerFactory.getLogger(TypeAction::class.java)

    @Synchronized
    override fun addItem(item: TypeItem) {
        try {
            val message = AddTypeItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while adding item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteAllItems() {
        try {
            val message = DeleteAllTypesItem().create()
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while adding item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteItem(item: TypeItem) {
        try {
            val message = DeleteTypeItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while adding item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getAllItems(): List<TypeItem> {
        return try {
            val message = GetAllTypesItem().create()
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            buildObjects(jsonArray = JSONArray(response.value.toString()))
        } catch (ex: Exception) {
            throw Exception("Error while adding item!\n${ex.message}")
        }
    }

    @Synchronized
    override fun updateItemById(item: TypeItem) {
        try {
            val message = UpdateTypeByIdItem().create(item = item)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            logger.error("Error while adding item!\n${ex.message}")
        }
    }

    private fun buildObjects(jsonArray: JSONArray): List<TypeItem> {
        val items = mutableListOf<TypeItem>()

        jsonArray.forEach { item ->
            item as JSONObject
            items.add(TypeItem().toObject(json = item))
        }

        return items
    }
}