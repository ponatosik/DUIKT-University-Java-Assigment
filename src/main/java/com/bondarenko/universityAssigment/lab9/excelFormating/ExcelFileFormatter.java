package com.bondarenko.universityAssigment.lab9.excelFormating;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelFileFormatter {
    private final List<ExcelSheetFormatter<?>> excelSheets = new ArrayList<>();

    public <T> ExcelSheetFormatter<T> createSheet(String sheetName) {
        ExcelSheetFormatter<T> excelSheet = new ExcelSheetFormatter<>(sheetName);
        excelSheets.add(excelSheet);
        return excelSheet;
    }

    public void writeToFile(FileOutputStream file) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        for (var sheetFormatter: excelSheets) {
            Sheet sheet = workbook.createSheet(sheetFormatter.getSheetName());
            sheetFormatter.formatSheet(sheet);
        }

        workbook.write(file);
        workbook.close();
    }
}
