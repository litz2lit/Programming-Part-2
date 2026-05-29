import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;

public class MessageApp {
    private static int messageCount = 0;

    public static String generateMessageId() {
        return "MSG" + System.currentTimeMillis();
    }

    private static String generateMessageHash(String messageId, int count, String message) {
        String[] words = message.trim().split("\\s+");
        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 1 ? words[words.length - 1] : first;
        return messageId.substring(0, 2) + ":" + count + ":" + first.toUpperCase() + last.toUpperCase();
    }

    public static void startChat() {
        while (true) {
            String menuText = "Welcome to Quick Chat:\n" +
                    "Select transition:\n" +
                    "Option 1 - Select Quick Chat\n" +
                    "Option 2 - Send Quick Chat\n" +
                    "Option 3 - Quit\n\n" +
                    "Enter your choice (1, 2 or 3):";

            String choiceInput = JOptionPane.showInputDialog(menuText);

            if (choiceInput == null) {
                JOptionPane.showMessageDialog(null, "Quitting Quickchat. Goodbye!");
                return;
            }

            int choice;
            try {
                choice = Integer.parseInt(choiceInput.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid choice. Please enter 1, 2 or 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    JOptionPane.showMessageDialog(null, "You select: Select Quick Chat\n\nThis feature is coming soon, please stay tuned");
                    break;

                case 2:
                    String recipient = "";
                    boolean resetting = false;

                    while (true) {
                        recipient = JOptionPane.showInputDialog("Enter your number (must start with +27 and be exactly 12 characters):");
                        if (recipient == null) {
                            resetting = true;
                            break;
                        }
                        if (recipient.startsWith("+27") && recipient.length() == 12) {
                            break;
                        }
                        JOptionPane.showMessageDialog(null, "Invalid number format. Try again.");
                    }

                    if (resetting) break;

                    String message = JOptionPane.showInputDialog("Enter your Quick Chat (must be 250 characters or less):");
                    if (message == null) break;

                    if (message.length() > 250) {
                        JOptionPane.showMessageDialog(null, "Please enter a Quick Chat of less than 250 characters");
                        break;
                    }

                    String messageId = generateMessageId();
                    int currentMessageCount = ++messageCount;
                    String messageHash = generateMessageHash(messageId, currentMessageCount, message);

                    String subMenuText = "Message Processed!\nMessage Hash: " + messageHash + "\n\n" +
                            "Choose an option:\n" +
                            "Option 1 - Send Quick Chat\n" +
                            "Option 2 - Disregard Quick Chat\n" +
                            "Option 3 - Store Quick Chat to send later\n\n" +
                            "Enter choice:";

                    String subInput = JOptionPane.showInputDialog(subMenuText);
                    if (subInput == null) break;

                    int subChoice;
                    try {
                        subChoice = Integer.parseInt(subInput.trim());
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid option. Quickchat not processed");
                        break;
                    }

                    switch (subChoice) {
                        case 1:
                            JOptionPane.showMessageDialog(null, "Sending Quickchat to " + recipient + "...\nMessage sent successfully");
                            break;
                        case 2:
                            JOptionPane.showMessageDialog(null, "Quickchat disregard");
                            break;
                        case 3:
                            storeMessageToTextFile(messageId, recipient, message, messageHash);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid option. Quickchat not processed");
                            break;
                    }
                    break;

                case 3:
                    JOptionPane.showMessageDialog(null, "Quitting Quickchat. Goodbye!");
                    return;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please enter 1, 2 or 3.");
                    break;
            }
        }
    }

    private static void storeMessageToTextFile(String Id, String recipient, String message, String hash) {
        try (FileWriter file = new FileWriter("stored_message.txt", true)) {
            file.write("MessageId: " + Id + "\n");
            file.write("Recipient: " + recipient + "\n");
            file.write("Message: " + message + "\n");
            file.write("Hash: " + hash + "\n");
            file.write("----\n");
            JOptionPane.showMessageDialog(null, "Message successfully saved to stored_message.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error storing message.");
        }
    }
}
