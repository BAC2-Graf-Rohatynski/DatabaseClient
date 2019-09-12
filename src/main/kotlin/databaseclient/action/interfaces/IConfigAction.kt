package databaseclient.action.interfaces

import apibuilder.slave.Slave

interface IConfigAction {
    fun addConfig(ssid: Int, show: String, ddfHash: String, ddfFile: String, requestId: Int? = null)
    fun getAllRecordsInDatabase(show: String, requestId: Int? = null): List<Slave>
    fun deleteAllRecordsInDatabase(show: String, requestId: Int? = null)
    fun getConfigurationBySsid(ssid: Int, show: String, requestId: Int? = null): List<Slave>
    fun getConfigurationByDdfHash(ddfHash: String, show: String, requestId: Int? = null): List<Slave>
    fun updateBySsid(ssid: Int, ddfHash: String, ddfFile: String, requestId: Int? = null)
    fun deleteSingleConfiguration(show: String, ssid: Int, requestId: Int? = null)
}