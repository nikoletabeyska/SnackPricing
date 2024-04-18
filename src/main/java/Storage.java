import client.Client;
import exceptions.ClientAlreadyExistsException;
import exceptions.ClientDoesNotExistException;
import exceptions.ProductAlreadyExistsException;
import exceptions.ProductDoesNotExistException;
import product.Product;
import utils.FileReaderUtility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class Storage {

    private static HashMap<Integer, Client> clients;
    private static HashMap<Integer, Product> products;

    public Storage() {
        this.clients = new HashMap<>();
        this.products = new HashMap<>();
    }

    public Storage(HashMap<Integer, Client> clients, HashMap<Integer, Product> products) {
        this.clients = clients;
        this.products = products;
    }

    public Client getClientById(int clientId) {
        if (!clients.containsKey(clientId)) {
            throw new ClientDoesNotExistException("Client with such id does not exist!\n");
        }
        return clients.get(clientId);
    }

    public Product getProductById(int productId)  {
        if (!products.containsKey(productId)) {
            throw new ProductDoesNotExistException("Product with such id does not exist!\n");
        }
        return products.get(productId);
    }

    public void populateClientsFromFile(String filename) throws FileNotFoundException {
        FileReaderUtility.readClientsFromJson(filename, clients);
    }

    public void populateProductsFromFile(String filename) throws IOException {
        FileReaderUtility.readProductsFromJson(filename, products);
    }

    public void addNewClient(Client newClient) {
        if (clients.containsKey(newClient.getId())) {
            throw new ClientAlreadyExistsException("There is already a client with this ID!");
        }
        clients.put(newClient.getId(), newClient);
    }

    public void addNewProduct(Product newProduct) {
        if (products.containsKey(newProduct.getId())) {
            throw new ProductAlreadyExistsException("There is already a product with this ID!");
        }
        products.put(newProduct.getId(), newProduct);
    }

    public void removeClientById(int clientId) {
        if (!clients.containsKey(clientId)) {
            throw new ClientDoesNotExistException("Client with such id does not exist!\n");
        }
        clients.remove(clientId);
    }

    public void removeProductById(int productId) {
        if (!products.containsKey(productId)) {
            throw new ProductDoesNotExistException("Product with such id does not exist!\n");
        }
        products.remove(productId);
    }

    public static HashMap<Integer, Client> getClients() {
        return clients;
    }

    public static void setClients(HashMap<Integer, Client> clients) {
        Storage.clients = clients;
    }

    public static HashMap<Integer, Product> getProducts() {
        return products;
    }

    public static void setProducts(HashMap<Integer, Product> products) {
        Storage.products = products;
    }

}

