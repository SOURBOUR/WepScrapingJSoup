import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

//age calculation
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class actualScraper {
    public static void main(String[] args) {
        String url = "https://www.vlr.gg/player/9/tenz";
        String secondUrl = "https://prosettings.net/players/tenz/#valorant";
        try {
            Document document = Jsoup.connect(url).userAgent("Chrome").get();
            for (Element playerInfo : document.select(".player-header")) {
                System.out.println(playerInfo.select(".wf-title").text());
                System.out.println(playerInfo.select(".player-real-name").text());
            }

            Document document1 = Jsoup.connect(secondUrl).userAgent("Chrome").get();
            Period age = null;
            for (Element playerInfo : document1.select("table.data tr")) {
                String birthDateString = "May 5, 2001";

                LocalDate birthDate = parseDateString(birthDateString);
                if (birthDate == null) {
                    System.out.println("Unable to parse the given date string.");
                    return;
                }
                LocalDate currentDate = LocalDate.now();
                age = Period.between(birthDate, currentDate);

            }
            System.out.println(age.getYears());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static LocalDate parseDateString(String dateString) {
        List<DateTimeFormatter> formatters = Arrays.asList(
                DateTimeFormatter.ofPattern("MMMM d, yyyy"),
                DateTimeFormatter.ofPattern("MMM d, yyyy"),
                DateTimeFormatter.ofPattern("M/d/yyyy"),
                DateTimeFormatter.ofPattern("d/M/yyyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateString, formatter);
            }
            catch (DateTimeParseException e) {
            }
        }

        return null;
    }
}
