package databaseclient.command

import apibuilder.database.interfaces.IDatabaseItem
import apibuilder.database.response.ResponseItem
import databaseclient.command.interfaces.ICommandSocketHandler
import databaseclient.queue.CommandQueue
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object CommandSocketHandler: ICommandSocketHandler {
    private lateinit var commandSocket: CommandSocket
    private val logger: Logger = LoggerFactory.getLogger(CommandSocketHandler::class.java)

    init {
        connect()
    }

    @Synchronized
    override fun send(message: IDatabaseItem, withResponse: Boolean): ResponseItem {
        try {
            return if (::commandSocket.isInitialized) {
                commandSocket.send(message = message)
                if (withResponse) CommandQueue.takeFromQueue(requestId = message.getRequestId()) else ResponseItem()
            } else {
                throw Exception("Socket hasn't been connected yet!")
            }
        } catch (ex: Exception) {
            throw Exception("Error while waiting for response!\n${ex.message}")
        }
    }

    @Synchronized
    override fun closeSockets() {
        if (::commandSocket.isInitialized) {
            commandSocket.closeSockets()
        }
    }

    private fun connect() {
        try {
            logger.info("Connecting ...")
            commandSocket = CommandSocket()
            commandSocket.initialize()
            commandSocket.start()
            logger.info("Connected")
        } catch (ex: Exception) {
            logger.error("Error occurred while connecting!\n${ex.message}")
        }
    }
}