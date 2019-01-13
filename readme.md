# MarketplaceServerAPI Documentation

## Overview

A server side web API for an online marketplace. Created in Java using Spring Boot.

Some key features that were implemented include:

* REST archietecutural structure
* concurrency compatibility by creating independent carts for each user
* synchronized blocks for purchasing items to avoid the race condition
* made the application loosely coupled, extendable and adaptable by creating multiple interface with implementation classes and utilizing the IoC container provided by Spring
* unit tests through JUnit

## Prerequisites

* Java 8
* Maven 3

## Thought Process / Structure

I chose Spring Boot to tackle this challenge as the framework offers a lot of functionality including IoCs and a automatic Tomcat Server on startup. At first, I created a more bare-bones application using just a Product, Market and some Controller classes, just ading on the basic functionality that a marketplace has. After I got that to work, I added in additional features including a shopping cart so that users could add and buy multiple products at a time. Later on, I added in Service interfaces and implementations to refine the programs structure and allow for flexbility and scalibility. I also made sure certain methods and properties (such as all methods updating or accessing the inventory count) to be locked to enforce data consistency even with multiple concurrent transactions. Finally, I added automated unit tests in maven to validate business logic and HTTPResponses.

Note: the product's title is used as the unique key.

    Product {
        int id
        String title
        double price
        int inventoryCount
    }

    Cart {
        int id
        int totalPrice
        HashMap<Product, Integer> items
    }

## Build

Please run the following commands in order:

    mvn clean package

Run the following command in the root folder

    java -jar target/MarketplaceServer.jar

and voila, your Tomcat Server should be up and running.

## Testing

Some unit tests are provided in the application. In order to run the tests, please build and run the jar file first.

    mvn test -DskipTests=false

## API Calls

All API calls are returned as JSON objects.

| Call             | HTTPRequest |  Description               |
| ---------------- | ----------- | -------------------------- |
| /market          | GET         | retrieves list of products |
| /market/available          | GET         | retrieves all available products (have inventoryCount > 0) |
| /market/{title}  | GET         | retrieves the product with the title "title" |
| /market/purchase/{title} | GET | confirms the purchase of a product with title "title" and returns the product purchased. returns null if purchase fails |
| /admin/market/add         | POST        | requires a JSON array of Products and adds products to the market |
| /admin/market/update/{title}  | POST        | requires a JSON array of Products and updates the current product in Market |
| /admin/market/delete/{title}  | DELETE      | deletes the product with title "title" from the market |
| /u/{userId}/add/{title} | GET   | adds the product with title "title" to the cart and returns the new cart |
| /u/{userId}/add/{title}/{num} | GET   | adds a "num" number of products with title "title" to the cart and returns the new cart |
| /u/{userId}/remove/{title} | GET   | removes the product with title "title" from the cart and returns the new cart |
| /u/{userId}/remove/{title}/{num} | GET   | removes a "num" number of products from the cart (if possible) and returns the new cart |
| /u/{userId}/view       | GET         | returns the current cart |
| /u/{userId}/clear | DELETE   | clears the items from the cart and returns the cleared cart |
| /u/{userId}/checkout | GET   | attempts to purchase all items in the cart |

## Next Steps

* building in database compatibility by changing service implementation
* adding more functionality to support more API calls
* creating and implementing User and Vendor classes
* added security through authentication tokens for adding, removing or updating produce on the market

## Reflection

Wow, this was hard but I learned a lot and ad tons of fun along the way. Thanks for the challenge Shopify! :D