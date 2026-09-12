package hr.algebra.view.model;

import hr.algebra.model.Film;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

public class FilmTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {"ID", "Naziv", "Godina", "Opis", "Redatelj", "Zanr", "Glumci", "Putanja Slike"};

    private List<Film> films;

    public FilmTableModel(List<Film> films) {
        this.films = films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return films.size();
    }

    @Override
    public int getColumnCount() {

        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return films.get(rowIndex).getId();
            case 1:
                return films.get(rowIndex).getNaziv();
            case 2:
                return films.get(rowIndex).getGodina();
            case 3:
                return films.get(rowIndex).getOpis();
            case 4:
                return films.get(rowIndex).getRedatelj();
            case 5:
                return films.get(rowIndex).getZanr();
            case 6:
                return films.get(rowIndex).getGlumci().stream()
                    .map(g -> g.toString()).collect(Collectors.joining(", "));
                    
            case 7: 
                return films.get(rowIndex).getPutanjaSlike();
            default:
                throw new RuntimeException("No such column");
        }
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex);
    }
    
    public void moveFilm(Film film, int index) {
        int oldIndex = films.indexOf(film);
        if (oldIndex == -1 || index < 0 || index >= films.size()) return;

        films.remove(oldIndex);

        // adjust index if removing from above
        if (oldIndex < index) {
            index--;
        }

        films.add(index, film);
        fireTableDataChanged(); // or fireTableRowsUpdated(index, index);
    }




}
