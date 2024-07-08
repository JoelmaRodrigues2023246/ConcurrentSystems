package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/*
Server Class:
numList: List that stores the generated numbers.
total: Variable that stores the total sum of numbers.
Constructor: Initializes numList and total.
addNumber: adds a number to the list and updates the total. Synchronous to ensure thread safety.
getTotal: returns the total sum of numbers.
getNumList: returns the list of numbers.
main: Main method that starts the RMI registration and binds the server instance to the name "Server".
*/

// The Server class implements the remote interface NumberManager and extends UnicastRemoteObject
public class Server extends UnicastRemoteObject implements NumberManager {
    private List<Integer> numList; // List to store generated numbers
    private int total; // Variable to store the total sum of numbers

    // Constructor initializes the numList and total
    protected Server() throws RemoteException {
        numList = new ArrayList<>();
        total = 0;
    }

    // Method to add a number to numList and update the total
    @Override
    public synchronized void addNumber(int number) throws RemoteException {
        numList.add(number); // Add number to the list
        total += number; // Add number to the total
        System.out.println("Number added: " + number + " | New total: " + total); // Log the number and total
    }

    // Method to return the total sum of numbers
    @Override
    public int getTotal() throws RemoteException {
        return total;
    }

    // Method to return the list of numbers
    @Override
    public List<Integer> getNumList() throws RemoteException {
        return numList;
    }

    // Main method to start the RMI registry and bind the server instance
    public static void main(String[] args) {
        try {
            Server server = new Server(); // Create a new server instance
            Registry registry = LocateRegistry.createRegistry(1099); // Create RMI registry (port: 1099)
            registry.rebind("NumberManager", server); // Bind the server instance to the name "NumberManager"
            System.out.println("Server is ready."); // Indicate that the server is ready
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace if there's an error
        }
    }
}