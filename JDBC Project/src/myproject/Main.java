package myproject;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            // Get a connection from ConnectionUtil
            Connection connection = ConnectionUtil.getConnection();

            // Create ContactManager instance with the obtained connection
            ContactManager contactManager = new ContactManager(connection);

            // Create the 'contact' table
            contactManager.createTable();

            // Add three contacts
            contactManager.addContact("Monika", "1234567890");
            contactManager.addContact("Kanmani", "9876543210");
            contactManager.addContact("Prema", "5555555555");

            // View all contacts
            System.out.println("All Contacts:");
            contactManager.getAllContacts().forEach(System.out::println);

            // Update the contact with ID 2
            contactManager.updateContact(2, "Kanmani Updated", "3333333333");

            // View all contacts after update
            System.out.println("All Contacts after Update:");
            contactManager.getAllContacts().forEach(System.out::println);

            // Delete the contact with ID 3
            contactManager.deleteContact(3);

            // View all contacts after delete
            System.out.println("All Contacts after Delete:");
            contactManager.getAllContacts().forEach(System.out::println);

            // Close the connection
            connection.close();
            System.out.println("Operations completed successfully");
        } catch (SQLException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
