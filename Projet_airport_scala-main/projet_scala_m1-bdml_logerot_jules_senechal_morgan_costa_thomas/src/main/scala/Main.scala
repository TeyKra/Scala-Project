import scala.io.StdIn.readLine
import service.CSVParser
import service.Data
import service.Requete
import model.{Airport, Countries, Runway}
import scala.io.StdIn
import java.nio.file.Paths
import java.sql.ResultSet
import scala.util.Try

object countries extends App {
  val cheminFichierCSV = "ressources/countries.csv"
  val countriesList = CSVParser.parseCountries(cheminFichierCSV)

  println("Countries:")
  countriesList.foreach { country =>
    println(s"ID: ${country.id}, Code: ${country.code}, Name: ${country.name}, Continent: ${country.continent}, Wikipedia Link: ${country.wikipediaLink}, Keywords: ${country.keywords.getOrElse("None")}")
  }
}
object avion extends App {
  val cheminFichierAirport = "ressources/airports.csv"
  val airports = CSVParser.parseAirports(cheminFichierAirport)

  // Afficher les informations sur les aéroports
  if (airports.nonEmpty) {
    println("Airports:")
    airports.foreach { airport =>
      println(s"ID: ${airport.id}, Ident: ${airport.ident}, Type: ${airport.`type`}, Name: ${airport.name}, Latitude: ${airport.latitudeDeg}, Longitude: ${airport.longitudeDeg}, Elevation: ${airport.elevationFt}, Continent: ${airport.continent}, ISO Country: ${airport.iso_country}, ISO Region: ${airport.isoRegion}, Municipality: ${airport.municipality}, Scheduled Service: ${airport.scheduledService}, GPS Code: ${airport.gpsCode}, IATA Code: ${airport.iataCode}, Local Code: ${airport.localCode}, Home Link: ${airport.homeLink.getOrElse("None")}, Wikipedia Link: ${airport.wikipediaLink.getOrElse("None")}, Keywords: ${airport.keywords.getOrElse("None")}")
    }
  } else {
    println("Aucun aéroport trouvé dans le fichier Airport.csv.")
  }
}

object runway extends App {
  val cheminFichierRunway = "ressources/runways.csv"
  val runways = CSVParser.parseRunways(cheminFichierRunway)

  // Afficher les informations sur les pistes
  if (runways.nonEmpty) {
    println("Runways:")
    runways.foreach { runway =>
      println(s"ID: ${runway.id}, Airport Ref: ${runway.airportRef}, Airport Ident: ${runway.airportIdent}, Length: ${runway.lengthFt}, Width: ${runway.widthFt}, Surface: ${runway.surface}, Lighted: ${runway.lighted}, Closed: ${runway.closed}, LE Ident: ${runway.leIdent}, LE Latitude: ${runway.leLatitudeDeg.getOrElse("None")}, LE Longitude: ${runway.leLongitudeDeg.getOrElse("None")}, LE Elevation: ${runway.leElevationFt.getOrElse("None")}, LE Heading: ${runway.leHeadingDegT.getOrElse("None")}, LE Displaced Threshold: ${runway.leDisplacedThresholdFt.getOrElse("None")}, HE Ident: ${runway.heIdent}, HE Latitude: ${runway.heLatitudeDeg.getOrElse("None")}, HE Longitude: ${runway.heLongitudeDeg.getOrElse("None")}, HE Elevation: ${runway.heElevationFt.getOrElse("None")}, HE Heading: ${runway.heHeadingDegT.getOrElse("None")}, HE Displaced Threshold: ${runway.heDisplacedThresholdFt.getOrElse("None")}")
    }
  } else {
    println("Aucune piste trouvée dans le fichier Runway.csv.")
  }
}
object DATABASE extends App {
  // Création de la table Airport
  Data.executeQuery(
    """CREATE TABLE IF NOT EXISTS Airport (
      id INT PRIMARY KEY,
      ident VARCHAR(255),
      type VARCHAR(255),
      name VARCHAR(255),
      latitudeDeg DOUBLE,
      longitudeDeg DOUBLE,
      elevationFt INT,
      continent VARCHAR(255),
      iso_country VARCHAR(255),
      isoRegion VARCHAR(255),
      municipality VARCHAR(255),
      scheduledService VARCHAR(255),
      gpsCode VARCHAR(255),
      iataCode VARCHAR(255),
      localCode VARCHAR(255),
      homeLink VARCHAR(255),
      wikipediaLink VARCHAR(255),
      keywords VARCHAR(255)
    );"""
  )

  // Création de la table Runway
  Data.executeQuery(
    """CREATE TABLE IF NOT EXISTS Runway (
      id INT PRIMARY KEY,
      airportRef INT,
      airportIdent VARCHAR(255),
      lengthFt INT,
      widthFt INT,
      surface VARCHAR(255),
      lighted INT,
      closed INT,
      leIdent VARCHAR(255),
      leLatitudeDeg DOUBLE,
      leLongitudeDeg DOUBLE,
      leElevationFt INT,
      leHeadingDegT INT,
      leDisplacedThresholdFt INT,
      heIdent VARCHAR(255),
      heLatitudeDeg DOUBLE,
      heLongitudeDeg DOUBLE,
      heElevationFt INT,
      heHeadingDegT INT,
      heDisplacedThresholdFt INT
    );"""
  )

  // Création de la table Countries
  Data.executeQuery(
    """CREATE TABLE IF NOT EXISTS Countries (
      id INT PRIMARY KEY,
      code VARCHAR(255),
      name VARCHAR(255),
      continent VARCHAR(255),
      wikipediaLink VARCHAR(255),
      keywords VARCHAR(255)
    );"""
  )
}

object insertvaluecountry extends App {
  val countriesFilePath = Paths.get("ressources/countries.csv").toAbsolutePath.toString
  val countriesData = CSVParser.parseCountries(countriesFilePath)

  countriesData.foreach { country =>
    val insertQuery = s"""INSERT INTO Countries VALUES (
      ${country.id},
      '${country.code}',
      '${country.name}',
      '${country.continent}',
      '${country.wikipediaLink}',
      ${country.keywords.map(k => s"'$k'").getOrElse("NULL")}
    );"""

    val result = Try {
      Data.executeQuery(insertQuery)
    }

    result match {
      case scala.util.Success(_) => println("Insertion successful")
      case scala.util.Failure(exception) => println(s"Insertion failed: ${exception.getMessage}")
    }
  }
}

object InsertRunways extends App {
  val runwaysFilePath = Paths.get("ressources/runways.csv").toAbsolutePath.toString
  val runwaysData = CSVParser.parseRunways(runwaysFilePath)

  runwaysData.foreach { runway =>
    val insertQuery =
      s"""INSERT INTO Runway VALUES (
         |  ${runway.id},
         |  ${runway.airportRef},
         |  '${runway.airportIdent}',
         |  ${runway.lengthFt},
         |  ${runway.widthFt},
         |  '${runway.surface}',
         |  ${runway.lighted},
         |  ${runway.closed},
         |  '${runway.leIdent}',
         |  ${runway.leLatitudeDeg.getOrElse("NULL")},
         |  ${runway.leLongitudeDeg.getOrElse("NULL")},
         |  ${runway.leElevationFt.getOrElse("NULL")},
         |  ${runway.leHeadingDegT.getOrElse("NULL")},
         |  ${runway.leDisplacedThresholdFt.getOrElse("NULL")},
         |  '${runway.heIdent}',
         |  ${runway.heLatitudeDeg.getOrElse("NULL")},
         |  ${runway.heLongitudeDeg.getOrElse("NULL")},
         |  ${runway.heElevationFt.getOrElse("NULL")},
         |  ${runway.heHeadingDegT.getOrElse("NULL")},
         |  ${runway.heDisplacedThresholdFt.getOrElse("NULL")}
         |);""".stripMargin

    val result = Try {
      Data.executeQuery(insertQuery)
    }

    result match {
      case scala.util.Success(_) => println("Insertion successful")
      case scala.util.Failure(exception) => println(s"Insertion failed: ${exception.getMessage}")
    }
  }
}

object InsertAirports extends App {
  val airportsFilePath = Paths.get("ressources/airports.csv").toAbsolutePath.toString
  val airportsData = CSVParser.parseAirports(airportsFilePath)

  airportsData.foreach { airport =>
    val insertQuery =
      s"""INSERT INTO Airport VALUES (
         |  ${airport.id},
         |  '${airport.ident}',
         |  '${airport.`type`}',
         |  '${airport.name}',
         |  ${airport.latitudeDeg},
         |  ${airport.longitudeDeg},
         |  ${airport.elevationFt},
         |  '${airport.continent}',
         |  '${airport.iso_country}',
         |  '${airport.isoRegion}',
         |  '${airport.municipality}',
         |  '${airport.scheduledService}',
         |  '${airport.gpsCode}',
         |  '${airport.iataCode}',
         |  '${airport.localCode}',
         |  ${airport.homeLink.map(h => s"'$h'").getOrElse("NULL")},
         |  ${airport.wikipediaLink.map(w => s"'$w'").getOrElse("NULL")},
         |  ${airport.keywords.map(k => s"'$k'").getOrElse("NULL")}
         |);""".stripMargin

    val result = Try {
      Data.executeQuery(insertQuery)
    }

    result match {
      case scala.util.Success(_) => println("Insertion successful")
      case scala.util.Failure(exception) => println(s"Insertion failed: ${exception.getMessage}")
    }
  }
}

object Mainrequete extends App {

  def printMenu(): Unit = {
    println("Menu:")
    println("1. Get airports by country name")
    println("2. Get top 10 airports by country count")
    println("3. Get bottom 10 airports by country count")
    println("4. Get runway types per country")
    println("5. Get top 10 common runway latitudes")
    println("0. Quit")
  }

  def handleOption(option: Int): Unit = {
    option match {
      case 1 =>
        println("Enter country name:")
        val countryName = readLine()
        val airports = Requete.getAirportsByCountryName(countryName)
        airports.foreach(println)

      case 2 =>
        val top10 = Requete.getTop10AirportsByCountryCount()
        println("Top 10 airports by country count:")
        top10.foreach(println)

      case 3 =>
        val bottom10 = Requete.getBottom10AirportsByCountryCount()
        println("Bottom 10 airports by country count:")
        bottom10.foreach(println)

      case 4 =>
        val runwayTypesPerCountry = Requete.getRunwayTypesPerCountry()
        println("Runway types per country:")
        runwayTypesPerCountry.foreach { case (country, runwayTypes) =>
          println(s"$country: ${runwayTypes.mkString(", ")}")
        }

      case 5 =>
        val commonRunwayLatitudes = Requete.getTop10CommonRunwayLatitudes()
        println("Top 10 common runway latitudes:")
        commonRunwayLatitudes.foreach(println)

      case 0 =>
        println("Exiting program.")
        System.exit(0)

      case _ =>
        println("Invalid option. Please enter a valid option.")
    }
  }

  var option = -1
  while (option != 0) {
    printMenu()
    println("Enter option:")
    option = readLine().toInt
    handleOption(option)
  }
}