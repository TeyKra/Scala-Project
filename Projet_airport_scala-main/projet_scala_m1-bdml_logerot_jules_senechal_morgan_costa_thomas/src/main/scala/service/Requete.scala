package service
import model.{Airport, Countries, Runway}
import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet}
import scala.util.Try


object Requete {
def getAirportsByCountryName(countryName: String): List[Airport] = {
  val query =
    s"""SELECT a.*
       |FROM Airport a
       |JOIN Countries c ON a.iso_country = c.code
       |WHERE c.name = '$countryName'
       |LIMIT 10;""".stripMargin   
       /* Lorsque dans la requete SQL nous cherchons les correspondances avec un "where", la requete n'arrive à ressortire aucune correspondance et donc aucune liste d'airport et de runway. Il nous est donc impossible de pouvoir faire correspondre le choix utilisateur sur les tables. */

  val resultSet: ResultSet = Data.executeQueryWithResult(query)

  var airports: List[Airport] = List()

  while (resultSet.next()) {
    val id = resultSet.getInt("id")
    val ident = resultSet.getString("ident")
    val airportType = resultSet.getString("type")
    val name = resultSet.getString("name")
    val latitudeDeg = resultSet.getDouble("latitudeDeg")
    val longitudeDeg = resultSet.getDouble("longitudeDeg")
    val elevationFt = resultSet.getInt("elevationFt")
    val continent = resultSet.getString("continent")
    val isoCountry = resultSet.getString("iso_country")
    val isoRegion = resultSet.getString("isoRegion")
    val municipality = resultSet.getString("municipality")
    val scheduledService = resultSet.getString("scheduledService")
    val gpsCode = resultSet.getString("gpsCode")
    val iataCode = resultSet.getString("iataCode")
    val localCode = resultSet.getString("localCode")
    val homeLink = Option(resultSet.getString("homeLink"))
    val wikipediaLink = Option(resultSet.getString("wikipediaLink"))
    val keywords = Option(resultSet.getString("keywords"))

    val airport = Airport(
      id,
      ident,
      airportType,
      name,
      latitudeDeg,
      longitudeDeg,
      elevationFt,
      continent,
      isoCountry,
      isoRegion,
      municipality,
      scheduledService,
      gpsCode,
      iataCode,
      localCode,
      homeLink,
      wikipediaLink,
      keywords
    )

    airports = airports :+ airport
  }

  resultSet.close()
  airports
}

def getTop10AirportsByCountryCount(): List[(String, Int)] = {
  val query =
    """SELECT a.iso_country, c.name, COUNT(*) as airport_count
      |FROM Airport a
      |JOIN Countries c ON a.iso_country = c.code
      |GROUP BY a.iso_country, c.name
      |ORDER BY airport_count DESC
      |LIMIT 10;""".stripMargin

  val resultSet: ResultSet = Data.executeQueryWithResult(query)

  var top10AirportsByCountryCount: List[(String, Int)] = List()

  while (resultSet.next()) {
    val isoCountry = resultSet.getString("iso_country")
    val countryName = resultSet.getString("name")
    val airportCount = resultSet.getInt("airport_count")
    top10AirportsByCountryCount = (countryName, airportCount) :: top10AirportsByCountryCount
  }

  resultSet.close()
  top10AirportsByCountryCount.reverse // Pour obtenir la liste triée par ordre croissant
}
def getBottom10AirportsByCountryCount(): List[(String, Int)] = {
  val query =
    """SELECT a.iso_country, c.name, COUNT(*) as airport_count
      |FROM Airport a
      |JOIN Countries c ON a.iso_country = c.code
      |GROUP BY a.iso_country, c.name
      |ORDER BY airport_count ASC
      |LIMIT 10;""".stripMargin

  val resultSet: ResultSet = Data.executeQueryWithResult(query)

  var bottom10AirportsByCountryCount: List[(String, Int)] = List()

  while (resultSet.next()) {
    val isoCountry = resultSet.getString("iso_country")
    val countryName = resultSet.getString("name")
    val airportCount = resultSet.getInt("airport_count")
    bottom10AirportsByCountryCount = (countryName, airportCount) :: bottom10AirportsByCountryCount
  }

  resultSet.close()
  bottom10AirportsByCountryCount.reverse // Pour obtenir la liste triée par ordre croissant
}
def getRunwayTypesPerCountry(): Map[String, List[String]] = {
  val query =
    """SELECT a.iso_country, GROUP_CONCAT(DISTINCT r.surface) AS runway_types
      |FROM Airport a
      |JOIN Runway r ON a.id = r.airportRef
      |GROUP BY a.iso_country;""".stripMargin

  val resultSet: ResultSet = Data.executeQueryWithResult(query)

  var runwayTypesPerCountry: Map[String, List[String]] = Map()

  while (resultSet.next()) {
    val isoCountry = resultSet.getString("iso_country")
    val runwayTypes = resultSet.getString("runway_types").split(",").toList

    runwayTypesPerCountry += (isoCountry -> runwayTypes)
  }

  resultSet.close()
  runwayTypesPerCountry
}
def getTop10CommonRunwayLatitudes(): List[String] = {
  val query =
    """SELECT leIdent, COUNT(leIdent) AS count
      |FROM Runway
      |GROUP BY leIdent
      |ORDER BY count DESC
      |LIMIT 10;""".stripMargin

  val resultSet: ResultSet = Data.executeQueryWithResult(query)

  var commonRunwayLatitudes: List[String] = List()

  while (resultSet.next()) {
    val leIdent = resultSet.getString("leIdent")
    commonRunwayLatitudes = commonRunwayLatitudes :+ leIdent
  }

  resultSet.close()
  commonRunwayLatitudes
}
}