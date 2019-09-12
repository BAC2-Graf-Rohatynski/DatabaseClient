package databaseclient.action

import apibuilder.database.configuration.*
import apibuilder.slave.Slave
import databaseclient.action.interfaces.IConfigAction
import databaseclient.command.CommandSocketHandler
import org.json.JSONArray
import org.json.JSONObject

object ConfigAction: IConfigAction {
    @Synchronized
    override fun addConfig(ssid: Int, show: String, ddfHash: String, ddfFile: String, requestId: Int?) {
        try {
            val message = AddConfigurationItem().create(ddfHash = ddfHash, ddfFile = ddfFile, show = show, ssid = ssid)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            throw Exception("Error while adding configuration!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getAllRecordsInDatabase(show: String, requestId: Int?): List<Slave> {
        return try {
            val message = GetAllConfigurationsItem().create(show = show)
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            buildObjects(JSONArray(response.value.toString()))
        } catch (ex: Exception) {
            throw Exception("Error while requesting all configurations!\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteAllRecordsInDatabase(show: String, requestId: Int?) {
        try {
            val message = DeleteAllConfigurationsItem().create(show = show)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            throw Exception("Error while clearing all configurations!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getConfigurationBySsid(ssid: Int, show: String, requestId: Int?): List<Slave> {
         return try {
             val message = GetConfigurationBySsidItem().create(show = show, ssid = ssid)
             val response = CommandSocketHandler.send(message = message, withResponse = true)
             buildObjects(JSONArray(response.value.toString()))
        } catch (ex: Exception) {
            throw Exception("Error while requesting configuration by SSID!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getConfigurationByDdfHash(ddfHash: String, show: String, requestId: Int?): List<Slave> {
        return try {
            val message = GetConfigurationByDdfHashItem().create(show = show, ddfHash = ddfHash)
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            buildObjects(JSONArray(response.value.toString()))
        } catch (ex: Exception) {
            throw Exception("Error while requesting configuration by DDF hash!\n${ex.message}")
        }
    }

    @Synchronized
    override fun updateBySsid(ssid: Int, ddfHash: String, ddfFile: String, requestId: Int?) {
        try {
            val message = UpdateConfigurationBySsidItem().create(
                    ssid = ssid,
                    ddfHash = ddfHash,
                    ddfFile = ddfFile)

            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            throw Exception("Error while updating configuration by SSID!\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteSingleConfiguration(show: String, ssid: Int, requestId: Int?) {
        try {
            val message = DeleteConfigurationItem().create(show = show, ssid = ssid)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            throw Exception("Error while deleting configuration!\n${ex.message}")
        }
    }

    private fun buildObjects(jsonArray: JSONArray): List<Slave> {
        val items = mutableListOf<Slave>()

        jsonArray.forEach { item ->
            item as JSONObject
            items.add(Slave().build(message = item))
        }

        return items
    }
}