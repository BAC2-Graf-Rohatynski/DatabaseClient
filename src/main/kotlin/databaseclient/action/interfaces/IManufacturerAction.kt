package databaseclient.action.interfaces

import apibuilder.database.manufacturer.item.ManufacturerItem

interface IManufacturerAction {
    fun addItem(item: ManufacturerItem)
    fun deleteAllItems()
    fun deleteItem(item: ManufacturerItem)
    fun getAllItems(): List<ManufacturerItem>
    fun updateItemById(item: ManufacturerItem)
}