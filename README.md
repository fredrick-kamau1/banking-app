# Banking app 
> creates a new user account with a 16 digit account number and a four digit PIN, uses Luhn Algorith to validate the account number, stores and retreives  the data from an SQLite Database. 
### Features
* Create account
* Log in
* Check balance
* Add funds to account
* Transfer funds to another account
* Close account
* Exit program
### Technologies
* Java 17
* SQLite 3.40.0
### Usage
To use this application, follow these steps:

1. Clone this repository to your local machine.
2. Open a terminal and navigate to the directory where the repository is located.
3. Build the application using the command `./gradlew build`
4. Run the application using the command 

    ```
    ./gradlew --console plain run --args="jdbc:sqlite:<path_to_database_file>"
    ``` 
    
    Replace <path_to_database_file> with the path to the SQLite database file you want to use for the application.
5. Follow the prompts to create an account, log in, and use the application.
### Contributors
[Fred Kamau](https://github.com/fredrick-kamau1)
### License
This project is licensed under the MIT License - see the LICENSE.md file for details.
