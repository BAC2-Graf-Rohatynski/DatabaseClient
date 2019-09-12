package databaseclient.queue

import apibuilder.database.response.ResponseItem
import databaseclient.queue.interfaces.ICommandQueue
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.Exception

object CommandQueue: ICommandQueue {
    private val logger: Logger = LoggerFactory.getLogger(CommandQueue::class.java)
    private val receivingQueue = mutableListOf<String>()

    @Volatile
    private var isBlocked = false

    override fun putIntoQueue(message: String) {
        try {
            logger.info("Pushing message into queue ...")
            while (isBlocked) {
                Thread.sleep(5)
            }
            isBlocked = true
            receivingQueue.add(message)
            isBlocked = false
            logger.info("Put into queue")
        } catch (ex: Exception) {
            logger.error("Queue error occurred while pushing! Clearing queue ...\n${ex.message}")
            receivingQueue.clear()
            isBlocked = false
        }
    }

    private fun getTime(): Long = System.currentTimeMillis()

    override fun takeFromQueue(requestId: Int): ResponseItem {
        try {
            val start: Long = getTime()

            while ((getTime() - start) < 500) {
                if (!isBlocked) {
                    if (receivingQueue.size > 0) {
                        receivingQueue.forEach { message ->
                            val responseItem = ResponseItem().create(message = message)

                            if (responseItem.getRequestId() == requestId) {
                                logger.info("Taking from queue: $message")
                                isBlocked = true
                                receivingQueue.remove(message)
                                isBlocked = false
                                return responseItem
                            }
                        }
                    }
                }
                Thread.sleep(10)
            }
        } catch (ex: Exception) {
            logger.error("Queue error occurred while pulling! Clearing queue ...\n${ex.message}")
            receivingQueue.clear()
            isBlocked = false
        }

        throw Exception("Message with request ID '$requestId' not received!")
    }
}