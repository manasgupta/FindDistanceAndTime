package com.exlservices.DistanceFinder.util;

import java.io.FileOutputStream;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.exlservices.DistanceFinder.model.DrivingDistanceAndTime;

/**
 * @author Manas Gupta
 * @since 21st Oct, 2015
 * @version 1.0
 */
public class ResultFileExporter {

	public boolean writeListToExcel(List<DrivingDistanceAndTime> list,
			String filePath) {
		try {
			Workbook workbook = new XSSFWorkbook();
			Sheet resultSetSheet = workbook
					.createSheet("Driving Distance & Time");
			int rowIndex = 0;
			Row row = resultSetSheet.createRow(rowIndex++);
			int cellIndex = 0;
			row.createCell(cellIndex++).setCellValue("SOURCE ADDRESS");
			row.createCell(cellIndex++).setCellValue("DESTINATION ADDRESS");
			row.createCell(cellIndex++).setCellValue("DRIVING DISTANCE");
			row.createCell(cellIndex++).setCellValue("DRIVING TIME");
			for (DrivingDistanceAndTime obj : list) {
				row = resultSetSheet.createRow(rowIndex++);
				cellIndex = 0;
				row.createCell(cellIndex++)
						.setCellValue(obj.getSourceAddress());
				row.createCell(cellIndex++).setCellValue(
						obj.getDestinationAddress());
				row.createCell(cellIndex++).setCellValue(
						obj.getDrivingDistance());
				row.createCell(cellIndex++).setCellValue(obj.getDrivingTime());
			}
			FileOutputStream fos = new FileOutputStream(filePath + ".xlsx");
			workbook.write(fos);
			fos.close();
			System.out.println(filePath + " is successfully written");
			JOptionPane optionPane = new JOptionPane(
					"File exported successfully to this location : " + filePath
							+ ".xlsx", JOptionPane.INFORMATION_MESSAGE);
			JDialog dialog = optionPane.createDialog("Success");
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
			return true;
		} catch (Exception e) {
			JOptionPane optionPane = new JOptionPane(
					"Export operation of results failed due to : \n"
							+ e.getMessage(), JOptionPane.ERROR_MESSAGE);
			JDialog dialog = optionPane.createDialog("Failure");
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
			return false;
		}
	}
}
