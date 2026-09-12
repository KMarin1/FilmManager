package hr.algebra.model;

import java.util.Objects;

public class Redatelj implements Comparable<Redatelj> {
    private int id;
    private String ime;
    private String prezime;
    
    
    public Redatelj( String ime, String prezime){
        this.ime = ime;
        this.prezime = prezime;
    }
    
    public Redatelj(int id, String ime, String prezime){
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
    }
    

    
    public int getId(){
        return id;
    }
    
    public String getIme(){
        return ime;
    }
    
    public String getPrezime(){
        return prezime;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setIme(String ime){
        this.ime = ime;
    }
    
    public void setPrezime(String prezime){
        this.prezime = prezime;
    }
    
    @Override
    public String toString(){
        return ime+" "+prezime;
    }
    
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Redatelj)) return false;
        Redatelj redatelj = (Redatelj) o;
        return Objects.equals(id, redatelj.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Redatelj o) {
        return this.prezime.compareToIgnoreCase(o.prezime);
    }
}
