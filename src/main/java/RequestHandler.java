import client.Client;
import exceptions.ClientDoesNotExistException;
import exceptions.ProductDoesNotExistException;
import product.Product;
import utils.InfoParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHandler {

    private static Storage storage;

    public static void main(String[] args)  {

        loadStorageUsingFile();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an option:\n" +
            "1 - Read orders from the console\n" + "2 - Read orders from a file\n");

        int choice = scanner.nextInt();
        if (choice == 1) {
            try {
                handleConsoleInput();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (choice == 2) {
            System.out.println("Please provide filename: ");
            String filename = scanner.next();
            try {
                handleFileInput(filename);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void loadStorageUsingFile() {
        storage = new Storage();
        try {
            storage.populateClientsFromFile("clients.json");
            storage.populateProductsFromFile("products.json");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Storage initialization failed!" + e.getMessage() + "\n");
        }
    }

    public static void loadStorageManually() {
        storage = new Storage();
        TreeMap<BigDecimal, BigDecimal> additionalVolumeDiscount = new TreeMap<>();
        additionalVolumeDiscount.put(InfoParser.parseThreshold("10000"), InfoParser.parsePercentage("0"));
        storage.addNewClient(new Client(1, "ABC Distribution",
            InfoParser.parsePercentage("5"), additionalVolumeDiscount));
        //...
    }

    public static void handleConsoleInput() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String userInput = scanner.nextLine();
            processInput(userInput);
        }
    }

    public static void handleFileInput(String filename) {
        try {
            InputStream inputStream = RequestHandler.class.getClassLoader().getResourceAsStream(filename);
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: " + filename);
            }
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                processInput(line);
            }
            scanner.close();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filename, e);
        }
    }

    public static void processInput(String input) {
        String[] args = input.split(",");
        try {
            validateInput(args);
            printOrderInformation(args);
        } catch (IllegalArgumentException | ClientDoesNotExistException | ProductDoesNotExistException e) {
            throw new IllegalArgumentException("Invalid input: " + e.getMessage());
        }
    }

    public static void validateInput(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Not enough arguments!\n");
        }
        checkIfStringIsNumber(args[0]);
        validateProductOrderFormat(args);
    }

    public static void validateProductOrderFormat(String[] args) {
        String orderInputPattern = "^\\d+=\\d+$";
        Pattern p = Pattern.compile(orderInputPattern);
        for (int i = 1; i < args.length; i++) {
            Matcher m = p.matcher(args[i]);
            if (!m.matches()) {
                throw new IllegalArgumentException("Order information should be in the format 'number=number'!\n");
            }
        }
    }

    public static void checkIfStringIsNumber(String toCheck) {
        try {
            Integer.parseInt(toCheck);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ClientID should be a number!\n");
        }
    }

    public static void printOrderInformation(String[] args) {
        Client client = getClient(args[0]);
        HashMap<Product, Integer> orderedProducts = getOrderedProducts(args);
        Order order = new Order(client, orderedProducts);
        System.out.println(order.printOrderInfo());
    }

    public static Client getClient(String clientIdString) {
        int clientId = Integer.parseInt(clientIdString);
        return storage.getClientById(clientId);
    }

    public static HashMap<Product, Integer> getOrderedProducts(String[] args) {
        HashMap<Product, Integer> orderedProducts = new LinkedHashMap<>();
        for (int i = 1; i < args.length; i++) {
            String[] productOrderInfo = args[i].split("=");
            int productId = Integer.parseInt(productOrderInfo[0]);
            int quantity = Integer.parseInt(productOrderInfo[1]);
            Product product = storage.getProductById(productId);

            orderedProducts.put(product, quantity);
        }
        return orderedProducts;
    }
}
