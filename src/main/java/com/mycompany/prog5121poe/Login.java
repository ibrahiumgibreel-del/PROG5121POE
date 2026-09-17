package com.mycompany.prog5121poe;
 /**
*Stores a users registration details and provides methods for
*validating registration information and authenticating login details
*/
public class Login {


private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    public Login(String firstName, String lastName, String username,
            String password, String cellPhoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }
//Checks that the username contains an underscore and has 5 or fewer characters.
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }
//Checks the password length, capital letter, number and special character.
    public boolean checkPasswordComplexity() {
        boolean hasEightCharacters = password.length() >= 8;
        boolean hasCapitalLetter = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean hasSpecialCharacter =
                password.matches(".*[^a-zA-Z0-9].*");

        return hasEightCharacters
                && hasCapitalLetter
                && hasNumber
                && hasSpecialCharacter;
    }
// Checks that the cellphone number uses +27 folloowed by 9  digits.
    public boolean checkCellPhoneNumber() {
        /*
         * Regex syntax reference:
         * Oracle Java Pattern documentation:
         * https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/regex/Pattern.html
         *
         * This expression requires +27 followed by nine digits.
         */
        return cellPhoneNumber.matches("^\\+27\\d{9}$");
    }
    //returns the appropriate registration success or error message.
public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that "
                    + "your username contains an underscore and is no more "
                    + "than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that "
                    + "the password contains at least eight characters, "
                    + "a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not "
                    + "contain international code.";
        }

        return "Username successfully captured.\n"
                + "Password successfully captured.\n"
                + "Cell phone number successfully added.";
    }
// compares the entered login details with the registered login details
    public boolean loginUser(String enteredUsername,
            String enteredPassword) {

        return username.equals(enteredUsername)
                && password.equals(enteredPassword);
    }
// Returns the appropriate succesful or failed login message
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + ", " + lastName
                    + " it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }
}