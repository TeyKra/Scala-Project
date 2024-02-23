package model

case class Countries(
    id: Int,
    code: String,
    name: String,
    continent: String,
    wikipediaLink: String,
    keywords: Option[String]
)