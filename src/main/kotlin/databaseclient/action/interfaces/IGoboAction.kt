package databaseclient.action.interfaces

import apibuilder.database.gobo.item.GoboItem

interface IGoboAction {
    fun addItem(item: GoboItem)
    fun deleteAllItems()
    fun deleteItem(item: GoboItem)
    fun getAllItems(): List<GoboItem>
    fun updateItemById(item: GoboItem)
}