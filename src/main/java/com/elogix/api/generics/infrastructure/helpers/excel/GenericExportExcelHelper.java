package com.elogix.api.generics.infrastructure.helpers.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.elogix.api.generics.domain.model.GenericEntity;
import com.elogix.api.generics.infrastructure.exception.ExcelProcessingException;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@NoArgsConstructor
public abstract class GenericExportExcelHelper<T extends GenericEntity> {

  public ByteArrayInputStream entitiesToExcel(List<T> entities) {
    try (Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {

      Sheet sheet = workbook.createSheet(getSheetName());

      // Write header
      Row headerRow = sheet.createRow(0);
      String[] headers = getHeaders();
      for (int i = 0; i < headers.length; i++) {
        Cell cell = headerRow.createCell(i);
        cell.setCellValue(headers[i]);
      }

      // Write data
      int rowIdx = 1;
      for (T entity : entities) {
        Row row = sheet.createRow(rowIdx++);
        writeEntityToRow(entity, row);
      }

      // Auto size columns
      for (int i = 0; i < headers.length; i++) {
        sheet.autoSizeColumn(i);
      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());

    } catch (IOException e) {
      log.error("Error creating excel file", e);
      throw new ExcelProcessingException("Failed to export data to Excel", e);
    }
  }

  protected abstract String getSheetName();

  protected abstract String[] getHeaders();

  protected abstract void writeEntityToRow(T entity, Row row);
}
