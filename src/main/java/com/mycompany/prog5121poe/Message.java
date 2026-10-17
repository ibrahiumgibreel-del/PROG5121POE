package com.mycompany.prog5121poe;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Message {
    private String messageID;
private int messageNumber;
private String recipient;
private String messageText;
private String messageHash;
private String messageStatus;

private static int totalMessages = 0;
private static final Set<String> usedMessageIDs = new HashSet<>();
private static final List<Message> sentMessages = new ArrayList<>();
private static final List<Message> storedMessages = new ArrayList<>();


public Message(String recipient, String messageText, int messageNumber) {
    this.messageID = generateMessageID();
    this.recipient = recipient;
    this.messageText = messageText;
    this.messageNumber = messageNumber;
    this.messageHash = "";
    this.messageStatus = "";
}

private String generateMessageID() {
    String generatedID;

    do {
        long number = ThreadLocalRandom.current()
                .nextLong(1_000_000_000L, 10_000_000_000L);

        generatedID = String.valueOf(number);
    } while (!usedMessageIDs.add(generatedID));

    return generatedID;
}
    public boolean checkMessageID() {
    return messageID != null
            && messageID.matches("\\d{10}");
}

public String checkRecipientCell() {
    if (recipient != null && recipient.matches("^\\+27\\d{9}$")) {
        return "Cell phone number successfully captured.";
    }

    return "Cell phone number is incorrectly formatted or does not contain "
            + "an international code. Please correct the number and try again.";
}

public String checkMessageLength() {
    if (messageText.length() <= 250) {
        return "Message ready to send.";
    }

    int extraCharacters = messageText.length() - 250;

    return "Message exceeds 250 characters by "
            + extraCharacters
            + "; please reduce the size.";
}
public String createMessageHash() {
    String cleanedMessage = messageText.trim();
    String[] words = cleanedMessage.split("\\s+");

    String firstWord = words[0];
    String lastWord = words[words.length - 1];

    messageHash = messageID.substring(0, 2)
            + ":"
            + messageNumber
            + ":"
            + firstWord.toUpperCase()
            + lastWord.toUpperCase();

    return messageHash;
}
public String SentMessage(int choice) {
    switch (choice) {
        case 1:
            messageStatus = "Sent";
            sentMessages.add(this);
            totalMessages++;
            return "Message successfully sent.";

        case 2:
            messageStatus = "Disregarded";
            return "Press 0 to delete the message.";

        case 3:
            messageStatus = "Stored";
            storedMessages.add(this);
            storeMessage();
            return "Message successfully stored.";

        default:
            return "Invalid selection.";
    }
}

public void storeMessage() {
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    try (FileWriter writer = new FileWriter("messages.json")) {
        gson.toJson(storedMessages, writer);
    } catch (IOException exception) {
        System.out.println(
                "The message could not be stored: "
                + exception.getMessage()
        );
    }
}

public String printMessages() {
    StringBuilder output = new StringBuilder();

    for (Message message : sentMessages) {
        output.append("Message ID: ")
                .append(message.messageID)
                .append("\nMessage Hash: ")
                .append(message.messageHash)
                .append("\nRecipient: ")
                .append(message.recipient)
                .append("\nMessage: ")
                .append(message.messageText)
                .append("\n\n");
    }

    return output.toString();
}

public static int returnTotalMessages() {
    return totalMessages;
}
public String getMessageID() {
    return messageID;
}

public int getMessageNumber() {
    return messageNumber;
}

public String getRecipient() {
    return recipient;
}

public String getMessageText() {
    return messageText;
}

public String getMessageHash() {
    return messageHash;
}

public String getMessageStatus() {
    return messageStatus;
}
}

