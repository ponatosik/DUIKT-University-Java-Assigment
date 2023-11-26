package com.bondarenko.universityAssigment.lab9.excelFormating;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Setter
public class ExcelSheetFormatter<T> {
    @Getter
    private String sheetName;
    private List<Object> headers;
    private List<T> rows;
    private Function<T, List<Object>> columnMapper;

    public ExcelSheetFormatter(String sheetName) {
        this.sheetName = sheetName;
        this.headers = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.columnMapper = obj -> List.of(obj.toString());
    }

    public void formatSheet(Sheet sheet) {
        int i = 0;

        Row headerRow = sheet.createRow(i++);
        fillRow(headerRow, headers);

        for (T rowData : rows) {
            Row row = sheet.createRow(i++);
            fillRow(row, columnMapper.apply(rowData));
        }

        autoSizeColumns(sheet, headers.size());
    }

    private void fillRow(Row row, List<Object> rowData) {
        int i = 0;
        for (Object cellData : rowData) {
            Cell cell = row.createCell(i++);

            if (cellData instanceof String) {
                cell.setCellValue((String) cellData);
            } else if (cellData instanceof Number) {
                cell.setCellValue(((Number) cellData).doubleValue());
            }
        }
    }

    private void autoSizeColumns(Sheet sheet, int columns) {
        for (int i = 0; i < columns; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}
