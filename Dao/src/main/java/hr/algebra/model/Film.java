
package hr.algebra.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Film implements Comparable<Film> {
    
    private int id;
    private String naziv;
    private int godina;
    private String opis;
    private Redatelj redatelj;
    private Zanr zanr;
    private List<Glumac> glumci;
    private String slikaPutanja;

    public Film(int id, String naziv, int godina, String opis, Redatelj redatelj, Zanr zanr, List<Glumac> glumci, String slikaPutanja){
        this(naziv,godina,opis,redatelj,zanr,glumci,slikaPutanja);
        this.id=id;
    }
    
    public Film( String naziv, int godina, String opis, Redatelj redatelj, Zanr zanr, List<Glumac> glumci, String slikaPutanja){

        this.naziv = naziv;
        this.godina = godina;
        this.opis = opis;
        this.redatelj = redatelj;
        this.glumci = glumci;
        this.zanr = zanr;
        this.slikaPutanja = slikaPutanja;    
    }
    
    public Film(){}
    
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    
    
    public String getNaziv(){
        return naziv;
    }
    public void setNaziv(String naziv){
        this.naziv = naziv;
    }
    
    public int getGodina(){
        return godina;
    }
    public void setGodina(int godina){
        this.godina = godina;
    }
    
    
    public String getOpis(){
        return opis;
    }
    public void setOpis(String opis){
        this.opis = opis;
    }
    
    
    public Redatelj getRedatelj(){
        return redatelj;
    }
    public void setRedatelj(Redatelj redatelj){
        this.redatelj = redatelj;
    }
    
 
    public Zanr getZanr(){
        return zanr;
    }
    public void setZanr(Zanr zanr){
        this.zanr = zanr;
    }
    
    
    public String getPutanjaSlike(){
        return slikaPutanja;
    }
    public void setSlikaPutanja(String slikaPutanja){
        this.slikaPutanja = slikaPutanja;
    }
    
    
    public List<Glumac> getGlumci(){
        return glumci;
    }
    public void setGlumci(List<Glumac> glumci){
        this.glumci = glumci;
    }
    
    
    @Override
    public String toString(){
        return naziv;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Redatelj)) return false;
        Film film = (Film) o;
        return this.id==film.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Film o) {
        return this.naziv.compareToIgnoreCase(o.naziv);
    }

    
    
}
