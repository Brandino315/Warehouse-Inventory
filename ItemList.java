import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

public class ItemList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2512613147066730239L;
	private String[] columnNames = {"Item", "Par", "Low", "In Stock", "Low", ""};
	private String[][] itemArray = new String[256][6];
	
	// attempts to load saveFile.sav and populate list with values
	public String[][] loadList() {
		try {
			FileInputStream saveFile = new  FileInputStream("saveFile.sav");
			ObjectInputStream restore = new ObjectInputStream(saveFile);
			String[][] loadList = (String[][])restore.readObject();
			itemArray = loadList;
			restore.close();
			return itemArray;
			
		} catch (Exception e) {
			try {
				File namesList = new File("namesList.csv");
				Scanner fileScanner = new Scanner(namesList);
				fileScanner.useDelimiter(",");
				for(int i = 0; i < itemArray.length; i++) {
					if(fileScanner.hasNextLine()) {
						String line = fileScanner.nextLine();
						itemArray[i][0] = line;
					}
				}
				
				fileScanner.close();
			} catch (FileNotFoundException e1) {
			}
			for(int i = 0; i < itemArray.length; i++) {
				for(int j = 1; j < itemArray[i].length - 1; j++) {
					itemArray[i][j] = "0";
				}
			}
			return itemArray;
		}
		
		
	}
	
	// returns column names array
	public String[] getCNames() {
		return columnNames;
	}
	
	// returns itemArray
	public String[][] getArray() {
		return itemArray;
	}
	
	// returns length of itemArray
	public int getLength() {
		return itemArray.length;
	}
	
	// Sets column 4 of itemArray to input value, currently unused
	public void setStock(int i, String j) {
		itemArray[i][3] = j;
	}
	
	// overwrites current saveFile.sav with new values
	public void saveList(String[][] s) {
		try {
			FileOutputStream saveFile = new FileOutputStream("saveFile.sav");
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(s);
			save.close();
		} catch(Exception e) {
			System.out.println("Cant save");
		}
		
	}
	
	// creates a .csv save of current items and their inStock value in directory.
	public void copyList(String file) {
		try {
			File cFile = new File(file);
			cFile.createNewFile();
			FileWriter copyFile = new FileWriter(cFile);
			BufferedWriter copy = new BufferedWriter(copyFile);
			copy.write(this.toCSV());
			copy.close();
		} catch (Exception o) {
			
		}
	}
	
	// iterates through itemAray and appends name/stock to .csv format
	public String toCSV() {
		StringBuilder string = new StringBuilder();
		for(int i = 0; i < itemArray.length; i++) {
			string.append(itemArray[i][0]);
			string.append(", ");
			string.append(itemArray[i][1]);
			string.append(", ");
			string.append(itemArray[i][2]);
			string.append(", ");
			string.append(itemArray[i][3]);
			string.append(", ");
			string.append(itemArray[i][4]);
			string.append("\n");
		}
		
		
		return string.toString();
	}
	
}


