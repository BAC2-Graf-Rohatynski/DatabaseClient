package databaseclient.action.interfaces

import apibuilder.database.type.item.TypeItem

interface ITypeAction {
    fun addItem(item: TypeItem)
    fun deleteAllItems()
    fun deleteItem(item: TypeItem)
    fun getAllItems(): List<TypeItem>
    fun updateItemById(item: TypeItem)
}