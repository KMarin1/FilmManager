# FilmManager

Desktop film catalogue: films, directors, actors, genres and roles, loaded from an RSS
feed into SQL Server and browsed in a Swing UI. University project (Algebra, Java),
2024/25.

## Stack

Java 17 · Maven (three modules) · Swing · JDBC / SQL Server · RSS parsing

## Structure

```
Dao/          data access — Repository interface, SQL implementation, RSS source,
              domain model (Film, Redatelj, Glumac, Zanr, Uloga, Korisnik)
Utilities/    parsers and factories shared by the other modules
FilmManager/  the Swing application
```

The split is the point of the assignment: the UI only sees the `Repository` interface,
and which implementation it gets is decided at runtime.

- `RepositoryFactory` reads `repository.properties` and instantiates the configured
  class by name, so swapping the SQL store for something else is a config change.
- `DataSourceSingleton` owns the one JDBC data source, built from `db.properties`.
- `ParserFactory` hands back the right parser for a source; `FilmParser` walks an RSS
  feed and turns items into `Film` objects, downloading the poster image for each.
- `SqlRepository` talks to SQL Server through stored procedures, including user lookup
  by credentials for the login screen.

## Running it

1. Create a SQL Server database and run the schema/procedures (see `Dao` for the
   expected tables and procedure names).
2. Edit `Dao/src/main/resources/config/db.properties` — server, database, user,
   password.
3. `mvn package`, then run the `FilmManager` module. Poster images are cached under
   `FilmManager/assets/` at runtime and are not part of the repo.

Requires a JDK (17 or newer), not just a JRE.
