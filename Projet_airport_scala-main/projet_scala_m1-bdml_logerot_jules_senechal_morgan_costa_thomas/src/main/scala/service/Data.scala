package  service
import java.sql.{Connection, DriverManager, Statement,ResultSet}
object Data {
    Class.forName("org.sqlite.JDBC")
    val url = "jdbc:sqlite:airport.db"
    def executeQuery(query : String): Unit = {
        val connection: Connection = DriverManager.getConnection(url)
        val statement: Statement = connection.createStatement()
        statement.execute(query)
        statement.close()
        connection.close()
  }
  def executeQueryWithResult(query: String): ResultSet = {
    val connection: Connection = DriverManager.getConnection(url)
    val statement: Statement = connection.createStatement()
    val resultSet: ResultSet = statement.executeQuery(query)
    resultSet
  }
}
