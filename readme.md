# MarketplaceServerAPI Documentation

## Overview

A RESTful web API to simulate an online marketplace. Created in Java using Spring and MongoDB. Includes shopping cart caching using JWT tokens to save the user's order (for a set amount of time).

## Build

Run the following command in the root directory

    $ mvn clean package
    $ java -jar target/MarketplaceServer.jar

## Testing

Unit tests can be run with the command

    mvn test -DskipTests=false

## API Calls

All API calls are returned as JSON objects.

| Call                              | HTTPRequest |  Description               |
| ----------------                  | ----------- | -------------------------- |
| /                                 | GET         | retrieves list of products on the market |
| /market                           | GET         | retrieves list of products on the market |
| /market/available                 | GET         | retrieves all products with available inventory |
| /market/{id}                      | GET         | retrieves the product by id |
| /u/{userId}/add/{id}              | GET         | adds an order item for product with id to the cart |
| /u/{userId}/add/{id}/{num}        | GET         | adds a number of order items for product with id to the cart |
| /u/{userId}/remove/{id}           | DELETE      | removes an order item for product with id to the cart |
| /u/{userId}/remove/{id}/{num}     | DELETE      | removes a number of order items for product from the cart |
| /u/{userId}/view                  | GET         | returns the current cart |
| /u/{userId}/clear                 | DELETE      | clears the order items from the cart |
| /u/{userId}/checkout              | GET         | completes the order |
| /admin/market/add                 | POST        | requires an array of products to add to the market |
| /admin/market/update/{id}         | POST        | requires an object of product and updates the existing one on the market |
| /admin/market/delete/{id}         | DELETE      | deletes the product with id from the market |
