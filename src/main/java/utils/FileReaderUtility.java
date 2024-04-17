package utils;

import client.Client;
import net.minidev.json.writer.JsonReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import product.Product;

import java.io.*;
import java.util.HashMap;

public class FileReaderUtility {

    public static void readClientsFromJson(String jsonFile, HashMap<Integer, Client> clients)
        throws FileNotFoundException {
        JSONParser parser = new JSONParser();
        try (InputStream inputStream = JsonReader.class.getResourceAsStream("/" + jsonFile)) {
            if (inputStream == null) {
                System.err.println("File not found!");
                return;
            }
            JSONArray clientsArray = (JSONArray) parser.parse(new InputStreamReader(inputStream));

            for (Object client : clientsArray) {
                JSONObject clientJson = (JSONObject) client;
                Client clientObject = InfoParser.parseClient(clientJson);
                clients.put(clientObject.getId(), clientObject);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readProductsFromJson(String jsonFile, HashMap<Integer, Product> products) throws IOException {
        JSONParser parser = new JSONParser();
        try (InputStream inputStream = JsonReader.class.getResourceAsStream("/" + jsonFile)) {
            if (inputStream == null) {
                System.err.println("File not found!");
                return;
            }
            JSONArray productsArray = (JSONArray) parser.parse(new InputStreamReader(inputStream));
            for (Object product : productsArray) {
                JSONObject productJson = (JSONObject) product;
                Product productObject = InfoParser.parseProduct(productJson);
                products.put(productObject.getId(), productObject);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}

