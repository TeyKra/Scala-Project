package model

case class Airport(
    id: Int,
    ident: String,
    `type`: String,
    name: String,
    latitudeDeg: Double,
    longitudeDeg: Double,
    elevationFt: Int,
    continent: String,
    iso_country: String,
    isoRegion: String,
    municipality: String,
    scheduledService: String,
    gpsCode: String,
    iataCode: String,
    localCode: String,
    homeLink: Option[String],
    wikipediaLink: Option[String],
    keywords: Option[String]
)