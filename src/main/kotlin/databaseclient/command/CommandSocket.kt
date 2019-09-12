package databaseclient.command

import apibuilder.database.interfaces.IDatabaseItem
import databaseclient.DatabaseClientRunner
import databaseclient.command.interfaces.ICommandSocket
import databaseclient.queue.CommandQueue
import interfacehelper.MyIpAddress
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import propertystorage.PortProperties
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.lang.Exception

class CommandSocket: ICommandSocket, Thread() {
    private lateinit var clientSocket: Socket
    private lateinit var printWriter: PrintWriter
    private lateinit var bufferedReader: BufferedReader
    private val logger: Logger = LoggerFactory.getLogger(CommandSocket::class.java)

    override fun initialize() {
        openSockets()
    }

    override fun run() {
        try {
            receive()
        } catch (ex: Exception) {
            logger.error("Error socket failure while running socket!\n${ex.message}")
        } finally {
            closeSockets()
        }
    }

    private fun openSockets() {
        logger.info("Opening sockets ...")
        clientSocket = Socket(MyIpAddress.getAsString(), PortProperties.getDatabasePort())
        printWriter = PrintWriter(clientSocket.getOutputStream(), true)
        bufferedReader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
        logger.info("Sockets opened")
    }

    @Synchronized
    override fun send(message: IDatabaseItem) {
        try {
            if (::printWriter.isInitialized) {
                printWriter.println(message.toJson())
                logger.info("Message '${message.toJson()}' sent")
            } else {
                throw Exception("Print writer isn't initialized yet!")
            }
        } catch (ex: Exception) {
            logger.error("Error occurred while sending message!\n${ex.message}")
        }
    }

    private fun receive() {
        logger.info("Hearing for messages ...")

        bufferedReader.use {
            try {
                while (DatabaseClientRunner.isRunnable()) {
                    val inputLine = bufferedReader.readLine()

                    if (inputLine != null) {
                        logger.info("Message '$inputLine' received")
                        CommandQueue.putIntoQueue(message = inputLine)
                    }
                }
            } catch (ex: Exception) {
                logger.error("Error occurred while parsing message!\n${ex.message}")
            }
        }
    }

    @Synchronized
    override fun closeSockets() {
        try {
            logger.info("Closing sockets ...")

            if (::printWriter.isInitialized) {
                printWriter.close()
            }

            if (::clientSocket.isInitialized) {
                clientSocket.close()
            }

            if (::bufferedReader.isInitialized) {
                bufferedReader.close()
            }

            logger.info("Sockets closed")
        } catch (ex: Exception) {
            logger.error("Error occurred while closing sockets!\n${ex.message}")
        }
    }
}