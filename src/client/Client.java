package client;

import server.NumberManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

/*
Client:
Registry: Connects to the RMI registry on localhost on port 1099.
NumberManager: Looks up the remote NumberManager object in the registry.
Random: Initializes a random number generator.
Number Generation: Continuously generates random numbers between 0 and 12, sending them to the server until the total reaches 1 million.
Delay: Waits 10 milliseconds before generating the next number to simulate concurrency.
Print: Prints the total and list of numbers when the total reaches 1 million.
*/

// The Client class connects to the server and generates random numbers
public class Client {
    public static void main(String[] args) {
        try {
            // Connect to the RMI registry on localhost at port 1099
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Lookup the remote object "NumberManager" in the registry
            NumberManager server = (NumberManager) registry.lookup("NumberManager");

            // Initialize a random number generator
            Random random = new Random();
            int clientCount = 0;

            // Continuously generate random numbers until the total on the server reaches 1 million
            while (server.getTotal() < 1000) { // Change this to 100000, 1000 is for testing
                int number = random.nextInt(13); // Generate a random number between 0 and 12
                server.addNumber(number); // Send the number to the server
                clientCount++;
                System.out.println("Generated number: " + number + " | Client Count: " + clientCount); // Log the generated number and count

                // Wait for 10 milliseconds before generating the next number
                Thread.sleep(10);
            }

            // Print the total and the list of numbers once the total reaches 1 million
            System.out.println("Total reached: " + server.getTotal());
            System.out.println("Number list: " + server.getNumList());
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace if there's an error
        }
    }
}