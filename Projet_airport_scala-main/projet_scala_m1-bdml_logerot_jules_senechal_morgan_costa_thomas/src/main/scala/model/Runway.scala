package model

case class Runway(
    id: Int,
    airportRef: Int,
    airportIdent: String,
    lengthFt: Int,
    widthFt: Int,
    surface: String,
    lighted: Int,
    closed: Int,
    leIdent: String,
    leLatitudeDeg: Option[Double],
    leLongitudeDeg: Option[Double],
    leElevationFt: Option[Int],
    leHeadingDegT: Option[Int],
    leDisplacedThresholdFt: Option[Int],
    heIdent: String,
    heLatitudeDeg: Option[Double],
    heLongitudeDeg: Option[Double],
    heElevationFt: Option[Int],
    heHeadingDegT: Option[Int],
    heDisplacedThresholdFt: Option[Int]
)