package com.mycompany.prog5121poe;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Message {
    private String messageID;
private int messageNumber;
private String recipient;
private String messageText;
private String messageHash;
private String messageStatus;

private static int totalMessages = 0;
private static final Set<String> usedMessageIDs = new HashSet<>();

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
}


