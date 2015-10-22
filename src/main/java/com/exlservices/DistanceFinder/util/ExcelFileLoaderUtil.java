package com.exlservices.DistanceFinder.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Manas Gupta
 * @since 21st Oct, 2015
 * @version 1.0
 */
public class ExcelFileLoaderUtil {

	public TreeMap<Double, Double> importExcelFile(String filePath) {

		TreeMap<Double, Double> map = new TreeMap<Double, Double>();

		try {
			FileInputStream file = new FileInputStream(new File(filePath));

			XSSFWorkbook workbook = new XSSFWorkbook(file);

			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Double key = null;
				Double value = null;
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				if (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						key = cell.getNumericCellValue();
					}
				}
				if (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						value = cell.getNumericCellValue();
					}
				}
				if (key != null && value != null) {
					map.put(key, value);
				}
			}
			file.close();

		} catch (Exception e) {
			JOptionPane optionPane = new JOptionPane("Unable to Read : \n"
					+ e.getMessage(), JOptionPane.ERROR_MESSAGE);
			JDialog dialog = optionPane.createDialog("Failure");
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
		}
		return map;

	}
}
