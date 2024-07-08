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

// The Client class connects to the server -> then generates random numbers
public class Client {
    public static void main(String[] args) {
        try {
            // Connect to the RMI registry to localhost (port: 1099)
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Lookup the remote object "NumberManager" in the registry
            NumberManager server = (NumberManager) registry.lookup("NumberManager");

            // Starts a random number generator
            Random random = new Random();
            int count = 0;

            /* Generate random numbers (continuously) until the total reaches 1 million */
            while (server.getTotal() < 1000000) {
                int number = random.nextInt(13); // Generate a random number between 0 and 12
                server.addNumber(number); // Send the number to the server
                count++;
                Thread.sleep(10); // Wait for 10 milliseconds** before generating the next number
            }

            // Print the total and the list of numbers once the total reaches 1 million
            System.out.println("Total reached: " + server.getTotal());
            System.out.println("Number list: " + server.getNumList());
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace if there is an error (handling exceptions)
        }
    }
}
