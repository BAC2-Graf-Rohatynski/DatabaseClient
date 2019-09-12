package databaseclient.action.interfaces

interface IDdfDatabaseAction {
    fun getEnabledShow(requestId: Int? = null): String
    fun enableShow(show: String, requestId: Int? = null)
    fun newShow(show: String, requestId: Int? = null)
    fun renameShow(show: String, value: String, requestId: Int? = null)
    fun deleteShow(show: String, requestId: Int? = null)
    fun isShowAvailable(show: String, requestId: Int? = null): Boolean
    fun getAllShows(requestId: Int? = null): List<String>
}