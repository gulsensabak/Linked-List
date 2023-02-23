import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.sound.sampled.Line;

public abstract class Project1 implements Factory{
	public static void main(String[] args) {
		FileInputStream fis;
		ArrayList<String> myArrayList = new ArrayList<>();
		FactoryImpl factoryImpl = new FactoryImpl() {
		};
		
		
		File file = new File(args[1]);
		
	      PrintStream stream;
		try {
			stream = new PrintStream(file);
		      System.setOut(stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			fis = new FileInputStream(args[0]);
			Scanner scanner = new Scanner(fis);
			String[] holderStrings = null; 
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				line = line.replaceAll("\\s", " ").strip();
				myArrayList.add(line);
			}

			for (int i = 0; i < myArrayList.size(); i++) {
				String[] forelem = null;
				forelem = myArrayList.get(i).split(" ");

				if(forelem[0].equals("P")) {
					factoryImpl.Print();
				}
				if(forelem[0].equals("R")) {
					factoryImpl.reverse();
				}

				if(forelem[0].equals("AF")) {
					int productid = Integer.parseInt(forelem[1]);
					int productval = Integer.parseInt(forelem[2]);
					Product productmy = new Product(productid, productval);
					factoryImpl.addFirst(productmy);
				}

				if(forelem[0].equals("RF")){
					try {
						System.out.println(factoryImpl.removeFirst());

					} catch (NoSuchElementException e) {
						System.out.println(e.getMessage());
						// TODO: handle exception
					}				}

				if(forelem[0].equals("RL")) {
					try {
						System.out.println(factoryImpl.removeLast());

					} catch (NoSuchElementException e) {
						System.out.println(e.getMessage());
						// TODO: handle exception
					}
				}

				if(forelem[0].equals("AL")) {
					int productid = Integer.parseInt(forelem[1]);
					int productval = Integer.parseInt(forelem[2]);
					Product productmy = new Product(productid, productval);
					factoryImpl.addLast(productmy);
				}

				if(forelem[0].equals("A")) {
					try {
						int index = Integer.parseInt(forelem[1]);
						int productid = Integer.parseInt(forelem[2]);
						int productval = Integer.parseInt(forelem[3]);
						Product productmy = new Product(productid, productval);
						factoryImpl.add(index, productmy);
					}
					catch(IndexOutOfBoundsException e){
					System.out.println(e.getMessage());}

				}

				if(forelem[0].equals("RI")) {
					int index = Integer.parseInt(forelem[1]);
					try {
						System.out.println(factoryImpl.removeIndex(index));

					} catch (IndexOutOfBoundsException e) {
						System.out.println(e.getMessage());
					}					}

				if(forelem[0].equals("RP")) {
					int productval = Integer.parseInt(forelem[1]);
					try {
						System.out.println(factoryImpl.removeProduct(productval));

					} catch (NoSuchElementException e) {
						System.out.println(e.getMessage());
					}					}

				if(forelem[0].equals("F")) {
					int productid = Integer.parseInt(forelem[1]);
					try {
						System.out.println(factoryImpl.find(productid));

					} catch (NoSuchElementException e) {
						System.out.println(e.getMessage());
						// TODO: handle exception
					}					}

				if(forelem[0].equals("G")) {
					int index = Integer.parseInt(forelem[1]);
					try {
						System.out.println(factoryImpl.get(index));

					} catch (IndexOutOfBoundsException e) {
						System.out.println(e.getMessage());
					}	
				}


				if(forelem[0].equals("U")) {
					int productid = Integer.parseInt(forelem[1]);
					int productval = Integer.parseInt(forelem[2]);
					try {
						System.out.println(factoryImpl.update(productid, productval));

					} catch (NoSuchElementException e) {
						System.out.println(e.getMessage());
						// TODO: handle exception
					}					}

				if(forelem[0].equals("FD")) {
					System.out.println(factoryImpl.filterDuplicates());
				}


			}



		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	
		
		////
		////
		/////////////
		////////////
		////////////////
		
		
		
		
		
		
		
		
		
		
	      
	      
		
		
	    }
		

	}
