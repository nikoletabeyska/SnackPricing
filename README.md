### Description
Java application that calculates and prints order details for clients of the company Everyday Snacks!
The application uses console input or file input to process orders in the format: **5,1=10000,4=20000**.

### Example usage

<!-- First Image -->
 - **using console input**
   
<img width="703" alt="image" src="https://github.com/nikoletabeyska/SnackPricing/assets/76749430/2ddaf740-87b2-4d85-a58b-a7b68c093521" style="margin-right: 10px;">

<!-- Second Image -->
 - **using file input**
   
<img width="703" alt="image" src="https://github.com/nikoletabeyska/SnackPricing/assets/76749430/2131615a-78be-48b7-8ba9-8b934a287e05" style="margin-right: 10px;">


## Implementation

  _Most of the discounts and numeric variables are stored using **BigDecimal class** for acheiving better precision when doing calculations although it is slower and uses more memory than double._
  _ The files ***clients.json**, ***products.json*** and ***orders.txt*** are stored in ***main/src/resources/***. The first two are used to populate the Storage with clients and products! _

 ### Client class
  - Represents client of the company Everyday Snacks
  - Stores client details (id, name, basicClientDiscount, additionalVolumeDiscounts)
  -	It has 2 important methods which calculate the discounts with precision up to 2 decimal places:
 	   -  ***calculateBasicClientDiscount***
     -  ***calculateAdditionalVolumeDiscoun***
-	Storing additionalVolumeDiscount in TreeMap keeps cost bounds as sorted keys which gives the oportunity to check for having this type of discount using binary search (lowerKey) method

 ### Product class
  -	Represents product of the company Everyday Snacks
  - Stores product details(id, name, unitCost, markupStrategy, promotionStrategy, standardUnitPrice, promotionalUnitPrice)
  -	Stores markupStrategy as a reference of the Interface ***Markup*** – providing abstraction – Product just needs to have its standard unit price calculated without caring what will the markup implementation will be based on its type. Also more markup types can be added implementing Markup interface
  - Same goes for Promotion interface reference
  - StandardUnitPrice and PromotionalUnitPrice are stored as attribites so that they don’t have to be precalculated each time (only when being initialized and when there is an update in the other aattributes)

  ### Markup interface
  - Has abstract method ***calculateStandardPriceUnit***
  - Implemented by ***EURMarkup*** and ***PercentageMarkup***
  - Also there is factory method for creating different types of markup based on input - ***MarkupFactory***

  ### Promotion interface
   - Has abstract method ***calculatePromotionalUnitPrice***
   - Implemented by ***DiscountPromotion*** and ***BuyAmountGetAmountForFreePromotion*** and can be extended with more promotions in the future
   - Has factory method PromotionFactory wjich creates promotions based on input

  ### Storage class
   - Stores Everyday Snacks’s clients and products in hash maps by their ids as keys 
   - Storage class is used when we want to calculate ***order details*** based on specific client and products – clients and products are retrieved in constant time by their id
   - Clients and Products can be stored in Json files and we can populate the Storage class when we run the application using ***populateProductsFromFile()*** and ***populateClientsFromFile()***. This way we can add more clients and products to our application.
   - **In resources/ are stored clients.json and products.json which are populating the storage!** 
   - Clients and products can be added also manually and removed by their id ( ***addClient(..)*** , ***removeClientById(..)*** )

  ### Utility classes
   - ***FileReaderUtility*** - used to handle reading from JSON file. More file types can be added in the future.
   - ***InfoParser*** - used by FileReaderUtility to parse the data into Client and Product objects after modifying the needed attributes from String to the compatible types in Client and Product constructors 

  ### Order class
   - Representing order - stores Client and HashMap of Product and quantity
   - Calculates and returns the order information using Client and Product classes methods

  ### RequestHandler class
   - populates storage class manually or from file
   - Handles user input from the console or from file
   - File orders.txt is stored in resources/
   - Validates and process input - printing the order information using Order class print method

## Test cases
- Test cases are made using JUnit5 and Mockito.




