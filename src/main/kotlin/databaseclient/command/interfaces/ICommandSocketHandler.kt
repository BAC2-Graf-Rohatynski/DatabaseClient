package databaseclient.command.interfaces

import apibuilder.database.interfaces.IDatabaseItem
import apibuilder.database.response.ResponseItem

interface ICommandSocketHandler {
    fun send(message: IDatabaseItem, withResponse: Boolean = false): ResponseItem
    fun closeSockets()
}