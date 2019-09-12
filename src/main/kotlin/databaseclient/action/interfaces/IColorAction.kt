package databaseclient.action.interfaces

import apibuilder.database.color.item.ColorItem

interface IColorAction {
    fun addItem(item: ColorItem)
    fun deleteAllItems()
    fun deleteItem(item: ColorItem)
    fun getAllItems(): List<ColorItem>
    fun updateItemById(item: ColorItem)
}