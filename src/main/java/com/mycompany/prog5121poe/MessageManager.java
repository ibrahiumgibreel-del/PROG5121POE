package com.mycompany.prog5121poe;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;

public class MessageManager {

    // Arrays required for Part 3
    private final ArrayList<String> sentMessages = new ArrayList<>();
    private final ArrayList<String> disregardedMessages = new ArrayList<>();
    private final ArrayList<String> storedMessages = new ArrayList<>();
    private final ArrayList<String> messageHashes = new ArrayList<>();
    private final ArrayList<String> messageIDs = new ArrayList<>();

    // Parallel arrays used to connect messages to their details
    private final ArrayList<String> senders = new ArrayList<>();
    private final ArrayList<String> recipients = new ArrayList<>();
    private final ArrayList<String> flags = new ArrayList<>();
    
    private final ArrayList<String> allMessages = new ArrayList<>();

    public void addMessage(String sender, String recipient, String message,
        String flag, String messageID, String messageHash) {

    senders.add(sender);
    recipients.add(recipient);
    allMessages.add(message);
    flags.add(flag);
    messageIDs.add(messageID);
    messageHashes.add(messageHash);

    if (flag.equalsIgnoreCase("Sent")) {
        sentMessages.add(message);
    } else if (flag.equalsIgnoreCase("Disregard")
            || flag.equalsIgnoreCase("Disregarded")) {
        disregardedMessages.add(message);
    } else if (flag.equalsIgnoreCase("Stored")) {
        storedMessages.add(message);
    }
}
public String displayStoredSendersAndRecipients() {
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < allMessages.size(); i++) {
        if (flags.get(i).equalsIgnoreCase("Stored")) {
            result.append("Sender: ")
                    .append(senders.get(i))
                    .append("\nRecipient: ")
                    .append(recipients.get(i))
                    .append("\n\n");
        }
    }

    if (result.length() == 0) {
        return "No stored messages found.";
    }

    return result.toString().trim();
}
public String getLongestStoredMessage() {
    if (storedMessages.isEmpty()) {
        return "No stored messages found.";
    }

    String longestMessage = storedMessages.get(0);

    for (String message : storedMessages) {
        if (message.length() > longestMessage.length()) {
            longestMessage = message;
        }
    }

    return longestMessage;
}
public String searchMessageByID(String messageID) {
    for (int i = 0; i < messageIDs.size(); i++) {
        if (messageIDs.get(i).equals(messageID)) {
            return allMessages.get(i);
        }
    }

    return "Message ID not found.";
}

public String searchMessageDetailsByID(String messageID) {
    for (int i = 0; i < messageIDs.size(); i++) {
        if (messageIDs.get(i).equals(messageID)) {
            return "Recipient: " + recipients.get(i)
                    + "\nMessage: " + allMessages.get(i);
        }
    }

    return "Message ID not found.";
}
public String searchMessagesByRecipient(String recipient) {
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < recipients.size(); i++) {
        boolean sentOrStored =
                flags.get(i).equalsIgnoreCase("Sent")
                || flags.get(i).equalsIgnoreCase("Stored");

        if (recipients.get(i).equals(recipient) && sentOrStored) {
            result.append(allMessages.get(i)).append("\n");
        }
    }

    if (result.length() == 0) {
        return "No messages found for this recipient.";
    }

    return result.toString().trim();
}
public String deleteMessageByHash(String messageHash) {
    for (int i = 0; i < messageHashes.size(); i++) {
        if (messageHashes.get(i).equals(messageHash)) {
            String deletedMessage = allMessages.get(i);
            String deletedFlag = flags.get(i);

            if (deletedFlag.equalsIgnoreCase("Sent")) {
                sentMessages.remove(deletedMessage);
            } else if (deletedFlag.equalsIgnoreCase("Stored")) {
                storedMessages.remove(deletedMessage);
            } else {
                disregardedMessages.remove(deletedMessage);
            }

            senders.remove(i);
            recipients.remove(i);
            allMessages.remove(i);
            flags.remove(i);
            messageIDs.remove(i);
            messageHashes.remove(i);

            return "Message: \"" + deletedMessage
                    + "\" successfully deleted.";
        }
    }

    return "Message hash not found.";
}
public String displayMessageReport() {
    StringBuilder report = new StringBuilder();

    for (int i = 0; i < allMessages.size(); i++) {
        boolean sentOrStored =
                flags.get(i).equalsIgnoreCase("Sent")
                || flags.get(i).equalsIgnoreCase("Stored");

        if (sentOrStored) {
            report.append("Message Hash: ")
                    .append(messageHashes.get(i))
                    .append("\nRecipient: ")
                    .append(recipients.get(i))
                    .append("\nMessage: ")
                    .append(allMessages.get(i))
                    .append("\nFlag: ")
                    .append(flags.get(i))
                    .append("\n\n");
        }
    }

    if (report.length() == 0) {
        return "No messages found.";
    }

    return report.toString().trim();
}
public ArrayList<String> getSentMessages() {
    return new ArrayList<>(sentMessages);
}

public ArrayList<String> getDisregardedMessages() {
    return new ArrayList<>(disregardedMessages);
}

public ArrayList<String> getStoredMessages() {
    return new ArrayList<>(storedMessages);
}

public ArrayList<String> getMessageHashes() {
    return new ArrayList<>(messageHashes);
}

public ArrayList<String> getMessageIDs() {
    return new ArrayList<>(messageIDs);
}
// Gson JSON parsing reference:
// https://github.com/google/gson/blob/main/UserGuide.md
public String loadStoredMessagesFromJson() {
    try (FileReader reader = new FileReader("messages.json")) {
        JsonElement jsonData = JsonParser.parseReader(reader);

        if (!jsonData.isJsonArray()) {
            return "The JSON file does not contain a message array.";
        }

        JsonArray messages = jsonData.getAsJsonArray();

        for (JsonElement element : messages) {
            JsonObject message = element.getAsJsonObject();

            String messageID =
                    message.get("messageID").getAsString();
            String messageHash =
                    message.get("messageHash").getAsString();
            String recipient =
                    message.get("recipient").getAsString();
            String messageText =
                    message.get("messageText").getAsString();

            addMessage(
                    "Developer",
                    recipient,
                    messageText,
                    "Stored",
                    messageID,
                    messageHash
            );
        }

        return messages.size() + " stored message(s) loaded.";

    } catch (IOException exception) {
        return "Stored messages could not be loaded: "
                + exception.getMessage();
    }
}
}


