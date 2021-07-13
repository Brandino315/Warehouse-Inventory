import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;




public class Main {
	
	public static void main(String[] args) {
		
		// Sets L&F 
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		
		// initialize GUI components
		ListPanel panel = new ListPanel();
		JFrame frame = new JFrame();
		
		// set up frame to hold scroll pane
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 2000, 1600);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.isDoubleBuffered();
		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.pack();
		
		
	}

}
