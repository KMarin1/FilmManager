/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.view;

import hr.algebra.model.Film;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

/**
 *
 * @author Marin
 */
public class FilmTransferable {
    public static final DataFlavor FILM_FLAVOR = new DataFlavor(Film.class, "Film");
    private final Film film;

    public FilmTransferable(Film film) {
        this.film = film;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{FILM_FLAVOR};
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return FILM_FLAVOR.equals(flavor);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (FILM_FLAVOR.equals(flavor)) {
            return film;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}
