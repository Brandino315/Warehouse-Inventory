import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class ListPanel extends JPanel {
	private ItemList s;
	Toolkit toolKit;
	private JTable table;
	private String[] columnNames = {"Item", "Par", "Low Threshold", "In Stock", "Low", ""};
	private String[][] itemArray = new String[256][6];
	public ListPanel() {
		
		// Builds 2-D array and String array for JTable values
		///////////////////////////////////////////////	
		s = new ItemList();
		itemArray = s.loadList();
		table = new JTable(itemArray, columnNames);
		JScrollPane scroll = new JScrollPane(table);
		scroll.isDoubleBuffered();
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//////////////////////////////////////////////
		
		// sets layout and instantiates toolkit
		setLayout(new BorderLayout());
		toolKit = Toolkit.getDefaultToolkit();
		
		// Tool buttons
		/////////////////////////////////////////////////
		JToolBar buttonBar = new JToolBar();
		buttonBar.setLayout(new FlowLayout());
		
		JButton subtract = new JButton("   Subtract   ");
		subtract.addActionListener(new subtractStock());
		
		JButton mkCopy = new JButton("   Copy File   ");
		mkCopy.addActionListener(new mkCopy());
		
		JButton paste = new JButton("    Paste    ");
		paste.addActionListener(new paste());
		
		JButton add = new JButton("     Add     ");
		add.addActionListener(new addStock());
		
		JButton setPar = new JButton("   Set Par   ");
		setPar.addActionListener(new changePar());
		
		JButton save = new JButton("     SAVE     ");
		save.addActionListener(new Save());
		
		JButton low = new JButton("   Set Low   ");
		low.addActionListener(new SetLow());
		
		JButton order = new JButton("  Order #'s  ");
		order.addActionListener(new Order());
		
		JButton copy = new JButton("   Copy   ");
		copy.addActionListener(new copy());
		////////////////////////////////////////////////
		
		updatePanel();
		
		// add JTable to panel, add buttons to button bar then to panel
		//////////////////////////////////////
		add(scroll, BorderLayout.CENTER);
		buttonBar.add(copy);
		buttonBar.add(paste);
		buttonBar.add(subtract);
		buttonBar.add(add);
		buttonBar.add(setPar);
		buttonBar.add(low);
		buttonBar.add(order);
		buttonBar.add(mkCopy);
		buttonBar.add(save);
		add(buttonBar, BorderLayout.SOUTH);
		/////////////////////////////////////
		
		
		
	}
	// changes JTable to display current values
	public void updatePanel() {
		table.repaint();
	}
	
	// appends 5th column of 2D array: itemList to store values of JTable: table
	public void appendValues() {
		for(int i = 0; i < s.getLength(); i++) {
			itemArray[i][5] = (String) table.getValueAt(i, 5);
		}
	}
	
	// clears column 3. meant for use after each button press
	public void clearC3() {
		for(int i = 0; i < s.getLength(); i++) {
			table.setValueAt("", i, 5);
		}
	}
	
	// Subtracts int in column 3 from inStock variable of item
	private class subtractStock implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			appendValues();
			int value = 0;
			for(int i = 0; i < s.getLength(); i++) {
				try {
					value = Integer.parseInt(itemArray[i][3]) - Integer.parseInt(itemArray[i][5]);
					itemArray[i][3] =  String.valueOf(value);
					checkLow(value, i);
				} catch(Exception q) {
					
				}
				
			}
			
			clearC3();
			updatePanel();
			
			
		}
		
	}
	
	// checks if inStock for an item is below low threshold, appends low column if true
	public void checkLow(int value, int row) {
		for(int i = 0; i < s.getLength(); i++) {
			if(value <= Integer.parseInt(itemArray[i][2])) {
			itemArray[i][4] = "LOW";
			}
		}
	}
	
	// Sets low of each item to corresponding number in column 3
	private class SetLow implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			for(int i = 0; i < s.getLength(); i++) {
				try {
					itemArray[i][2] = itemArray[i][5];
				} catch (Exception g) {
					
				}
				
			}
			clearC3();
			updatePanel();
			
		}
			
		
	}
	
	private class Order implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int z = 0;
			try {
				for (int i = 0; i < s.getLength(); i++) {
					z = (Integer.parseInt(itemArray[i][1]) - Integer.parseInt(itemArray[i][3]));
					itemArray[i][5] = String.valueOf(z);
				}
				updatePanel();
			} catch (Exception e) {
				
			}
		}
		
	}
	
	// Saves a copy of list as .csv into directory
	private class mkCopy implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String file = JOptionPane.showInputDialog(getParent(),
                    "Enter file name, add .csv to the end", null);
			s.copyList(file);
			
		}
		
	}
	
	// Adds to inStock variable for each item from column 3
	private class addStock implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			appendValues();
			int z = 0;
			for(int i = 0; i < s.getLength(); i++) {
				try {
					z = Integer.parseInt(itemArray[i][3]) + Integer.parseInt(itemArray[i][5]);
					itemArray[i][3] = String.valueOf(z);
				} catch (Exception g) {
					
				}
				
			}
			clearC3();
			updatePanel();
			
		}
		
	}
	
	// Sets par of item in row to corresponding number in column 3
	private class changePar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			appendValues();
			for(int i = 0; i < s.getLength(); i++) {
				try {
					itemArray[i][1] = itemArray[i][5];
				} catch (Exception g) {
					
				}
			}
			clearC3();
			updatePanel();
		}
		
		
	}
	
	//pastes contents of clip board into column 3
	private class paste implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			clearC3();
			try {
				String clip = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
				Scanner scan = new Scanner(clip);
				scan.useDelimiter("\n");
				int i = 0;
				String line;
				while(scan.hasNext() && i < 256) {
					line = scan.next();
					table.setValueAt(line, i, 5);
					i++;
				}
				scan.close();
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (UnsupportedFlavorException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
		}
		
	}
	
	private class copy implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			StringBuilder string = new StringBuilder();
			for(int i = 0; i < s.getLength(); i++) {
				string.append(table.getValueAt(i, 5) + "\n");
			}
			StringSelection selection = new StringSelection(string.toString());
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
			
		}
		
	}
	
	// Saves list to saveFile.sav in directory
	private class Save implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			s.saveList(itemArray);
			
		}
		
	}

	
	
	
}
