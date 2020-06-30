package module6;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class CoronaCases{
    public void fetchData(CountryCorona cc, String country) throws IOException {
        country = country.toLowerCase();
        country.replaceAll(" ", "-");
        URL url = new URL("https://api.covid19api.com/live/country/" + country);
        HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HTTPS Response Code : " + responseCode);
        }
        else {
            String inline = "";
            Scanner sc = new Scanner(url.openStream());
            while(sc.hasNext()) {
                inline += sc.nextLine();
            }
            sc.close();
//            System.out.println(inline);
            int a = inline.lastIndexOf("Confirmed");
            int b = inline.indexOf(":",a);
            int c = inline.indexOf(",",a);
            cc.confirmed = inline.substring(b+1, c);
            a = inline.lastIndexOf("Deaths");
            b = inline.indexOf(":",a);
            c = inline.indexOf(",",a);
            cc.deaths = inline.substring(b+1, c);
            a = inline.lastIndexOf("Recovered");
            b = inline.indexOf(":",a);
            c = inline.indexOf(",",a);
            cc.recovered = inline.substring(b+1, c);
            a = inline.lastIndexOf("Active");
            b = inline.indexOf(":",a);
            c = inline.indexOf(",",a);
            cc.active = inline.substring(b+1, c);
            a = inline.lastIndexOf("Date");
            b = inline.indexOf("\"",a+5);
            c = inline.lastIndexOf("\"");
            cc.date = inline.substring(b+1, c);
            System.out.println("Confirmed : "  + cc.confirmed +
                    "\nDate : " + cc.date +
                    "\nActive : " + cc.active +
                    "\nRecovered : " + cc.recovered +
                    "\nDeaths : " + cc.deaths);
        }
    }
}
