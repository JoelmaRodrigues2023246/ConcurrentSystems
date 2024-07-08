package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// Defines the methods that can be called remotely by clients.

// The NumberManager interface defines the methods that can be invoked remotely
public interface NumberManager extends Remote {
    void addNumber(int number, String clientId) throws RemoteException;
    int getTotal() throws RemoteException;
    List<Integer> getNumList() throws RemoteException;
    void incrementClientCount() throws RemoteException;
    int getClientCount() throws RemoteException;
}