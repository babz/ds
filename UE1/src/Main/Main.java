package Main;

import java.io.*;
import java.util.logging.Logger;

import Connector.SocketConnector;

public class Main {
	public static void main(String[] args) {
		
		SocketConnector socket = null;

		try {
			socket = new SocketConnector();
			socket.readStream();
			socket.closeConnections();
		} catch (IOException exc) {
			System.out.println("connection failed");
		}
	}
}
