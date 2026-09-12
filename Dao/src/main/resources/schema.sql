use FilmManager1
DROP ALL TABLE;
CREATE TABLE Zanr (
  Id INT IDENTITY PRIMARY KEY,
  Naziv NVARCHAR(50) NOT NULL
);
GO

CREATE TABLE Redatelj (
  Id INT IDENTITY PRIMARY KEY,
  Ime NVARCHAR(50) NOT NULL,
  Prezime NVARCHAR(50) NOT NULL
);
GO

CREATE TABLE Glumac (
  Id INT IDENTITY PRIMARY KEY,
  Ime NVARCHAR(50) NOT NULL,
  Prezime NVARCHAR(50) NOT NULL
);
GO
drop table korisnik
CREATE TABLE Korisnik(
    Id INT IDENTITY PRIMARY KEY,
    KorisnickoIme NVARCHAR(50) NOT NULL,
    Lozinka NVARCHAR(50) NOT NULL,
    Uloga NVARCHAR(20) NOT NULL
);

CREATE TABLE Film (
  Id INT IDENTITY PRIMARY KEY,
  Naziv NVARCHAR(100) NOT NULL,
  Opis NVARCHAR(MAX) NULL,
  RedateljId INT NOT NULL FOREIGN KEY REFERENCES Redatelj(Id),
  ZanrId INT NOT NULL FOREIGN KEY REFERENCES Zanr(Id),
  SlikaPutanja NVARCHAR(200) NULL
);
GO

CREATE TABLE FilmGlumac (
  film_id INT NOT NULL FOREIGN KEY REFERENCES Film(Id),
  GlumacId INT NOT NULL FOREIGN KEY REFERENCES Glumac(Id),
  PRIMARY KEY (film_id, GlumacId)
);
GO

CREATE or alter PROCEDURE spInsertFilm
  @naziv NVARCHAR(100),
  @godina INT,
  @opis NVARCHAR(MAX),
  @redateljId INT,
  @zanrId INT,
  @slikaPutanja NVARCHAR(200),
  @newId INT OUTPUT
AS
BEGIN
  INSERT INTO Film (Naziv, godina, Opis,redatelj_id, zanr_id, SlikaPutanja)
    VALUES (@naziv, @godina, @opis, @redateljId, @zanrId, @slikaPutanja);
  SET @newId = SCOPE_IDENTITY();
END
GO

CREATE OR ALTER PROCEDURE spGetAllFilms
AS
BEGIN
  SELECT * FROM Film;
END
GO

CREATE OR ALTER PROCEDURE spGetFilmById
  @id INT
AS
BEGIN
  SELECT * FROM Film WHERE Id = @id;
END
GO

CREATE or alter PROCEDURE spUpdateFilm
  @id INT,
  @naziv NVARCHAR(100),
  @godina INT,
  @opis NVARCHAR(MAX),
  @redateljId INT,
  @zanrId INT,
  @slikaPutanja NVARCHAR(200)
AS
BEGIN
  UPDATE Film
    SET Naziv = @naziv,
		godina = @godina,
        Opis = @opis,
        redatelj_id = @redateljId,
        zanr_id = @zanrId,
        SlikaPutanja = @slikaPutanja
  WHERE Id = @id;
END
GO

CREATE OR ALTER PROCEDURE spDeleteFilm
  @id INT
AS
BEGIN
  DELETE FROM Film WHERE Id = @id;
END
GO

CREATE OR ALTER PROCEDURE spInsertGlumac
  @ime NVARCHAR(50),
  @prezime NVARCHAR(50),
  @newId INT OUTPUT
AS
BEGIN
  INSERT INTO Glumac (Ime, Prezime)
    VALUES (@ime, @prezime);
  SET @newId = SCOPE_IDENTITY();
END
GO

CREATE OR ALTER PROCEDURE spGetAllGlumci
AS
BEGIN
  SELECT * FROM Glumac;
END
GO

CREATE or Alter PROCEDURE spGetGlumacByName
    @Ime NVARCHAR(50),
    @Prezime NVARCHAR(50)
AS
BEGIN
    SELECT id, ime, prezime FROM Glumac WHERE ime = @Ime AND prezime = @Prezime
END
go

CREATE or alter PROCEDURE spGetGlumacById
  @id INT
AS
BEGIN
  SELECT * FROM Glumac WHERE Id = @id;
END
GO

CREATE OR ALTER PROCEDURE spUpdateGlumac
  @id INT,
  @ime NVARCHAR(50),
  @prezime NVARCHAR(50)
AS
BEGIN
  UPDATE Glumac
    SET Ime = @ime,
        Prezime = @prezime
  WHERE Id = @id;
END
GO

CREATE OR ALTER PROCEDURE spDeleteGlumac
  @id INT
AS
BEGIN
  DELETE FROM Glumac WHERE Id = @id;
END
GO


CREATE OR ALTER PROCEDURE spInsertRedatelj
  @ime NVARCHAR(50),
  @prezime NVARCHAR(50),
  @newId INT OUTPUT
AS
BEGIN
  INSERT INTO Redatelj (Ime, Prezime)
    VALUES (@ime, @prezime);
  SET @newId = SCOPE_IDENTITY();
END
GO

CREATE OR ALTER PROCEDURE spGetAllRedatelji
AS
BEGIN
  SELECT * FROM Redatelj;
END
GO

CREATE OR ALTER PROCEDURE spGetRedateljById
  @id INT
AS
BEGIN
  SELECT * FROM Redatelj WHERE Id = @id;
END
GO

CREATE OR ALTER PROCEDURE spUpdateRedatelj
  @id INT,
  @ime NVARCHAR(50),
  @prezime NVARCHAR(50)
AS
BEGIN
  UPDATE Redatelj
    SET Ime = @ime,
        Prezime = @prezime
  WHERE Id = @id;
END
GO

CREATE OR ALTER PROCEDURE spDeleteRedatelj
  @id INT
AS
BEGIN
  DELETE FROM Redatelj WHERE Id = @id;
END
GO

CREATE OR ALTER PROCEDURE spInsertZanr
  @naziv NVARCHAR(50),
  @newId INT OUTPUT
AS
BEGIN
  INSERT INTO Zanr (Naziv)
    VALUES (@naziv);
  SET @newId = SCOPE_IDENTITY();
END
GO

CREATE OR ALTER PROCEDURE spGetAllZanrovi
AS
BEGIN
  SELECT * FROM Zanr;
END
GO

CREATE OR ALTER PROCEDURE spGetZanrById
  @id INT
AS
BEGIN
  SELECT * FROM Zanr WHERE Id = @id;
END
GO

CREATE OR ALTER PROCEDURE spUpdateZanr
  @id INT,
  @naziv NVARCHAR(50)
AS
BEGIN
  UPDATE Zanr
    SET Naziv = @naziv
  WHERE Id = @id;
END
GO

CREATE OR ALTER PROCEDURE spDeleteZanr
  @id INT
AS
BEGIN
  DELETE FROM Zanr WHERE Id = @id;
END
GO

CREATE OR ALTER PROCEDURE spInsertKorisnik
  @korisnickoIme NVARCHAR(50),
  @lozinka NVARCHAR(255),
  @uloga NVARCHAR(20),
  @newId INT OUTPUT
AS
BEGIN
  INSERT INTO Korisnik (KorisnickoIme, Lozinka, Uloga)
    VALUES (@korisnickoIme, @lozinka, @uloga);
  SET @newId = SCOPE_IDENTITY();
END
GO

CREATE OR ALTER PROCEDURE spGetAllKorisnici
AS
BEGIN
  SELECT * FROM Korisnik;
END
GO

CREATE OR ALTER PROCEDURE spGetKorisnikByKorisnickoIme
  @korisnickoIme NVARCHAR(50)
AS
BEGIN
  SELECT * FROM Korisnik WHERE KorisnickoIme = @korisnickoIme;
END
GO

CREATE OR ALTER PROCEDURE spGetKorisnikByCredentials (
    @korisnickoIme NVARCHAR(50),
    @lozinka NVARCHAR(50)
)
AS
BEGIN
    SELECT * FROM Korisnik
    WHERE KorisnickoIme = @korisnickoIme AND Lozinka = @lozinka
END
GO

CREATE OR ALTER PROCEDURE spUpdateKorisnik
  @id INT,
  @korisnickoIme NVARCHAR(50),
  @lozinka NVARCHAR(50),
  @uloga NVARCHAR(20)
AS
BEGIN
  UPDATE Korisnik
    SET KorisnickoIme = @korisnickoIme,
        Lozinka = @lozinka,
        Uloga = @uloga
  WHERE Id = @id;
END
GO

CREATE OR ALTER PROCEDURE spDeleteKorisnik
  @id INT
AS
BEGIN
  DELETE FROM Korisnik WHERE Id = @id;
END
GO

CREATE OR ALTER PROCEDURE spAddGlumacToFilm
  @film_id INT,
  @glumacId INT
AS
BEGIN
  INSERT INTO FilmGlumac (film_id, glumac_id)
    VALUES (@film_id, @glumacId);
END
GO

CREATE OR ALTER PROCEDURE spRemoveGlumacFromFilm
  @film_id INT,
  @glumacId INT
AS
BEGIN
  DELETE FROM FilmGlumac
    WHERE film_id = @film_id AND glumac_id = @glumacId;
END
GO
