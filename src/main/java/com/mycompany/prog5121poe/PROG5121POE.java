package com.mycompany.prog5121poe;

import java.util.Scanner;

public class PROG5121POE {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        Login user;
        boolean registrationSuccessful;

        // Repeats registration until all information is valid.
        do {
            System.out.println("REGISTRATION");

            System.out.print("Enter your first name: ");
            String firstName = input.nextLine();

            System.out.print("Enter your last name: ");
            String lastName = input.nextLine();

            System.out.print("Enter a username: ");
            String username = input.nextLine();

            System.out.print("Enter a password: ");
            String password = input.nextLine();

            System.out.print("Enter a South African cell phone number "
                    + "using the +27 international code: ");
            String cellPhoneNumber = input.nextLine();

            user = new Login(
                    firstName,
                    lastName,
                    username,
                    password,
                    cellPhoneNumber
            );

            System.out.println();
            System.out.println(user.registerUser());

            registrationSuccessful =
                    user.checkUserName()
                    && user.checkPasswordComplexity()
                    && user.checkCellPhoneNumber();

            if (!registrationSuccessful) {
                System.out.println("Please enter your registration details again.");
                System.out.println();
            }

        } while (!registrationSuccessful);

        boolean loginSuccessful;

        // Repeats login until the correct username and password are entered.
        do {
            System.out.println();
            System.out.println("LOGIN");

            System.out.print("Enter your username: ");
            String loginUsername = input.nextLine();

            System.out.print("Enter your password: ");
            String loginPassword = input.nextLine();

            loginSuccessful =
                    user.loginUser(loginUsername, loginPassword);

            System.out.println(user.returnLoginStatus(loginSuccessful));

        } while (!loginSuccessful);

        input.close();
    }
}