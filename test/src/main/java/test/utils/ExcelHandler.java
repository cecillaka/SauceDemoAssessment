package test.utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class ExcelHandler {

	private final File file;
	private String path;

	public ExcelHandler(String filePath) {
		this.path=filePath;
		this.file = new File(filePath);
		Log4jLogger.loggerInfo(!isFileOpen(filePath) ? "File Found." : "");
	}
	public Object readExcel(String sheetName, String columnName, int rowNumber) throws IOException {
		
		
		
		try (FileInputStream fis = new FileInputStream(file);
				
				Workbook workbook = new XSSFWorkbook(fis)) {
		
			Log4jLogger.loggerInfo("reading data from : "+ path);

			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new IllegalArgumentException("Sheet " + sheetName + " does not exist.");
			}

			int rows = sheet.getPhysicalNumberOfRows();
			if (rowNumber <= 0 || rowNumber >= rows) {
				throw new IllegalArgumentException("Invalid row number: " + rowNumber);
			}

			// Get header row
			Row headerRow = sheet.getRow(0);
			if (headerRow == null) {
				throw new IllegalArgumentException("Sheet " + sheetName + " has no header row.");
			}

			// Map column name to index
			int cols = headerRow.getPhysicalNumberOfCells();
			int columnIndex = -1;
			for (int j = 0; j < cols; j++) {
				Cell cell = headerRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				if (cell.getCellType() == CellType.STRING && cell.getStringCellValue().equalsIgnoreCase(columnName)) {
					columnIndex = j;
					break;
				}
			}

			if (columnIndex == -1) {
				throw new IllegalArgumentException("Column '" + columnName + "' does not exist in sheet " + sheetName);
			}

			// Get the specified row
			Row row = sheet.getRow(rowNumber);
			if (row == null) {
				throw new IllegalArgumentException("Row " + rowNumber + " is empty.");
			}

			// Get the cell value
			Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue();
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					return cell.getDateCellValue().toString();
				} else {
					return cell.getNumericCellValue();
				}
			case BOOLEAN:
				return cell.getBooleanCellValue();
			default:
				return ""; // Return empty string for blank or unsupported cells
			}
		}
	}


	public void writeToExcel(String sheetName, String columnName, int rowNumber, Object value) throws IOException {
		try (FileInputStream fis = new FileInputStream(file);
				Workbook workbook = new XSSFWorkbook(fis)) {
			Log4jLogger.loggerInfo("writing data to : "+ path);
			// Get the sheet
			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new IllegalArgumentException("Sheet " + sheetName + " does not exist.");
			}

			// Get the header row
			Row headerRow = sheet.getRow(0);
			if (headerRow == null) {
				throw new IllegalArgumentException("Sheet " + sheetName + " has no header row.");
			}

			// Map column name to index
			int cols = headerRow.getPhysicalNumberOfCells();
			int columnIndex = -1;
			for (int j = 0; j < cols; j++) {
				Cell cell = headerRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				if (cell.getCellType() == CellType.STRING && cell.getStringCellValue().equalsIgnoreCase(columnName)) {
					columnIndex = j;
					break;
				}
			}

			if (columnIndex == -1) {
				throw new IllegalArgumentException("Column '" + columnName + "' does not exist in sheet " + sheetName);
			}

			// Ensure the specified row exists or create it
			Row row = sheet.getRow(rowNumber);
			if (row == null) {
				row = sheet.createRow(rowNumber);
			}

			// Create or update the cell value
			Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			if (value instanceof String) {
				cell.setCellValue((String) value);
			} else if (value instanceof Double) {
				cell.setCellValue((Double) value);
			} else if (value instanceof Boolean) {
				cell.setCellValue((Boolean) value);
			} else if (value instanceof Integer) {
				cell.setCellValue((Integer) value);
			} else {
				cell.setCellValue(value.toString());
			}

			// Write changes to the file
			try (FileOutputStream fos = new FileOutputStream(file)) {
				workbook.write(fos);
			}
		}
	}
	
	public int getNumberOfRows(String sheetName) throws IOException {
	    try (FileInputStream fis = new FileInputStream(file);
	         Workbook workbook = new XSSFWorkbook(fis)) {

	        Log4jLogger.loggerInfo("Getting number of rows from sheet: " + sheetName);

	        // Get the sheet
	        Sheet sheet = workbook.getSheet(sheetName);
	        if (sheet == null) {
	            throw new IllegalArgumentException("Sheet " + sheetName + " does not exist.");
	        }

	        // Return the number of rows
	        return sheet.getPhysicalNumberOfRows();
	    }
	}


	
	 public static boolean fileExists(String filePath) {
	        return new File(filePath).exists();
	    }
	 
	 public static boolean isFileOpen(String filePath) {
	        File file = new File(filePath);
	        if (!file.exists()) {
	            System.out.println("File does not exist.");
	            return false;
	        }

	        try (FileChannel channel = new FileOutputStream(file, true).getChannel();
	             FileLock lock = channel.tryLock()) {
	            
	            if (lock == null) {	               
	                Log4jLogger.loggerWarn("File is locked (possibly open).");
	                return true; // File is open somewhere else
	            }
	            return false; // File is not open
	        } catch (Exception e) {
	           
	            Log4jLogger.loggerWarn("WARNING :"+e.getMessage());
	            return true; // If an exception occurs, file is likely open
	        }
	    }
	 
	 
}
