import java.io.*;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExelCreator {
    private final String fileName;
    private boolean alreadyExist;

    public ExelCreator(String fileName) {
        this.fileName = fileName;
    }

    public void create(List<Athlete> athletes) throws IOException {

        File file = new File(fileName + ".xlsx");
        if (file.exists()) {
            alreadyExist = true;
            return;
        }
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create a new sheet in the workbook
        XSSFSheet sheet = workbook.createSheet("Gare");

        // Create competition row
        XSSFRow row = sheet.createRow(0);

        // Create cells and insert values
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("");
        cell = row.createCell(1);
        cell.setCellValue("50 Stile Libero");
        cell = row.createCell(2);
        cell.setCellValue("50 Dorso");
        cell = row.createCell(3);
        cell.setCellValue("50 Rana");
        cell = row.createCell(4);
        cell.setCellValue("50 Farfalla");
        cell = row.createCell(5);
        cell.setCellValue("Apnea partenza 25 mt");
        cell = row.createCell(6);
        cell.setCellValue("Did. 2");
        cell = row.createCell(7);
        cell.setCellValue("Did. 3");
        cell = row.createCell(8);
        cell.setCellValue("100 Stile Libero");
        cell = row.createCell(9);
        cell.setCellValue("100 Rana");
        cell = row.createCell(10);
        cell.setCellValue("100 Dorso");
        cell = row.createCell(11);
        cell.setCellValue("100 Misti");
        cell = row.createCell(12);
        cell.setCellValue("200 Stile Libero");

        // Create athlete rows
        for (int i = 1; i <= athletes.size(); i++) {
            row = sheet.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(athletes.get(i-1).getName());
        }

        // Write the workbook to a file
        FileOutputStream out = new FileOutputStream(fileName + ".xlsx");
        workbook.write(out);
        out.close();

        // Close the workbook
        workbook.close();
        System.out.println("Succesfully create the file 'Gare.txt'");
    }

    public void insert(Athlete athlete) throws IOException {
        Workbook workbook = getWorkbook();
        Sheet sheet = workbook.getSheet("Gare");
        int row;
        for (row = 0; row < 25; row++) {
            if (sheet.getRow(row).getCell(0).getStringCellValue().equals(athlete.getName()))
                break;
        }
        final int finalRow = row;
        athlete.getCompetition().forEach(c -> {
            for (int column = 0; column < 13; column++) {
                if (sheet.getRow(0).getCell(column).getStringCellValue().equals(c.get(0))) {
                    if (sheet.getRow(finalRow).getCell(column) == null)
                        createCell(finalRow, column, sheet);
                    sheet.getRow(finalRow).getCell(column).setCellValue(
                            sheet.getRow(finalRow).getCell(column).getStringCellValue() + "\n" + c.get(1)
                    );
                }
            }
        });
        FileOutputStream fos = new FileOutputStream(fileName + ".xlsx");
        workbook.write(fos);
        workbook.close();
        fos.close();
    }

    private void createCell(int row, int column, Sheet sheet) {
        sheet.getRow(row).createCell(column);
    }

    public String checkCompetition(String competition) throws IOException {
        Workbook workbook = getWorkbook();
        Sheet sheet = workbook.getSheet("Gare");
        for (int i = 1; i < 13; i++) {
            if (competition.contains(sheet.getRow(0).getCell(i).getStringCellValue()))
                return sheet.getRow(0).getCell(i).getStringCellValue();
        }
        workbook.close();
        return "Not found";
    }

    private Workbook getWorkbook() throws IOException {
        FileInputStream inputStream = new FileInputStream( fileName + ".xlsx");
        Workbook workbook = new XSSFWorkbook(inputStream);
        inputStream.close();
        return workbook;
    }
}
