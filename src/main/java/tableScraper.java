import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class tableScraper {
    public static void main(String[] args) {
        String url = "https://www.vlr.gg/player/9/tenz";

        try {
            Document document = Jsoup.connect(url).userAgent("Chrome").get();
            for(Element row : document.select("table.wf-table tr")) {
                if(row.select("td.mod-right:nth-of-type(2)").text().equals("")){
                    continue;
                } else {
                    System.out.println(row.select("td.mod-right:nth-of-type(2)").text());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
