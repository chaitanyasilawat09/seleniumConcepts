package upload_Download_Files;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Read_Data_From_WebPage {

    public static String getData(String urlString) throws Exception {

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line;
        StringBuffer response = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();

        return response.toString();
    }

    public static void main(String[] args) throws Exception {

        String data = getData("https://api.agify.io/?name=michael");

        System.out.println(data);
    }
}
