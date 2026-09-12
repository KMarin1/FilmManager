/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package hr.algebra.dal;

import hr.algebra.model.Film;
import hr.algebra.model.Glumac;
import hr.algebra.model.Korisnik;
import hr.algebra.model.Redatelj;
import hr.algebra.model.Zanr;
import hr.algebra.utilities.DataAccessException;
import java.sql.SQLException;
import java.util.List;
import hr.algebra.utilities.ValidationException;
import java.util.Optional;

public interface Repository<T> {

    List<Film> getAllFilms() throws DataAccessException;
    Optional<Film> getFilmById(int id) throws DataAccessException;
    boolean insertFilm(Film film) throws DataAccessException, SQLException;
    boolean updateFilm(Film film) throws DataAccessException;
    boolean deleteFilm(int id) throws DataAccessException, SQLException;

    List<Glumac> getAllGlumci() throws DataAccessException,SQLException;
    Optional<Glumac> getGlumacById(int id) throws DataAccessException;
    Optional<Glumac> getGlumacByName(String ime, String prezime) throws DataAccessException;

    boolean insertGlumac(Glumac glumac) throws DataAccessException;
    boolean updateGlumac(Glumac glumac) throws DataAccessException;
    boolean deleteGlumac(int id) throws DataAccessException;

    List<Redatelj> getAllRedatelji() throws DataAccessException,SQLException;
    public Optional<Redatelj> getRedateljbyId(int id) throws DataAccessException;
    public Optional<Redatelj> getRedateljByName(String name) throws DataAccessException;
    boolean insertRedatelj(Redatelj redatelj) throws DataAccessException;
    boolean updateRedatelj(Redatelj redatelj) throws DataAccessException,SQLException;
    boolean deleteRedatelj(int id) throws DataAccessException,SQLException;

    List<Zanr> getAllZanrovi() throws DataAccessException,SQLException;
    Optional<Zanr> getZanrById(int id) throws DataAccessException;
    Optional<Zanr> getZanrByName(String name) throws DataAccessException;
    boolean insertZanr(Zanr zanr) throws DataAccessException;
    boolean updateZanr(Zanr zanr) throws DataAccessException;
    boolean deleteZanr(int id) throws DataAccessException;

    List<Glumac> getGlumciForFilm(int filmId) throws DataAccessException;
    void removeGlumacFromFilm(int filmId) throws DataAccessException,SQLException;
    void addGlumacToFilm(int filmId, Glumac glumac) throws DataAccessException,SQLException;
    
    Optional<Korisnik> getKorisnikByCredentials(String KorisnickoIme, String Password) throws DataAccessException;
    void insertKorisnik(Korisnik korisnik) throws DataAccessException;
    void updateKorisnik(Korisnik korisnik) throws DataAccessException;
    void deleteKorisnik(int id) throws DataAccessException;
    
    void release() throws SQLException;
}
