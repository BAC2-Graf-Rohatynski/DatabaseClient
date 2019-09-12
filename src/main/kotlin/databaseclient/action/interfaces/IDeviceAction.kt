package databaseclient.action.interfaces

import apibuilder.database.device.item.DeviceItem

interface IDeviceAction {
    fun addItem(item: DeviceItem)
    fun deleteAllItems()
    fun deleteItem(item: DeviceItem)
    fun getAllItems(): List<DeviceItem>
    fun updateItemById(item: DeviceItem)
}