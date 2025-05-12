# GameSpot Store Manager

A desktop application designed for use by store clerks of a fictional company called GameSpot. Users are able to keep track of stock, sales records, as well as selling games to customers and updating information about game listings.

## Features:

  - Keeping track of stock (games) both new and used.
  - Creating listings for new games.
  - Performing game searches based on name, publisher, genre and age rating to help customers select a game to purchase.
  - Modifying a game’s information; including adjusting the new-copy stock or adding used-copy listings.
  - Keeping records of modifications made to a game’s information.
  - Selling games to customers and updating stock appropriately.
  - Keeping records of purchases/sales.

## Tech Stack: 

  - **Frontend:** JavaFX
  - **Controller:** Java
  - **Database:** MySQL (via JDBC)

## System Overview:

In order for the front end to communicate with the database: 
  - The frontend invokes method calls within the control layer.
  - The control layer gathers information from relevant user-entered fields and formats the information into a query string.
  - The query string is run on the database via Java Database Connector.
  - If applicable, a result set is returned to the controller, where it is formatted and displayed on the front end.

## Setup Instructions:

### Prerequisites:

  - Java JDK 24+
  - Maven
  - MySQL Workbench

### Build and Run:
  
  1.	Open MySQL Workbench and open a local connection with username "root" and password "cheeseland" (or the username and password you set if you’re using a custom version).
  2.	Run the "GameSpot Creation Script.sql" in this MySQL environment.
  3.	Perform a Maven clean build of the project.
  4.	Locate “GameSpotStoreManager-1.0-SNAPSHOT-jar-with-dependencies.jar” in the “target/” and place it in “GameSpotStoreManagerApp/”.
  5.	Rename “GameSpotStoreManager-1.0-SNAPSHOT-jar-with-dependencies.jar” to “GSSM.jar”.
  6.	Double-click “RunProgram.bat” to run the app.

## Screenshots:

Getting all games with the “Action” genre and an E10+ rating.
![image](https://github.com/user-attachments/assets/a4760315-8ebe-49d5-89ec-43d4b504d11a)

Changing the name of a game and removing a genre from it:
![image](https://github.com/user-attachments/assets/43dab53a-b62d-4fd7-a8c2-6d5ee39464d1)
 
Reviewing the changes:
![image](https://github.com/user-attachments/assets/c48fbea5-620f-4d33-90aa-6049f307e4f5)

Adding new and used copies to the cart
![image](https://github.com/user-attachments/assets/e63bb5dc-f5cc-4982-a5c9-4af4bf071a46)

The cart displaying the games we added before purchase:
![image](https://github.com/user-attachments/assets/9479eb69-7ddc-49bd-899d-b4fc09ae0d86)

Reviewing the purchase:
 ![image](https://github.com/user-attachments/assets/6a16df8f-4cd0-4b6c-8073-4caaa7afee09)

Preparing to add a new game to the database:
![image](https://github.com/user-attachments/assets/1d1ccb4a-260b-42a9-8d67-9dd6e230f7e3)

Performing the same search as before and having the new game appear:
 ![image](https://github.com/user-attachments/assets/a0f9fed2-7b31-41c1-a513-1e389201508c)

##License

Copyright 2025 Ernest Whitehead

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
