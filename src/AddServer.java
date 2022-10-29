import java.net.*;
import java.rmi.*;
import java.io.*;
public class AddServer {
  public static void main(String args[]) {
    try {
      AddServerImpl addServerImpl = new AddServerImpl();
      Naming.rebind("AddServer", addServerImpl);
      File myObj = new File("reservas.txt");
      if (myObj.createNewFile()) {
        System.out.println("File created: " + myObj.getName());
      } else {
        System.out.println("File already exists.");    
      }
    }
    catch(Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}
