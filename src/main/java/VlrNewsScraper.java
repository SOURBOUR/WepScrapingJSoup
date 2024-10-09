import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VlrNewsScraper {

    public static void main(String[] args) {
        try {
            Map<String, Object> data = vlrNews();
            saveDataAsJson(data, "vlr_news.json");  // Save data as JSON file
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Scrape the news data
    public static Map<String, Object> vlrNews() throws IOException {
        // URL to scrape
        String url = "https://www.vlr.gg/news";

        // Create a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Get the response code
        int status = connection.getResponseCode();

        // Parse the HTML with Jsoup
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();

        // List to store results
        List<Map<String, String>> result = new ArrayList<>();

        // Select news elements
        Elements items = doc.select("a.wf-module-item");

        // Loop through each item
        for (Element item : items) {
            String dateAuthor = item.selectFirst("div.ge-text-light").text();
            String[] dateAuthorSplit = dateAuthor.split("by");

            String date = dateAuthorSplit[0].split("\u2022")[1].trim();
            String author = dateAuthorSplit[1].trim();

            String desc = item.select("div").get(1).text().trim();
            String title = item.select("div").first().text().split("\n")[0].replace("\t", "").trim();
            String newsUrl = item.attr("href");

            // Create a map for each news entry
            Map<String, String> news = new HashMap<>();
            news.put("title", title);
            news.put("description", desc);
            news.put("date", date);
            news.put("author", author);
            news.put("url_path", "https://vlr.gg" + newsUrl);

            result.add(news);
        }

        // Final result with status and data
        Map<String, Object> data = new HashMap<>();
        data.put("status", status);
        data.put("segments", result);

        // Throw an exception if status is not 200
        if (status != 200) {
            throw new IOException("API response: " + status);
        }
        return data;
    }

    // Save the scraped data as a JSON file
    public static void saveDataAsJson(Map<String, Object> data, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(data, writer);  // Convert the data to JSON and write it to the file
            System.out.println("Data successfully saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
