# Coding Challenge - Super Simple Stocks

This is a Super Simple Stocks application for a coding challange.
This project can be built and tested using the following:
1. JDK: 8 or higher
2. Maven: 3.9 or higher

**High Level Requirements:**
1.	Provide working source code that will:

    a.	For a given stock:
    
        i. Given any price as input, calculate the dividend yield
        ii. Given any price as input, calculate the P/E Ratio
        iii. Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price
        iv. Calculate Volume Weighted Stock Price based on trades in past 15 minutes
        
    b.	Calculate the GBCE All Share Index using the geometric mean of prices for all stocks

See [Coding Challenge - Super Simple Stocks][Coding Challenge - Super Simple Stocks.pdf] for detailed requirements.

**Code Structure**

1. **main/java/com/stl**

Contains all the main files

    A. __dao:__ Data Access Objects

    B. __domain:__ Classes to hold domain specifc business logic

    C. __lang:__ Application specifc lang classes e.g. Exception classes

    D. __model:__ Entities and other Data storage classes

    E. __store:__ Local store to hold Stocks

2. **test**

Contains all the test cases. Junit 4 is used to write test cases. Follows same package structure as **main** classes.

**How to build and test:**

Unless you are using an IDE, simply browse to the root folder and execute

   _mvn test_

&copy; Saggu Technologies Limited (stl)
