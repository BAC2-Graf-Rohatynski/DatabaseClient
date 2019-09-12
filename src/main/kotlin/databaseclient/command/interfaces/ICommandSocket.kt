package databaseclient.command.interfaces

import apibuilder.database.interfaces.IDatabaseItem

interface ICommandSocket {
    fun initialize()
    fun send(message: IDatabaseItem)
    fun closeSockets()
}