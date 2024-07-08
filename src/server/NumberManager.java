package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// The NumberManager interface defines the methods that can be invoked remotely
public interface NumberManager extends Remote {
    // Method to add a number to the list and update the total
    void addNumber(int number) throws RemoteException;

    // Method to get the current total sum of numbers
    int getTotal() throws RemoteException;

    // Method to get the list of numbers
    List<Integer> getNumList() throws RemoteException;
}