package myproject;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ContactManager {

    private Connection connection;

    public ContactManager(Connection connection) {
        this.connection = connection;
    }
     //TABLE CREATION
    public void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS contact (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "phoneNumber VARCHAR(200) NOT NULL" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
            System.out.println("Table 'contact' created or already exists.");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }
    //ADD CONTACT
    public void addContact(String name, String phoneNumber) {
        String insertDataSQL = "INSERT INTO contact (name, phoneNumber) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phoneNumber);
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    System.out.println("Contact inserted successfully into the database with ID: " + generatedId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error adding contact: " + e.getMessage());
        }
    }

 // READ ALL CONTACT 
    public List<String> getAllContacts() {
        List<String> formattedContacts = new ArrayList<>();
        String query = "SELECT * FROM contact";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> contactMap = new LinkedHashMap<>(); // LinkedHashMap to maintain insertion order

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value;

                    // Handling different data types
                    if (metaData.getColumnType(i) == Types.INTEGER) {
                        value = Math.abs(resultSet.getInt(i));
                    } else {
                        value = resultSet.getObject(i);
                    }

                    contactMap.put(columnName, value);
                }

                // Convert the HashMap to a custom JSON-like format with each contact on a new line
                StringBuilder jsonContact = new StringBuilder("{");
                contactMap.forEach((key, value) -> jsonContact.append("\n\t").append(key).append(" = ").append(value).append(","));

                // Remove the trailing comma and close the object
                jsonContact.deleteCharAt(jsonContact.length() - 1).append("\n}");
                
                formattedContacts.add(jsonContact.toString());
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving contacts: " + e.getMessage());
        }

        return formattedContacts;
    }
    

    //UPDATE CONTACT
    public void updateContact(int id, String newName, String newPhoneNumber) {
        String updateDataSQL = "UPDATE contact SET name=?, phoneNumber=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateDataSQL)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newPhoneNumber);
            preparedStatement.setInt(3, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Contact updated successfully.");
            } else {
                System.out.println("Contact not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating contact: " + e.getMessage());
        }
    }
    //DELETE CONTACT
    public void deleteContact(int id) {
        String deleteDataSQL = "DELETE FROM contact WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteDataSQL)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Contact deleted successfully.");
            } else {
                System.out.println("Contact not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting contact: " + e.getMessage());
        }
    }

//=========================HASHMAP METHOD WITHOUT MAINTAINING INSERTION ORDER============================//

//READ ALL CONTACT
//public List<String> getAllContacts() {
// List<String> formattedContacts = new ArrayList<>();
// String query = "SELECT * FROM contact";
// try (Statement statement = connection.createStatement();
//      ResultSet resultSet = statement.executeQuery(query)) {
//     ResultSetMetaData metaData = resultSet.getMetaData();
//     int columnCount = metaData.getColumnCount();
//
//     while (resultSet.next()) {
//         Map<String, Object> contactMap = new HashMap<>();
//
//         for (int i = 1; i <= columnCount; i++) {
//             String columnName = metaData.getColumnName(i);
//             Object value;
//
//             // Handling different data types
//             if (metaData.getColumnType(i) == Types.INTEGER) {
//                 value = Math.abs(resultSet.getInt(i));
//             } else {
//                 value = resultSet.getObject(i);
//             }
//
//             contactMap.put(columnName, value);
//         }
//
//         // Convert the HashMap to a custom JSON-like format with each contact on a new line
//         StringBuilder jsonContact = new StringBuilder("{");
//         contactMap.forEach((key, value) -> jsonContact.append("\n\t").append(key).append(": ").append(value).append(","));
//
//         // Remove the trailing comma and close the object
//         jsonContact.deleteCharAt(jsonContact.length() - 1).append("\n}");
//
//         formattedContacts.add(jsonContact.toString());
//     }
// } catch (SQLException e) {
//     System.out.println("Error retrieving contacts: " + e.getMessage());
// }
//
// return formattedContacts;
//}
}
