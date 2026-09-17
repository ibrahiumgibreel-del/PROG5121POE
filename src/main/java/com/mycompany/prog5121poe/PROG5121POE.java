package com.mycompany.prog5121poe;

import java.util.Scanner;

public class PROG5121POE {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

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

        Login user = new Login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
        );

        System.out.println();
        System.out.println(user.registerUser());

        boolean registrationSuccessful =
                user.checkUserName()
                && user.checkPasswordComplexity()
                && user.checkCellPhoneNumber();

        if (registrationSuccessful) {
            System.out.println();
            System.out.println("LOGIN");

            System.out.print("Enter your username: ");
            String loginUsername = input.nextLine();

            System.out.print("Enter your password: ");
            String loginPassword = input.nextLine();

            boolean loginSuccessful =
                    user.loginUser(loginUsername, loginPassword);

            System.out.println(user.returnLoginStatus(loginSuccessful));
        }

        input.close();
    }
}