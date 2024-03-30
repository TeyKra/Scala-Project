# Projet_Scala_M1

# Instructions for Submission

To submit your project, please provide the following:

- Your last commit hash here.
- A Google Drive link to a video showing your project working at the given commit hash.

Ensure the Git repo and video are private and shared with: teacher's email

## Starting Your Project

You must start your project from the command line using:
- `git log`
- `sbt run`

Or launch it from an IDE where your commit hash is visible.

## Main Part (For Everyone To Reach 10/20)

### C.1) Project Requirements

For this project, you'll write a program that parses 3 CSV files containing data for countries, airports, and runway information. The files must remain intact.

- Create an sbt Scala project.
- Feel free to use any Scala library/framework for writing your tests.
- **No dependency** is allowed for parsing CSV files.
- Avoid using the following keywords unless writing an optional part and with prior permission: `catch`, `var`, `for`, `return`, `.get` from Option type, `null`, `throw`, `head`.
- Do not nest `foreach` loops.
- If not using a database, you're allowed to use mutable collection(s) as a replacement.

#### Code Instructions:

- Separate code for parsing, storing/querying, and user interface into 3 distinct classes, objects, or packages.
- User interface code (e.g., `println`) should only be in the user interface package/class.
- `Runway` and `Airport` must have their own case classes and companion objects, with a `from` method in each companion object for deserializing from a CSV line.

### M.2.1) Program Functionality

Your program will be a command-line application that offers the user two options: Query or Reports.

- **Query Option:** Ask the user for a country name or code and display the airports & runways at each airport. The input can be a country code or country name.
- **Reports Option:** Display the following information (possibly through a menu):
  - 10 countries with the highest and lowest number of airports (with counts).
  - Types of runways (as indicated in the "surface" column) per country.
  - The top 10 most common runway latitudes (indicated in the "le_ident" column).

### Achievement Points

- Completing the project: 10 points
- Clean code: 4 points
- Correct submission: 1 point
- **Total:** Up to 15/20

## Chosen Part (Optional Tasks)

### C.1) Advanced Features

- Implement partial/fuzzy name matching for country search (2pt).
- Use a database (in-memory for this exercise, e.g., H2, SQLite) (4pt).
- Develop a GUI (6pt).
- Provide a REST API (4pt).
- Use `Future` to improve app speed (2pt).
- Utilize parallel collections (1pt).
- Write the project using Scala 3 (3pt).
- Correct use of value class (Scala 2) or opaque type (Scala 3).

### Libraries Allowed

For parts 2 to 4, you're allowed to use Scala libraries such as:
- Anorm, slick, squeryl, reactive-mongo, Casbah, elastic4s, Quil, doobie, Scalikejdbc, sdbc, sorm, mongo-scala-driver for databases.
- Finch, http4s, Akka Http, Spray, Play for REST APIs. (Note: finatra and scalatra are forbidden)

### CSV Files

You will be working with the following CSV files:
- `airports.csv`
- `countries.csv`
- `runways.csv`

>Note: You may theoretically score more than 20/20, but CTI rules will cap it at 20/20.
