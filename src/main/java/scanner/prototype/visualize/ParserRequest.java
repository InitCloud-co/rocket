package scanner.prototype.visualize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import scanner.prototype.exception.ScanException;
import scanner.prototype.env.Env;

@Service
public class ParserRequest {

    private final String API = Env.PARSER_API.getValue();
    private final String parse = "/api/v1/";
    private final JSONParser jsonParser = new JSONParser();

    public Object getTerraformParsingData(String directory, String provider)
    throws MalformedURLException, IOException, ParseException
    {
        HttpURLConnection conn = null;
        URL url = new URL(API + parse + provider + "/" + directory);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); 
        conn.setUseCaches(false);
        conn.setDoOutput(true);

        BufferedReader in = new BufferedReader(
                                new InputStreamReader(conn.getInputStream())); 
        String inputLine; 
        StringBuffer response = new StringBuffer(); 
        while ((inputLine = in.readLine()) != null) { 
            response.append(inputLine); 
        } 
        in.close();

        return jsonParser.parse(response.toString());
    }       
}
