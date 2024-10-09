import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class playerScraper {
    public static void main(String[] args) {
        String url = "https://www.vlr.gg/player/9/tenz/";

        try {
            Document document = Jsoup.connect(url).get();
            Elements playerInfo = document.select(".player-header");

            for(Element info: playerInfo) {
                String Name = info.select("h1 > .wf-title").text();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
