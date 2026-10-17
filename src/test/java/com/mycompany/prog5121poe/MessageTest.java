package com.mycompany.prog5121poe;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void testMessageIDIsTenDigits() {
        Message message = new Message(
                "+27718693002",
                "Test message",
                0
        );

        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    @Test
    public void testRecipientCorrectlyFormatted() {
        Message message = new Message(
                "+27718693002",
                "Test message",
                0
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testRecipientIncorrectlyFormatted() {
        Message message = new Message(
                "08575975889",
                "Test message",
                0
        );

        assertEquals(
                "Cell phone number is incorrectly formatted or does not "
                + "contain an international code. Please correct the "
                + "number and try again.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testMessageLengthSuccessful() {
        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?",
                0
        );

        assertEquals(
                "Message ready to send.",
                message.checkMessageLength()
        );
    }

    @Test
    public void testMessageExceedsLimit() {
        String longMessage = "a".repeat(251);

        Message message = new Message(
                "+27718693002",
                longMessage,
                0
        );

        assertEquals(
                "Message exceeds 250 characters by 1; "
                + "please reduce the size.",
                message.checkMessageLength()
        );
    }

    @Test
    public void testMessageHash() {
        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?",
                0
        );

        String expectedHash =
                message.getMessageID().substring(0, 2)
                + ":0:HITONIGHT";

        assertEquals(
                expectedHash,
                message.createMessageHash()
        );
    }

    @Test
    public void testMessageSuccessfullySent() {
        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?",
                0
        );

        assertEquals(
                "Message successfully sent.",
                message.SentMessage(1)
        );
    }

    @Test
    public void testMessageDisregarded() {
        Message message = new Message(
                "+27718693002",
                "Hi Keegan, did you receive the payment?",
                1
        );

        assertEquals(
                "Press 0 to delete the message.",
                message.SentMessage(2)
        );
    }

    @Test
    public void testMessageSuccessfullyStored() {
        Message message = new Message(
                "+27718693002",
                "This message will be stored.",
                1
        );

        assertEquals(
                "Message successfully stored.",
                message.SentMessage(3)
        );
    }

    @Test
    public void testTotalMessagesIncreasesAfterSending() {
        int totalBeforeSending = Message.returnTotalMessages();

        Message message = new Message(
                "+27718693002",
                "This message will be sent.",
                1
        );

        message.SentMessage(1);

        assertEquals(
                totalBeforeSending + 1,
                Message.returnTotalMessages()
        );
    }
}