package com.mycompany.prog5121poe;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageManagerTest {

    private MessageManager manager;

    @Before
    public void setUp() {
        manager = new MessageManager();

        manager.addMessage(
                "Developer", "+27834557896",
                "Did you get the cake?",
                "Sent", "MESSAGE-1", "HASH-1");

        manager.addMessage(
                "Developer", "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                "Stored", "MESSAGE-2", "HASH-2");

        manager.addMessage(
                "Developer", "+27834484567",
                "Yohoooo, I am at your gate.",
                "Disregard", "MESSAGE-3", "HASH-3");

        manager.addMessage(
                "Developer", "0838884567",
                "It is dinner time!",
                "Sent", "0838884567", "HASH-4");

        manager.addMessage(
                "Developer", "+27838884567",
                "Ok, I am leaving without you.",
                "Stored", "MESSAGE-5", "HASH-5");
    }
    @Test
public void testSentMessagesArrayCorrectlyPopulated() {
    assertEquals(2, manager.getSentMessages().size());

    assertEquals(
            "Did you get the cake?",
            manager.getSentMessages().get(0)
    );

    assertEquals(
            "It is dinner time!",
            manager.getSentMessages().get(1)
    );
}
@Test
public void testSearchForMessageID() {
    assertEquals(
            "It is dinner time!",
            manager.searchMessageByID("0838884567")
    );
}
@Test
public void testSearchMessagesByRecipient() {
    String expected =
            "Where are you? You are late! I have asked you to be on time."
            + "\n"
            + "Ok, I am leaving without you.";

    assertEquals(
            expected,
            manager.searchMessagesByRecipient("+27838884567")
    );
}
@Test
public void testDeleteMessageUsingHash() {
    assertEquals(
            "Message: \"Where are you? You are late! "
            + "I have asked you to be on time.\" successfully deleted.",
            manager.deleteMessageByHash("HASH-2")
    );

    assertFalse(
            manager.getStoredMessages().contains(
                    "Where are you? You are late! "
                    + "I have asked you to be on time."
            )
    );
}
@Test
public void testDisplayMessageReport() {
    String report = manager.displayMessageReport();

    assertTrue(report.contains("Message Hash: HASH-1"));
    assertTrue(report.contains("Recipient: +27834557896"));
    assertTrue(report.contains("Message: Did you get the cake?"));

    assertTrue(report.contains("Message Hash: HASH-2"));
    assertTrue(report.contains("Recipient: +27838884567"));
    assertTrue(report.contains(
            "Where are you? You are late! I have asked you to be on time."
    ));
}
@Test
public void testDisplayLongestStoredMessage() {
    assertEquals(
            "Where are you? You are late! I have asked you to be on time.",
            manager.getLongestStoredMessage()
    );
}
}