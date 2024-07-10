package client;

import server.NumberManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

/*
Client Class:
Registry: Connects to the RMI registry on localhost on port 1099.
NumberManager: Looks up the remote NumberManager object in the registry.
Random: Initializes a random number generator.
Number Generation: Continuously generates random numbers between 0 and 12, sending them to the server until the total reaches 1 million.
Delay: Waits 10 milliseconds before generating the next number to simulate concurrency.
Print: Prints the total and list of numbers when the total reaches 1 million.
*/

// The Client class connects to the server and generates random numbers
public class Client implements Runnable {
    private String clientId; // Variable to store the unique identifier of the client

    // Constructor to set the client ID
    public Client(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public void run() {
        try {
            // Connect to the RMI registry on localhost at port 1099
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Lookup the remote object "NumberManager" in the registry
            NumberManager server = (NumberManager) registry.lookup("NumberManager");

            // Notify server of new client connection
            server.incrementClientCount();

            // Wait until at least 5 clients are connected
            while (server.getClientCount() < 5) {
                Thread.sleep(100); // Check every 100ms
            }

            System.out.println(clientId + " - Starting number generation...");

            // Initialize a random number generator
            Random random = new Random();
            int clientCount = 0;

            // Continuously generate random numbers until the total on the server reaches 1 million
            while (server.getTotal() < 1000) { //Change this to 1000000 after testing. 1000 is for testing purposes
                int number = random.nextInt(13); // Generate a random number between 0 and 12
                server.addNumber(number, clientId); // Send the number to the server with client ID
                clientCount++;
                System.out.println(clientId + " : Generated number: " + number + " | Total Count: " + clientCount); // Log the generated number and count

                // Wait for 10 milliseconds before generating the next number
                Thread.sleep(1000); //Change this to 10ms after testing. 10000ms is for testing purposes = 10 seconds
            }

            // Print the total and the list of numbers once the total reaches 1 million
            System.out.println(clientId + " - Total reached: " + server.getTotal());
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace if there's an error
        }
    }

    public static void main(String[] args) {
        int numberOfClients = 10; // Define the number of client threads to run

        // Start the client threads
        for (int i = 0; i < numberOfClients; i++) {
            new Thread(new Client("Client " + (i + 1))).start();
        }
    }
}