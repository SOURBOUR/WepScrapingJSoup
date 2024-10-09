import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class vlrScraper {
    public static void main(String[] args) {
        String url = "https://www.vlr.gg/search/?q=tenz";
        try {
            Document document = Jsoup.connect(url).get();
            Elements players = document.select(".wf-module-item");

            for(Element player : players) {
                String name = player.select(".search-item-title").text();
//                System.out.println(name);
                String realName = player.select(".search-item-desc").text();
                String Link = player.select(".search-item").attr("href");

                if(!realName.equals("") && name.equals("TenZ")){
                    System.out.println(name + " " +  "Real Name - " + realName);
                    System.out.println(Link);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

