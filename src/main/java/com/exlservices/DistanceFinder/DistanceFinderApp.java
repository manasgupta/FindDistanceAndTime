package com.exlservices.DistanceFinder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.exlservices.DistanceFinder.model.DrivingDistanceAndTime;
import com.exlservices.DistanceFinder.util.ExcelFileLoaderUtil;
import com.exlservices.DistanceFinder.util.ResultFileExporter;

/**
 * @author Manas Gupta
 * @version 1.0
 */
public class DistanceFinderApp extends JDialog {

	private static ExcelFileLoaderUtil loader = null;

	private static DistanceCalculator calc = null;

	private static ResultFileExporter writer = null;

	private static TreeMap<Double, Double> mapOfFileOne = null;

	private static TreeMap<Double, Double> mapOfFileTwo = null;

	private static final long serialVersionUID = -4986721916119023563L;

	private static class BrowseOneButtonAction implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Browse the file to process");
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				fileOneName = chooser.getSelectedFile().getAbsolutePath();
				fileOnePathField.setText(fileOneName);
				mapOfFileOne = loader.importExcelFile(fileOneName);
			} else {
				JOptionPane.showMessageDialog(frame, "Could not open file",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private static class BrowseTwoButtonAction implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Browse the file to process");
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				fileTwoName = chooser.getSelectedFile().getAbsolutePath();
				fileTwoPathField.setText(fileTwoName);
				mapOfFileTwo = loader.importExcelFile(fileTwoName);
			} else {
				JOptionPane.showMessageDialog(frame, "Could not open file",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private static class DirectoryBrowseButtonAction implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Browse the directory to process");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(true);

			if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				directoryName = chooser.getSelectedFile().getAbsolutePath();
				finalPathField.setText(directoryName);
			} else {
				JOptionPane.showMessageDialog(frame,
						"Could not open directory", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private static class SubmitAction implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			List<DrivingDistanceAndTime> list = calc.calculateDistance(
					mapOfFileOne, mapOfFileTwo);
			writer.writeListToExcel(list, directoryName + "\\"
					+ fileNameTextField.getText());
			frame.dispose();
			System.exit(0);
		}
	}

	public static String fileOneName;

	public static String fileTwoName;

	public static JFrame frame;

	public static JLabel fileOnePathLabel;

	public static JLabel fileTwoPathLabel;

	public static JTextField fileOnePathField;

	public static JTextField fileTwoPathField;

	public static JButton browseOneButton;

	public static JButton browseTwoButton;

	public static JPanel fileOneChooserPanel;

	public static JPanel fileTwoChooserPanel;

	public static JButton submitButton;

	public static Stack<String> stackValue = new Stack<String>();

	private static JPanel buttonPanel;

	private static String directoryName;

	private static JLabel finalPathLabel;

	private static JTextField finalPathField;

	private static JButton directoryBrowseButton;

	private static JPanel finalPathChooserPanel;

	private static JLabel fileNameLabel;

	private static JTextField fileNameTextField;

	private static JPanel fileNamePanel;

	private static JPanel northPanel;

	private static JPanel centralPanel;

	public DistanceFinderApp() {
		super();
	}

	public static void main(String[] args) {

		loader = new ExcelFileLoaderUtil();
		calc = new DistanceCalculator();
		writer = new ResultFileExporter();
		createDialog();

	}

	private static void createDialog() {
		frame = new JFrame();
		frame.setTitle("File Loader");

		fileOnePathLabel = new JLabel("Path : ");

		fileOnePathField = new JTextField();
		fileOnePathField.setColumns(50);

		browseOneButton = new JButton("Browse");
		browseOneButton.addActionListener(new BrowseOneButtonAction());

		fileTwoPathLabel = new JLabel("Path : ");

		fileTwoPathField = new JTextField();
		fileTwoPathField.setColumns(50);

		browseTwoButton = new JButton("Browse");
		browseTwoButton.addActionListener(new BrowseTwoButtonAction());

		finalPathLabel = new JLabel("Path : ");

		finalPathField = new JTextField();
		finalPathField.setColumns(45);

		directoryBrowseButton = new JButton("Browse");
		directoryBrowseButton
				.addActionListener(new DirectoryBrowseButtonAction());

		fileNameLabel = new JLabel("File Name : ");
		fileNameTextField = new JTextField();
		fileNameTextField.setColumns(50);

		fileOneChooserPanel = new JPanel();
		fileOneChooserPanel.setLayout(new BorderLayout());
		fileOneChooserPanel.setBorder(BorderFactory
				.createTitledBorder("Select File # 1"));
		fileOneChooserPanel.add(fileOnePathLabel, BorderLayout.WEST);
		fileOneChooserPanel.add(fileOnePathField, BorderLayout.CENTER);
		fileOneChooserPanel.add(browseOneButton, BorderLayout.EAST);

		fileTwoChooserPanel = new JPanel();
		fileTwoChooserPanel.setLayout(new BorderLayout());
		fileTwoChooserPanel.setBorder(BorderFactory
				.createTitledBorder("Select File # 2"));
		fileTwoChooserPanel.add(fileTwoPathLabel, BorderLayout.WEST);
		fileTwoChooserPanel.add(fileTwoPathField, BorderLayout.CENTER);
		fileTwoChooserPanel.add(browseTwoButton, BorderLayout.EAST);

		finalPathChooserPanel = new JPanel();
		finalPathChooserPanel.setLayout(new BorderLayout());
		finalPathChooserPanel.setBorder(BorderFactory
				.createTitledBorder("Select Export Directory"));
		finalPathChooserPanel.add(finalPathLabel, BorderLayout.WEST);
		finalPathChooserPanel.add(finalPathField, BorderLayout.CENTER);
		finalPathChooserPanel.add(directoryBrowseButton, BorderLayout.EAST);

		fileNamePanel = new JPanel();
		fileNamePanel.setLayout(new BorderLayout());
		fileNamePanel.setBorder(BorderFactory
				.createTitledBorder("Name of Result Excel File"));
		fileNamePanel.add(fileNameLabel, BorderLayout.WEST);
		fileNamePanel.add(fileNameTextField, BorderLayout.EAST);

		submitButton = new JButton("SUBMIT");
		submitButton.addActionListener(new SubmitAction());

		buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(submitButton, BorderLayout.CENTER);

		northPanel = new JPanel(new BorderLayout());
		northPanel.add(fileOneChooserPanel, BorderLayout.NORTH);
		northPanel.add(fileTwoChooserPanel, BorderLayout.SOUTH);

		centralPanel = new JPanel(new BorderLayout());
		centralPanel.add(finalPathChooserPanel, BorderLayout.NORTH);
		centralPanel.add(fileNamePanel, BorderLayout.SOUTH);

		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(centralPanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		frame.setResizable(false);
		frame.setMinimumSize(new Dimension(250, 100));
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}