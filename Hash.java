import java.util.*;
import java.lang.Object;
import java.io.*;

// Use the simple HashMe() method to hash Strings. 
public class Hash{

	public static void main(String args[]){
		String input = "SayWhat";
		int hash = HashMe(input);
		System.out.println("Our new hash is: " + hash);
	}

	public static int HashMe(String s){ 

		int newHash = s.hashCode();
		return newHash;
	}
}
