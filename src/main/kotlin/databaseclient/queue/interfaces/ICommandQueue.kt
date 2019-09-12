package databaseclient.queue.interfaces

import apibuilder.database.response.ResponseItem

interface ICommandQueue {
    fun putIntoQueue(message: String)
    fun takeFromQueue(requestId: Int): ResponseItem
}