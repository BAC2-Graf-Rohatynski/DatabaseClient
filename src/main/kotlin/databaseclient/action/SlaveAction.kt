package databaseclient.action

import apibuilder.database.information.*
import apibuilder.slave.Slave
import databaseclient.action.interfaces.ISlaveAction
import databaseclient.command.CommandSocketHandler
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

object SlaveAction: ISlaveAction {
    @Synchronized
    override fun saveSlaveInformationBackup(requestId: Int?) {
        try {
            val message = SaveSlaveBackupItem().create()
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            throw Exception("Error while checking whether slave is saved in table or not!\n${ex.message}")
        }
    }

    @Synchronized
    override fun checkIfSlaveIsAlreadyAdded(ssid: Int, requestId: Int?): Boolean {
        return try {
            val message = CheckIfSlaveIsAvailableItem().create(ssid = ssid)
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            response.value.toString().toBoolean()
        } catch (ex: Exception) {
            throw Exception("Error while checking whether slave is saved in table or not!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getNewSsid(requestId: Int?): Int {
        return try {
            val message = GetNewSsidItem().create()
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            response.value.toString().toInt()
        } catch (ex: Exception) {
            throw Exception("Error while requesting new SSID!\n${ex.message}")
        }
    }

    @Synchronized
    override fun addSlave(slave: Slave, requestId: Int?) {
        try {
            val message = AddSlaveItem().create(slave = slave)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            throw Exception("Error while adding slave!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getAllRecordsInDatabase(requestId: Int?): List<Slave> {
        return try {
            val message = GetAllSlavesItem().create()
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            buildObjects(JSONArray(response.value.toString()))
        } catch (ex: Exception) {
            throw Exception("Error while requesting all slave records!\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteAllRecordsInDatabase(requestId: Int?) {
        try {
            val message = DeleteAllSlavesItem().create()
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            throw Exception("Error while clearing slave table!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getNumberOfRecords(requestId: Int?): Int {
        return try {
            val message = GetNumberOfSlavesItem().create()
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            response.value.toString().toInt()
        } catch (ex: Exception) {
            throw Exception("Error while requesting number of records!\n${ex.message}")
        }
    }

    @Synchronized
    override fun updateBySsid(ssid: Int, field: String, value: String, requestId: Int?) {
        try {
            val message = UpdateSlaveBySsidItem().create(ssid = ssid, value = value, field = field)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            throw Exception("Error while updating by SSID!\n${ex.message}")
        }
    }

    @Synchronized
    override fun updateByMacAddress(macAddress: String, field: String, value: String, requestId: Int?) {
        try {
            val message = UpdateSlaveByMacAddressItem().create(macAddress = macAddress, field = field, value = value)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            throw Exception("Error while updating by MAC address!\n${ex.message}")
        }
    }

    @Synchronized
    override fun updateAllWhere(setField: String, setValue: Any, whereField: String, whereValue: Any, requestId: Int?) {
        try {
            val message = UpdateSlaveAllWhereItem().create(setField = setField, setValue = setValue, whereField = whereField, whereValue = whereValue)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            throw Exception("Error while updating!\n${ex.message}")
        }
    }

    @Synchronized
    override fun updateGeo(slave: Slave, requestId: Int?) {
        try {
            val message = UpdateGeoItem().create(
                    macAddress = slave.macAddress,
                    positionX = slave.positionX,
                    positionY = slave.positionY,
                    positionZ = slave.positionZ,
                    rotationX = slave.rotationX,
                    rotationY = slave.rotationY,
                    rotationZ = slave.rotationZ,
                    accelerationX = slave.accelerationX,
                    accelerationY = slave.accelerationY,
                    accelerationZ = slave.accelerationZ
            )

            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            throw Exception("Error while updating geo positions!\n${ex.message}")
        }
    }

    @Synchronized
    override fun updateRotating(isRotating: Boolean, ssid: Int, requestId: Int?) {
        try {
            val message = UpdateRotatingItem().create(ssid = ssid, isRotating = isRotating)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            throw Exception("Error while updating rotation!\n${ex.message}")
        }
    }

    @Synchronized
    override fun updateTimeStamp(macAddress: String, requestId: Int?) {
        try {
            val message = UpdateTimestampItem().create(macAddress = macAddress)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            throw Exception("Error while updating timestamp!\n${ex.message}")
        }
    }

    @Synchronized
    override fun updateInformationBySsid(slave: Slave, requestId: Int?) {
        try {
            val message = UpdateInformationBySsidItem().create(slave = slave)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            throw Exception("Error while updating timestamp!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getSlaveBySsid(ssid: Int, requestId: Int?): List<Slave> {
        return try {
            val message = GetSlaveBySsidItem().create(ssid = ssid)
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            buildObjects(JSONArray(response.value.toString()))
        } catch (ex: Exception) {
            throw Exception("Error while requesting slave by SSID!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getSlaveByManufacturer(manufacturer: String, requestId: Int?): List<Slave> {
        return try {
            val message = GetSlaveByManufacturerItem().create(manufacturer = manufacturer)
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            buildObjects(JSONArray(response.value.toString()))
        } catch (ex: Exception) {
            throw Exception("Error while requesting slave by manufacturer!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getSlaveByDevice(device: String, requestId: Int?): List<Slave> {
        return try {
            val message = GetSlaveByDeviceItem().create(device = device)
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            buildObjects(JSONArray(response.value.toString()))
        } catch (ex: Exception) {
            throw Exception("Error while requesting slave by device!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getSlaveByType(type: String, requestId: Int?): List<Slave> {
        return try {
            val message = GetSlaveByTypeItem().create(type = type)
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            buildObjects(JSONArray(response.value.toString()))
        } catch (ex: Exception) {
            throw Exception("Error while requesting slave by manufacturer!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getSlaveByEnabledRotation(requestId: Int?): List<Slave> {
        return try {
            val message = GetSlaveByEnabledRotationItem().create()
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            buildObjects(JSONArray(response.value.toString()))
        } catch (ex: Exception) {
            throw Exception("Error while requesting slave by enabled rotation!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getSlaveByMacAddress(macAddress: String, requestId: Int?): List<Slave> {
        return try {
            val message = GetSlaveByMacAddressItem().create(macAddress = macAddress)
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            buildObjects(JSONArray(response.value.toString()))
        } catch (ex: Exception) {
            throw Exception("Error while requesting slave by MAC address!\n${ex.message}")
        }
    }

    @Synchronized
    override fun getGeo(macAddress: String, requestId: Int?): List<Slave> {
        return try {
            val message = GetGeoByMacAddressItem().create(macAddress = macAddress)
            val response = CommandSocketHandler.send(message = message, withResponse = true)
            buildObjects(JSONArray(response.value.toString()))
        } catch (ex: Exception) {
            throw Exception("Error while requesting geo positions!\n${ex.message}")
        }
    }

    @Synchronized
    override fun deleteSlave(ssid: Int, requestId: Int?) {
        try {
            val message = DeleteSlaveItem().create(ssid = ssid)
            CommandSocketHandler.send(message = message)
        } catch (ex: Exception) {
            throw Exception("Error while deleting slave!\n${ex.message}")
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