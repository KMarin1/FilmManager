package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Film;
import hr.algebra.model.Glumac;
import hr.algebra.model.Korisnik;
import hr.algebra.model.Redatelj;
import hr.algebra.model.Uloga;
import hr.algebra.model.Zanr;
import hr.algebra.utilities.DataAccessException;
import hr.algebra.utilities.ValidationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class SqlRepository implements Repository {

    // CRUD za Film
    public List<Film> getAllFilms() throws DataAccessException {
        List<Film> films = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM Film"); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                films.add(new Film(
                        rs.getInt("id"),
                        rs.getString("naziv"),
                        rs.getInt("godina"),
                        rs.getString("opis"),
                        (Redatelj) getRedateljbyId(rs.getInt("redatelj_id")).orElse(null),
                        getZanrById(rs.getInt("zanr_id")).orElse(null),
                        getGlumciForFilm(rs.getInt("id")),
                        rs.getString("slikaPutanja")
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Cannot get films", e);
        }
        return films;
    }

    @Override
    public Optional<Film> getFilmById(int id) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spGetFilmById(?)}")) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Film(
                            rs.getInt("id"),
                            rs.getString("naziv"),
                            rs.getInt("godina"),
                            rs.getString("opis"),
                            (Redatelj) getRedateljbyId(rs.getInt("redatelj_id")).orElse(null),
                            getZanrById(rs.getInt("zanr_id")).orElse(null),
                            getGlumciForFilm(rs.getInt("id")),
                            rs.getString("slikaPutanja")
                    ));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Cannot get film by id", e);
        }
    }

    @Override
    public boolean insertFilm(Film film) throws SQLException, DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spInsertFilm(?,?,?,?,?,?,?)}")) {

            cs.setString(1, film.getNaziv());
            cs.setInt(2, film.getGodina());
            cs.setString(3, film.getOpis());
            cs.setInt(4, film.getRedatelj().getId());
            cs.setInt(5, film.getZanr().getId());
            cs.setString(6, film.getPutanjaSlike());
            cs.registerOutParameter(7, Types.INTEGER);
            boolean exec = cs.executeUpdate() > 0;
            film.setId(cs.getInt(7));
            return exec;
            
            
        } catch (SQLException e) {
            throw new SQLException("Cannot insert film", e);

        }
    }

    @Override
    public boolean updateFilm(Film film) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spUpdateFilm(?,?,?,?,?,?,?)}")) {

            cs.setInt(1, film.getId());
            cs.setString(2, film.getNaziv());
            cs.setInt(3, film.getGodina());
            cs.setString(4, film.getOpis());
            cs.setInt(5, film.getRedatelj().getId());
            cs.setInt(6, film.getZanr().getId());
            cs.setString(7, film.getPutanjaSlike());

            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Cannot update film", e);
        }
    }

    @Override
    public boolean deleteFilm(int id) throws SQLException, DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spDeleteFilm(?)}")) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("Cannot delete film", e);
        }
    }

    @Override
    // CRUD za Glumac
    public List<Glumac> getAllGlumci() throws SQLException, DataAccessException {
        List<Glumac> glumci = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT id, ime, prezime FROM Glumac"); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Glumac glumac = new Glumac(
                        rs.getInt("id"),
                        rs.getString("ime"),
                        rs.getString("prezime"));
                glumci.add(glumac);
            }
        } catch (SQLException e) {
            throw new SQLException("Cannot get all glumci", e);
        }
        return glumci;
    }

    @Override
    public Optional<Glumac> getGlumacById(int id) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spGetGlumacById(?)}")) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Glumac(
                            rs.getInt("id"),
                            rs.getString("ime"),
                            rs.getString("prezime")
                    ));
                }

                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Cannot get glumac by id", e);
        }

    }

    @Override
    public Optional<Glumac> getGlumacByName(String ime, String prezime) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spGetGlumacByName(?,?)}")) {

            cs.setString(1, ime);
            cs.setString(2, prezime);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    Glumac g = (new Glumac(
                            rs.getInt("id"),
                            rs.getString("ime"),
                            rs.getString("prezime")
                    ));
                    return Optional.of(g);
                }
                
            }
        } catch (SQLException e) {
            throw new DataAccessException("Cannot get glumac by Name", e);
        }

    return Optional.empty();
    }

    @Override
    public boolean insertGlumac(Glumac glumac) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spInsertGlumac(?,?,?)}")) {

            cs.setString(1, glumac.getIme());
            cs.setString(2, glumac.getPrezime());
            cs.registerOutParameter(3, Types.INTEGER);
            boolean exec = cs.executeUpdate() > 0;
            glumac.setId(cs.getInt(3));
            return exec;
        } catch (SQLException e) {
            throw new DataAccessException("Cannot get all glumci", e);
        }
    }

    @Override
    public boolean updateGlumac(Glumac glumac) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spUpdateGlumac(?,?,?)}")) {

            cs.setInt(1, glumac.getId());
            cs.setString(2, glumac.getIme());
            cs.setString(3, glumac.getPrezime());

            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Cannot update glumci", e);
        }
    }

    @Override
    public boolean deleteGlumac(int id) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spDeleteGlumac(?)}")) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Cannot delete glumci", e);
        }
    }

    // CRUD za Redatelj
    @Override
    public List<Redatelj> getAllRedatelji() throws SQLException, DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        List<Redatelj> directors = new ArrayList<>();
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT id, ime, prezime FROM Redatelj"); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Redatelj redatelj = new Redatelj(
                        rs.getInt("id"),
                        rs.getString("ime"),
                        rs.getString("prezime"));
                directors.add(redatelj);
            }
        } catch (SQLException e) {
            throw new SQLException("Cannot get all redatelj", e);
        }
        return directors;
    }

    @Override
    public Optional<Redatelj> getRedateljbyId(int id) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        Optional<Redatelj> redatelj = null;
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spGetRedateljById(?)}")) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Redatelj(
                            rs.getInt("id"),
                            rs.getString("ime"),
                            rs.getString("prezime")
                    ));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataAccessException("Cannot get redatelj by id", e);
        }
    }

    @Override
    public Optional<Redatelj> getRedateljByName(String name) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        Optional<Redatelj> redatelj = null;
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT id, ime, prezime FROM Redatelj WHERE ime + ' ' + prezime = ?")) {

            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Redatelj(
                            rs.getInt("id"),
                            rs.getString("ime"),
                            rs.getString("prezime")
                    ));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataAccessException("Cannot get redatelj by name", e);
        }

    }

    @Override
    public boolean insertRedatelj(Redatelj redatelj) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spInsertRedatelj(?,?,?)}")) {

            cs.setString(1, redatelj.getIme());
            cs.setString(2, redatelj.getPrezime());
            cs.registerOutParameter(3, Types.INTEGER);
            boolean exec = cs.executeUpdate() > 0;
            redatelj.setId(cs.getInt(3));
            return exec;
        } catch (SQLException e) {
            throw new DataAccessException("Cannot insert redatelj", e);
        }
    }

    @Override
    public boolean updateRedatelj(Redatelj redatelj) throws SQLException, DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spUpdateRedatelj(?,?,?)}")) {

            cs.setString(1, redatelj.getIme());
            cs.setString(2, redatelj.getPrezime());
            cs.setInt(3, redatelj.getId());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("Cannot update redatelj", e);
        }
    }

    @Override
    public boolean deleteRedatelj(int id) throws SQLException, DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spDeleteRedatelj(?)}")) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("Cannot delete film", e);
        }
    }

    // CRUD za Zanr
    @Override
    public List<Zanr> getAllZanrovi() throws SQLException, DataAccessException {
        List<Zanr> genres = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM Zanr"); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Zanr zanr = new Zanr(rs.getInt("id"),
                        rs.getString("naziv"));
                genres.add(zanr);
            }
        } catch (SQLException e) {
            throw new SQLException("Cannot get all zanr", e);
        }
        return genres;
    }

    @Override
    public boolean insertZanr(Zanr zanr) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spInsertZanr(?,?)}")) {

            cs.setString(1, zanr.getNaziv());
            cs.registerOutParameter(2, Types.INTEGER);
            boolean exec = cs.executeUpdate() > 0;
            zanr.setId(cs.getInt(2));
            return exec;
        } catch (SQLException e) {
            throw new DataAccessException("Cannot insert zanr", e);
        }
    }

    @Override
    public boolean updateZanr(Zanr zanr) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spUpdateZanr(?,?)}")) {

            cs.setString(1, zanr.getNaziv());
            cs.setInt(2, zanr.getId());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Cannot update zanr", e);
        }
    }

    @Override
    public boolean deleteZanr(int id) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spDeleteZanr(?)}")) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Cannot delete zanr", e);

        }
    }

    @Override
    public List<Glumac> getGlumciForFilm(int filmId) throws DataAccessException {
        List<Glumac> list = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(
                "SELECT g.id, g.ime, g.prezime "
                + "FROM Glumac g "
                + "JOIN FilmGlumac fg ON g.id = fg.glumac_id "
                + "WHERE fg.film_id = ?"
        )) {
            ps.setInt(1, filmId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Glumac(
                            rs.getInt("id"),
                            rs.getString("ime"),
                            rs.getString("prezime")
                    ));
                }
            }
            return list;
        } catch (SQLException ex) {
            throw new DataAccessException("Error fetching glumci for film " + filmId, ex);
        }
    }

    @Override
    public Optional<Zanr> getZanrById(int zanrId) throws DataAccessException {
        Zanr zanr = null;
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spGetZanrById(?)}")) {

            cs.setInt(1, zanrId);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Zanr(
                            rs.getInt("id"),
                            rs.getString("naziv")
                    ));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataAccessException("Cannot get zanr by id", e);
        }

    }

    @Override
    public Optional getZanrByName(String name) throws DataAccessException {
        Zanr zanr = null;
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); PreparedStatement cs = connection.prepareStatement("SELECT id, naziv FROM Zanr WHERE naziv = ?")) {

            cs.setString(1, name);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Zanr(
                            rs.getInt("id"),
                            rs.getString("naziv")
                    ));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataAccessException("Cannot get zanr by name", e);
        }
    }

    @Override
    public void release() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional getKorisnikByCredentials(String KorisnickoIme, String Password) throws DataAccessException {
        Korisnik korisnik = null;
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spGetKorisnikByCredentials(?,?)}")) {

            cs.setString(1, KorisnickoIme);
            cs.setString(2, Password);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Korisnik(
                            rs.getInt("id"),
                            rs.getString("korisnickoIme"),
                            rs.getString("lozinka"),
                            Uloga.valueOf(rs.getString("uloga"))
                    ));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            try {
                throw new SQLException("Cant get korisnik by cred", e);
            } catch (SQLException ex) {
                Logger.getLogger(SqlRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Optional.empty();
    }

    @Override
    public void insertKorisnik(Korisnik korisnik) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spInsertKorisnik(?,?,?,?)}")) {

            cs.setString(1, korisnik.getKorisnickoIme());
            cs.setString(2, korisnik.getLozinka());
            cs.setString(3, korisnik.getUloga().name());
            cs.registerOutParameter(4, java.sql.Types.INTEGER);
            cs.execute();

            int newId = cs.getInt(4);
            korisnik.setId(newId);
        } catch (SQLException e) {
            throw new DataAccessException("Cannot insert korisnik", e);
        }
    }

    @Override
    public void updateKorisnik(Korisnik korisnik) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spUpdateRedatelj(?,?,?)}")) {

            cs.setString(1, korisnik.getKorisnickoIme());
            cs.setString(2, korisnik.getLozinka());
            cs.setString(3, korisnik.getUloga().name());
            cs.setInt(3, korisnik.getId());
        } catch (SQLException e) {
            try {
                throw new SQLException("Cannot update korisnik", e);
            } catch (SQLException ex) {
                Logger.getLogger(SqlRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void deleteKorisnik(int id) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); CallableStatement cs = connection.prepareCall("{call spDeleteRedatelj(?)}")) {

            cs.setInt(1, id);
        } catch (SQLException e) {
            try {
                throw new SQLException("Cannot delete film", e);
            } catch (SQLException ex) {
                Logger.getLogger(SqlRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void removeGlumacFromFilm(int filmId) throws DataAccessException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); PreparedStatement cs = connection.prepareStatement("DELETE FROM FilmGlumac WHERE film_id = ?")) {

            cs.setInt(1, filmId);
            cs.execute();
        } catch (SQLException ex) {
            Logger.getLogger(SqlRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    @Override
    public void addGlumacToFilm(int filmId, Glumac glumac) throws DataAccessException, SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); PreparedStatement cs = connection.prepareStatement("INSERT INTO FilmGlumac(film_id,glumac_id) VALUES (?,?)")) {

            try {
                cs.setInt(1, filmId);
                cs.setInt(2, glumac.getId());
            } catch (SQLException ex) {
                Logger.getLogger(SqlRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

            cs.execute();
        };
    }
}
