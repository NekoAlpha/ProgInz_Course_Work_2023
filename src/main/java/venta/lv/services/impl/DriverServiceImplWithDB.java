package venta.lv.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import venta.lv.models.Trip;
import venta.lv.models.users.Buscategory;
import venta.lv.models.users.Driver;
import venta.lv.models.users.Person;
import venta.lv.repos.users.IDriverRepo;
import venta.lv.services.IDriverCRUDService;

@Service
public class DriverServiceImplWithDB implements IDriverCRUDService{
		
	@Autowired
	private IDriverRepo driverRepo;
	
	
	@Override
	public Page<Driver> selectAllDrivers(Pageable pageable) {
		return (Page<Driver>)driverRepo.findAll(pageable);
	}	
	
	@Override
	public Driver selectDriverById(long idd) throws Exception {
		if(driverRepo.existsById(idd))
		{
			return driverRepo.findById(idd).get();
		}
		else
		{
			throw new Exception("Wrong id");
		}
	}
	
	@Override
	public void deleteDriverById(long idd) throws Exception {
		if(driverRepo.existsById(idd)) {
			driverRepo.deleteById(idd);
		}
		else
		{
			throw new Exception("Wrong id");
		}
		
	}   
	
	@Override
	public void insertNewDriver(Driver driver) {
	    if (!driverRepo.existsByNameAndSurnameAndBuscategory(driver.getName(), driver.getSurname(), driver.getBuscategory())) {
	        driverRepo.save(driver);
	    }
	}
	
	@Override
	public void updateDriverById(long idd, Driver updatedDriver) {

	    driverRepo.findById(idd).ifPresent(driver -> {

	        driver.setName(updatedDriver.getName());
	        driver.setSurname(updatedDriver.getSurname());
	        driver.setBuscategory(updatedDriver.getBuscategory());

	        driverRepo.save(driver);
	    });
	}

	@Override
	public Driver findById(long idd) throws Exception {
		if(driverRepo.existsById(idd))
		{
			return driverRepo.findById(idd).get();
		}
		else
		{
			throw new Exception("Wrong id");
		}
	}

	@Override
	public void updateDriver(long idd, @Valid Driver updatedDriver) {
		driverRepo.findById(idd).ifPresent(driver -> {
			driver.setName(updatedDriver.getName());
			driver.setSurname(updatedDriver.getSurname());
			driver.setBuscategory(updatedDriver.getBuscategory());
			driverRepo.save(driver);
		});
	}
	
	@Override
	public int getTotalPages(int pageSize) {
	    long totalDrivers = driverRepo.count();
	    return (int) Math.ceil((double) totalDrivers / pageSize);
	}
	
	@Override
	public Workbook ExportDriversToExelFile(Pageable pageable) {
		
		Page<Driver> driver = selectAllDrivers(pageable);
		
		Workbook workbook = new XSSFWorkbook();
		
		Sheet sheet = workbook.createSheet("Drivers");
		
		Row headRow = sheet.createRow(0);
		
		String[] headers = {"Id", "Vārds", "Uzvārds", "Buskategorija"};
		
		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			headerRow.createCell(i).setCellValue(headers[i]);
		}
		
		int rowNumber = 1;
		
		for (int i = 0; i < headers.length; i++) {
            headRow.createCell(i).setCellValue(headers[i]);
        }
		
		for (Driver drivers : driver) {
			Row dataRow = sheet.createRow(rowNumber++);
			dataRow.createCell(0).setCellValue(drivers.getName());
			dataRow.createCell(1).setCellValue(drivers.getSurname());
			dataRow.createCell(3).setCellValue(drivers.getBuscategory().toString());
		}
		
		for (int i = 0; i < 3; i ++) {
			sheet.setColumnWidth(i, 8000);
		}
		return workbook;

	}
	
	 @Override
	    public Driver retrieveDriversByIdd(Long idd) throws Exception {
	        if (driverRepo.existsByIdd(idd)){
	            return driverRepo.findByIdd(idd);
	        } else {
	            throw new Exception("Wrong Idd");
	        }
	    }

	 @Override
	 public void importDriversFromExcel(InputStream excelFile) throws Exception {
	     Workbook workbook = new XSSFWorkbook(excelFile);
	     Sheet sheet = workbook.getSheetAt(0);
	     Iterator<Row> rowIterator = sheet.iterator();
	     
	     List<Driver> drivers = (List<Driver>) driverRepo.findAll();

	     while (rowIterator.hasNext()) {
	         Row row = rowIterator.next();

	         // Skip the header row
	         if (row.getRowNum() == 0) {
	             continue;
	         }

	         Long idd = (long) row.getCell(0).getNumericCellValue();
	         String name = row.getCell(1).getStringCellValue();
	         String surname = row.getCell(2).getStringCellValue();
	         // String busCategory = row.getCell(3).getStringCellValue(); 

	         if (!driverRepo.existsByIdd(idd)) {
	             drivers.get(0);
	             Driver newDriver = new Driver(name, surname, null);
	             driverRepo.save(newDriver);
	 	    }
	     }
	 }
	    
	    @Override
	    public Driver createNewDriver(String name, String surname ) {
	        return driverRepo.save(new Driver(name, surname, null));
	    }
	    
	    @Override
		public XWPFDocument exportDriversToWord(Pageable pageable) {
	    	Page<Driver> drivers = selectAllDrivers(pageable);

	        XWPFDocument document = new XWPFDocument();
	        XWPFParagraph paragraph = document.createParagraph();

	        XWPFRun run = paragraph.createRun();
	        run.setBold(true);
	        run.setText("Drivers");

	        XWPFTable table = document.createTable(drivers.getSize() + 1, 6);

	        String[] headers = {"Name", "Surname", "Buscategory"};
	        XWPFTableRow headerRow = table.getRow(0);
	        for (int i = 0; i < headers.length; i++) {
	            XWPFTableCell headerCell = headerRow.getCell(i);
	            headerCell.setText(headers[i]);
	        }

	        int rowNum = 1;
	        for (Driver driver : drivers) {
	            XWPFTableRow dataRow = table.getRow(rowNum++);
	            dataRow.getCell(0).setText(driver.getName());
	            dataRow.getCell(1).setText(driver.getSurname());
	            dataRow.getCell(2).setText(driver.getBuscategory().toString());

	        }

	        for (int i = 0; i < 4; i++) {
	            CTTblWidth cellWidth = table.getRow(0).getCell(i).getCTTc().addNewTcPr().addNewTcW();
	            cellWidth.setType(STTblWidth.DXA);
	            cellWidth.setW(BigInteger.valueOf(3000));
	        }

	        return document;
	    }
	    
	    @Override
		public void importDriversFromWord(InputStream docxFile) throws IOException {
	    	XWPFDocument document = new XWPFDocument(docxFile);
	        List<Driver> drivers = (List<Driver>) driverRepo.findAll();
	        int currentDriverIndex = 2;

	        for (XWPFTable table : document.getTables()) {
	            // Iterate through the rows, starting from the second row
	            for (int rowIndex = 1; rowIndex < table.getRows().size(); rowIndex++) {
	                XWPFTableRow row = table.getRows().get(rowIndex);

	                List<XWPFTableCell> cells = row.getTableCells();

	                if (cells.size() >= 3) {
	                	Long idd = (long) ((Cell) row.getCell(0)).getNumericCellValue();
	                	String name = cells.get(1).getText();
	                    String surname = cells.get(2).getText();
	                    cells.get(3).getText();

	                    if (!driverRepo.existsByIdd(idd)) {
	                        drivers.get(currentDriverIndex);
	                        createNewDriver(name, surname);
	                        currentDriverIndex++;
	                    }
	                }
	            }
	        }
	        driverRepo.saveAll(drivers);
	    }
	    
	    
}

