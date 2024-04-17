import client.Client;
import exceptions.ClientDoesNotExistException;
import exceptions.ProductDoesNotExistException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import product.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

public class StorageTest {
    private static Storage storage;

    @BeforeAll
    public static void setUp() {
        storage = new Storage();
    }

    @Test
    void getClientById_ClientExists() {
        Client client = new Client(1, "Test", any(), any());
        storage.addNewClient(client);
        assertEquals(client, storage.getClientById(1));
    }

    @Test
    void getClientById_ClientDoesNotExists() {
        assertThrows(ClientDoesNotExistException.class, () -> storage.getClientById(2));
    }

    @Test
    void getProductById_ProductExists() {
        Product product = new Product(1, "Test", any(),any(),any());
        storage.addNewProduct(product);

        assertEquals(product, storage.getProductById(1));
    }

    @Test
    void getProductById_ProductDoesNotExist() {
        assertThrows(ProductDoesNotExistException.class, () -> storage.getProductById(4));
    }

    @Test
    void removeProductById_Success() {
        Product product = new Product(6, "Test", any(),any(),any());
        storage.addNewProduct(product);
        storage.removeProductById(6);

        assertThrows(ProductDoesNotExistException.class, () -> storage.getProductById(6));
    }

    @Test
    void removeProductById_Fail() {
        assertThrows(ProductDoesNotExistException.class, () -> storage.removeProductById(10));
    }
}
