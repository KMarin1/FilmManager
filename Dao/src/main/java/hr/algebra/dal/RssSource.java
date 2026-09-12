package hr.algebra.dal;

public enum RssSource {
    NASA("https://www.nasa.gov/rss-feeds/"),
    PJ("https://photojournal.jpl.nasa.gov/rss/index.html"),
    Cedefop("https://www.cedefop.europa.eu/en/news-and-events/rss-feeds"),
    Sky("https://news.sky.com/info/rss"),
    ScienceDaily("https://www.sciencedaily.com/newsfeeds.htm");

    private final String url;

    RssSource(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
