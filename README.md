# JDBC: Extracting relational data

### Description
In this project I'm using JDBC to extract relational data

#### Theory:
* How to extract relational data using JDBC

#### Practical tasks:
**Task 1**

**Get the customer’s address and orders with the products**

Description:  Extract the customer’s address and all the customer’s orders with the ordered products.

To be able to do this, create an Address, Orders, and Product POJOs and DAOs. The Customer class should hold the Address and Orders Objects, Orders class should hold the Products objects.

Extract the data using Apache DBUtils Database Helper. Create a DataBase Manager Helper class if needed to extract all common database actions.
 
### Instruction
#### Prerequisites
1. SQL: Local database environment setup.
2. customers table with example data
##### Useful links:
1. https://commons.apache.org/proper/commons-dbutils/examples.html