package databaseclient.action.interfaces

import apibuilder.slave.Slave

interface ISlaveAction {
    fun saveSlaveInformationBackup(requestId: Int? = null)
    fun checkIfSlaveIsAlreadyAdded(ssid: Int, requestId: Int? = null): Boolean
    fun getNewSsid(requestId: Int? = null): Int
    fun addSlave(slave: Slave, requestId: Int? = null)
    fun getAllRecordsInDatabase(requestId: Int? = null): List<Slave>
    fun deleteAllRecordsInDatabase(requestId: Int? = null)
    fun getNumberOfRecords(requestId: Int? = null): Int
    fun updateBySsid(ssid: Int, field: String, value: String, requestId: Int? = null)
    fun updateInformationBySsid(slave: Slave, requestId: Int? = null)
    fun updateByMacAddress(macAddress: String, field: String, value: String, requestId: Int? = null)
    fun updateAllWhere(setField: String, setValue: Any, whereField: String, whereValue: Any, requestId: Int? = null)
    fun updateGeo(slave: Slave, requestId: Int? = null)
    fun updateRotating(isRotating: Boolean, ssid: Int, requestId: Int? = null)
    fun updateTimeStamp(macAddress: String, requestId: Int? = null)
    fun getSlaveByDevice(device: String, requestId: Int?): List<Slave>
    fun getSlaveBySsid(ssid: Int, requestId: Int? = null): List<Slave>
    fun getSlaveByManufacturer(manufacturer: String, requestId: Int? = null): List<Slave>
    fun getSlaveByType(type: String, requestId: Int? = null): List<Slave>
    fun getSlaveByEnabledRotation(requestId: Int? = null): List<Slave>
    fun getSlaveByMacAddress(macAddress: String, requestId: Int? = null): List<Slave>
    fun getGeo(macAddress: String, requestId: Int? = null): List<Slave>
    fun deleteSlave(ssid: Int, requestId: Int? = null)
}