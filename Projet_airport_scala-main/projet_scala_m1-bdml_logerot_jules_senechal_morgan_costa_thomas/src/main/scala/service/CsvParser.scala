package service

import java.io.File
import scala.io.Source
import model.{Airport, Countries, Runway}
import scala.util.Try

object CSVParser {
  def parseCountries(filePath: String): List[Countries] = {
    val lines = Source.fromFile(filePath).getLines().drop(1)
    lines.map { line =>
      val fields = line.split(",").map(_.trim)
      Countries(
        id = fields.lift(0).flatMap(s => Try(s.toInt).toOption).getOrElse(0),
        code = fields.lift(1).getOrElse(""),
        name = fields.lift(2).getOrElse(""),
        continent = fields.lift(3).getOrElse(""),
        wikipediaLink = fields.lift(4).getOrElse(""),
        keywords = fields.lift(5).flatMap(s => if (s.nonEmpty) Some(s) else None)
      )
    }.toList
  }

  def parseAirports(filePath: String): List[Airport] = {
    val lines = Source.fromFile(filePath).getLines().drop(1)
    lines.map { line =>
      val fields = line.split(",").map(_.trim)
      Airport(
        id = fields.lift(0).flatMap(s => Try(s.toInt).toOption).getOrElse(0),
        ident = fields.lift(1).getOrElse(""),
        `type` = fields.lift(2).getOrElse(""),
        name = fields.lift(3).getOrElse(""),
        latitudeDeg = fields.lift(4).flatMap(s => Try(s.toDouble).toOption).getOrElse(0.0),
        longitudeDeg = fields.lift(5).flatMap(s => Try(s.toDouble).toOption).getOrElse(0.0),
        elevationFt = fields.lift(6).flatMap(s => if (s.nonEmpty) Try(s.toInt).toOption else None).getOrElse(0),
        continent = fields.lift(7).getOrElse(""),
        iso_country = fields.lift(8).getOrElse(""),
        isoRegion = fields.lift(9).getOrElse(""),
        municipality = fields.lift(10).getOrElse(""),
        scheduledService = fields.lift(11).getOrElse(""),
        gpsCode = fields.lift(12).getOrElse(""),
        iataCode = fields.lift(13).getOrElse(""),
        localCode = fields.lift(14).getOrElse(""),
        homeLink = fields.lift(15).flatMap(s => if (s.nonEmpty) Some(s) else None),
        wikipediaLink = fields.lift(16).flatMap(s => if (s.nonEmpty) Some(s) else None),
        keywords = fields.lift(17).flatMap(s => if (s.nonEmpty) Some(s) else None)
      )
    }.toList
  }

  def parseRunways(filePath: String): List[Runway] = {
    val lines = Source.fromFile(filePath).getLines().drop(1)
    lines.map { line =>
      val fields = line.split(",").map(_.trim)
      Runway(
        id = fields.lift(0).flatMap(s => Try(s.toInt).toOption).getOrElse(0),
        airportRef = fields.lift(1).flatMap(s => Try(s.toInt).toOption).getOrElse(0),
        airportIdent = fields.lift(2).getOrElse(""),
        lengthFt = fields.lift(3).flatMap(s => Try(s.toInt).toOption).getOrElse(0),
        widthFt = fields.lift(4).flatMap(s => Try(s.toInt).toOption).getOrElse(0),
        surface = fields.lift(5).getOrElse(""),
        lighted = fields.lift(6).flatMap(s => Try(s.toInt).toOption).getOrElse(0),
        closed = fields.lift(7).flatMap(s => Try(s.toInt).toOption).getOrElse(0),
        leIdent = fields.lift(8).getOrElse(""),
        leLatitudeDeg = fields.lift(9).flatMap(s => Try(s.toDouble).toOption),
        leLongitudeDeg = fields.lift(10).flatMap(s => Try(s.toDouble).toOption),
        leElevationFt = fields.lift(11).flatMap(s => Try(s.toInt).toOption),
        leHeadingDegT = fields.lift(12).flatMap(s => Try(s.toInt).toOption),
        leDisplacedThresholdFt = fields.lift(13).flatMap(s => Try(s.toInt).toOption),
        heIdent = fields.lift(14).getOrElse(""),
        heLatitudeDeg = fields.lift(15).flatMap(s => Try(s.toDouble).toOption),
        heLongitudeDeg = fields.lift(16).flatMap(s => Try(s.toDouble).toOption),
        heElevationFt = fields.lift(17).flatMap(s => Try(s.toInt).toOption),
        heHeadingDegT = fields.lift(18).flatMap(s => Try(s.toInt).toOption),
        heDisplacedThresholdFt = fields.lift(19).flatMap(s => Try(s.toInt).toOption)
      )
    }.toList
  }
}