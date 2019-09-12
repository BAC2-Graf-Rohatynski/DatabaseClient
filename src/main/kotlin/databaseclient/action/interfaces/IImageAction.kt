package databaseclient.action.interfaces

import apibuilder.database.image.item.ImageItem

interface IImageAction {
    fun addItem(item: ImageItem)
    fun deleteAllItems()
    fun deleteItem(item: ImageItem)
    fun getAllItems(): List<ImageItem>
    fun updateItemById(item: ImageItem)
}