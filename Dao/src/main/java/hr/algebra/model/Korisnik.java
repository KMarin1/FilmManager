package hr.algebra.model;

import java.util.Objects;

public class Korisnik implements Comparable<Korisnik> {
    private int id;
    private String korisnickoIme;
    private String lozinka;
    private Uloga uloga;
    
    public Korisnik(int id, String korisnickoIme, String lozinka, Uloga uloga){
        this.id = id;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.uloga = uloga; 
    }

    public Korisnik(String korisnickoIme, String lozinka) {
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        }
    
    public Korisnik(String korisnickoIme, String lozinka, Uloga uloga) {
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.uloga = uloga;
        }

    
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getKorisnickoIme(){
        return korisnickoIme;
    }
    public void setKorisnickoIme(String korisnickoIme){
        this.korisnickoIme = korisnickoIme;
    }
    public String getLozinka(){
        return lozinka;
    }
    public void setLozinka(String lozinka){
        this.lozinka = lozinka;
    }
    public Uloga getUloga(){
        return uloga;
    }
    public void setUloga(Uloga uloga){
        this.uloga = uloga;
    }
    
    @Override
    public String toString(){
        return korisnickoIme+" ("+uloga+") ";
    }

    @Override
    public int compareTo(Korisnik o) {
        return this.korisnickoIme.compareTo(o.korisnickoIme);
    }
    
    @Override
    public boolean equals(Object o){
        if(this == o)return true;
        if(!(o instanceof Korisnik)) return false;
        Korisnik korisnik = (Korisnik) o;
        return Objects.equals(id,korisnik.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
