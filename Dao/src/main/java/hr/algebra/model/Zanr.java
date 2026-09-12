package hr.algebra.model;

import java.util.Objects;

public class Zanr implements Comparable<Zanr>{
    private int id;
    private String naziv;
    
    public Zanr(int id, String naziv){
        this.id = id;
        this.naziv = naziv;
    }

    public Zanr() {
    }
    
    public Zanr(String naziv) {
        this.naziv = naziv;
    }
    
    public int getId(){
        return id;
    }
    public String getNaziv(){
        return naziv;
    }
    
    public void setId(int id){
        this.id = id;
    }
    public void setNaziv(String naziv){
        this.naziv = naziv;
    }
    
    @Override
    public String toString(){
        return this.naziv;
    }
    
    @Override
    public boolean equals(Object o){
        if(this == o)return true;
        if(!(o instanceof Zanr)) return false;
        Zanr zanr = (Zanr) o;
        return Objects.equals(id,zanr.id);
        
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Zanr o) {
        return this.naziv.compareToIgnoreCase(o.naziv);
    }
    
}
