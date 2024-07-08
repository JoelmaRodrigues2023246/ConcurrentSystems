package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    private AtomicInteger clientCount; // Atomic integer to count the number of connected clients

    // Constructor initializes the numList, total, and clientCount
    protected Server() throws RemoteException {
        numList = new ArrayList<>();
        total = 0;
        clientCount = new AtomicInteger(0);
    }

    // Method to add a number to numList and update the total
    @Override
    public synchronized void addNumber(int number, String clientId) throws RemoteException {
        numList.add(number); // Add number to the list
        total += number; // Add number to the total
        System.out.println("Number " + number + " received from client " + clientId + ". Total: " + total); // Log the number and total with client ID
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

    // Method to increment the client count
    @Override
    public synchronized void incrementClientCount() throws RemoteException {
        int currentCount = clientCount.incrementAndGet();
        System.out.println("Client connected. Total clients: " + currentCount); // Log the client count

        if (currentCount >= 5) {
            System.out.println("At least 5 clients connected. Starting number generation...");
        }
    }

    // Method to get the client count
    @Override
    public int getClientCount() throws RemoteException {
        return clientCount.get();
    }

    // Main method to start the RMI registry and bind the server instance
    public static void main(String[] args) {
        try {
            Server server = new Server(); // Create a new server instance
            Registry registry = LocateRegistry.createRegistry(1099); // Create RMI registry on port 1099
            registry.rebind("NumberManager", server); // Bind the server instance to the name "NumberManager"
            System.out.println("Server is ready and waiting for client connections..."); // Indicate that the server is ready
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace if there's an error
        }
    }
}