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
        
        runQuickChat(input);
        
        input.close();
    }
    private static void runQuickChat(Scanner input) {
    System.out.println("Welcome to QuickChat.");

    MessageManager messageManager = new MessageManager();
System.out.println(messageManager.loadStoredMessagesFromJson());

    int maximumMessages;

    do {
        System.out.print("How many messages would you like to enter? ");
        maximumMessages = readInteger(input);

        if (maximumMessages <= 0) {
            System.out.println("Please enter a number greater than zero.");
        }
    } while (maximumMessages <= 0);

    int processedMessages = 0;
    int menuChoice;

    do {
        System.out.println("\nQuickChat Menu");
        System.out.println("1. Send Messages");
        System.out.println("2. Show recently sent messages");
        System.out.println("3. Stored Messages");
        System.out.println("4. Quit");
        System.out.print("Enter your selection: ");

        menuChoice = readInteger(input);

        switch (menuChoice) {
            case 1:
                if (processedMessages >= maximumMessages) {
                    System.out.println(
                            "You have reached your message limit."
                    );
                    break;
                }

                String recipient;

                do {
                    System.out.print(
                            "Enter the recipient cell phone number: "
                    );
                    recipient = input.nextLine().trim();

                    if (!recipient.matches("^\\+27\\d{9}$")) {
                        System.out.println(
                                "Cell phone number is incorrectly formatted "
                                + "or does not contain an international code. "
                                + "Please correct the number and try again."
                        );
                    }
                } while (!recipient.matches("^\\+27\\d{9}$"));

                String messageText;

                do {
                    System.out.print("Enter your message: ");
                    messageText = input.nextLine();

                    if (messageText.length() > 250) {
                        int extraCharacters =
                                messageText.length() - 250;

                        System.out.println(
                                "Message exceeds 250 characters by "
                                + extraCharacters
                                + "; please reduce the size."
                        );
                    }
                } while (messageText.length() > 250);

                Message message = new Message(
                        recipient,
                        messageText,
                        processedMessages
                );

                System.out.println(
                        "Message ID generated: "
                        + message.getMessageID()
                );

                System.out.println(message.checkMessageLength());
                message.createMessageHash();

                System.out.println("1. Send Message");
                System.out.println("2. Disregard Message");
                System.out.println("3. Store Message to send later");
                System.out.print("Enter your selection: ");

                int action = readInteger(input);
                String result = message.SentMessage(action);
                System.out.println(result);

                if (action == 1) {
                    System.out.println(
                            "Message ID: " + message.getMessageID()
                    );
                    System.out.println(
                            "Message Hash: " + message.getMessageHash()
                    );
                    System.out.println(
                            "Recipient: " + message.getRecipient()
                    );
                    System.out.println(
                            "Message: " + message.getMessageText()
                    );
                }
                if (action >= 1 && action <= 3) {
                String flag;

                if (action == 1) {
                    flag = "Sent";
                } else if (action == 2) {
                    flag = "Disregard";
                } else {
                    flag = "Stored";
                }

                messageManager.addMessage(
                        "Developer",
                        message.getRecipient(),
                        message.getMessageText(),
                        flag,
                        message.getMessageID(),
                        message.getMessageHash()
                );

                processedMessages++;
            }

                
                break;

            case 2:
                System.out.println("Coming Soon.");
                break;
            case 3:
                 runStoredMessagesMenu(input, messageManager);
                 break;
                 
            case 4:
                System.out.println(
                        "Total messages sent: "
                        + Message.returnTotalMessages()
                );
                break;

            default:
                System.out.println(
                        "Invalid selection. Please choose 1, 2 , 3 or 4."
                );
                break;
        }
    } while (menuChoice != 4);
}
private static void runStoredMessagesMenu(
Scanner input, MessageManager messageManager) {

    int storedChoice;

    do {
        System.out.println("\nStored Messages Menu");
        System.out.println("1. Display stored senders and recipients");
        System.out.println("2. Display the longest stored message");
        System.out.println("3. Search using a message ID");
        System.out.println("4. Search using a recipient");
        System.out.println("5. Delete using a message hash");
        System.out.println("6. Display message report");
        System.out.println("7. Return to QuickChat menu");
        System.out.print("Enter your selection: ");

        storedChoice = readInteger(input);

        switch (storedChoice) {
            case 1:
                System.out.println(
                        messageManager.displayStoredSendersAndRecipients()
                );
                break;

            case 2:
                System.out.println(
                        messageManager.getLongestStoredMessage()
                );
                break;

            case 3:
                System.out.print("Enter the message ID: ");
                String messageID = input.nextLine().trim();

                System.out.println(
                        messageManager.searchMessageDetailsByID(messageID)
                );
                break;

            case 4:
                System.out.print("Enter the recipient number: ");
                String recipient = input.nextLine().trim();

                System.out.println(
                        messageManager.searchMessagesByRecipient(recipient)
                );
                break;

            case 5:
                System.out.print("Enter the message hash: ");
                String messageHash = input.nextLine().trim();

                System.out.println(
                        messageManager.deleteMessageByHash(messageHash)
                );
                break;

            case 6:
                System.out.println(
                        messageManager.displayMessageReport()
                );
                break;

            case 7:
                System.out.println("Returning to QuickChat menu.");
                break;

            default:
                System.out.println(
                        "Invalid selection. Please choose 1 to 7."
                );
                break;
        }

    } while (storedChoice != 7);
}

private static int readInteger(Scanner input) {
    while (true) {
        String value = input.nextLine().trim();

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            System.out.print(
                    "Please enter a valid number: "
            );
        }
    }
}
}