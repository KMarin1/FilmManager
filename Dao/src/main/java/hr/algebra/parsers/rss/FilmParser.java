/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.parsers.rss;

import hr.algebra.dal.Repository;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Film;
import hr.algebra.model.Redatelj;
import hr.algebra.model.Zanr;
import hr.algebra.utilities.DataAccessException;
import hr.algebra.utilities.FileUtils;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class FilmParser {

    private static final String RSS_URL = "https://slobodnadalmacija.hr/feed";
    private static final String ATTRIBUTE_URL = "url";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";
    private Repository repository;

    public static List<Film> parse() throws IOException, XMLStreamException, DataAccessException, SQLException {
        Repository repository = RepositoryFactory.getRepository();
        
        Redatelj defRedatelj = new Redatelj("RSS","Redatelj");
        repository.insertRedatelj(defRedatelj);

   

        Zanr defZanr = new Zanr("Redatelj");
        repository.insertZanr(defZanr);

        List<Film> films = new ArrayList<>();
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL);
        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);

            Optional<TagType> tagType = Optional.empty();
            Film film = null;
            StartElement startElement = null;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);
                        // put breakpoint here
                        if (tagType.isPresent() && tagType.get().equals(TagType.ITEM)) {
                            film = new Film();
                            films.add(film);
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        if (tagType.isPresent() && film != null) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            switch (tagType.get()) {
                                case NAZIV -> {
                                    if (!data.isEmpty()) {
                                        film.setNaziv(data);
                                    }
                                }
                                case GODINA -> {
                                    if (!data.isEmpty()) {
                                        LocalDateTime publishedDate = LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME);
                                        film.setGodina(publishedDate.getYear());
                                    }
                                }
                                case OPIS -> {
                                    if (!data.isEmpty()) {
                                        film.setOpis(data);
                                    }
                                }
                                case REDATELJ -> {
                                    if (!data.isEmpty()) {
                                        
                                        film.setRedatelj((Redatelj) repository.getRedateljByName("RSS Parser").get());

                                    } else {
                                        System.err.println("Data Empty");
                                    }
                                }
                                case ZANR -> {
                                    if (!data.isEmpty()) {

                                        film.setZanr((Zanr) repository.getZanrById(1).get());
                                    }
                                }
                                case ENCLOSURE -> {
                                    if (startElement != null && film.getPutanjaSlike() == null) {
                                        Attribute urlAttribute = startElement.getAttributeByName(new QName(ATTRIBUTE_URL));
                                        if (urlAttribute != null) {
                                            handlePicture(film, urlAttribute.getValue());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return films;

    }

    private static void handlePicture(Film film, String pictureUrl) throws IOException {

        String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
        if (ext.length() > 4) {
            ext = EXT;
        }
        String pictureName = UUID.randomUUID() + ext;
        String localPicturePath = DIR + File.separator + pictureName;
        FileUtils.copyFromUrl(pictureUrl, localPicturePath);
        // put breakpoint
        film.setSlikaPutanja(localPicturePath);

    }

    private enum TagType {

        ITEM("item"),
        NAZIV("title"),
        GODINA("pub_date"),
        OPIS("description"),
        REDATELJ("redatelj"),
        ZANR("zanr"),
        ENCLOSURE("enclosure");

        private final String name;

        private TagType(String name) {
            this.name = name;
        }

        private static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }

}
