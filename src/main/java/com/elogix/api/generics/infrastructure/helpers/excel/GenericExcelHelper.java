package com.elogix.api.generics.infrastructure.helpers.excel;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.users.domain.model.UserBasic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GenericExcelHelper<T> {
  protected final ExcelHelper excelHelper;
  protected final UpdateUtils updateUtils;

  protected GenericExcelHelper(ExcelHelper excelHelper, UpdateUtils updateUtils) {
    this.excelHelper = excelHelper;
    this.updateUtils = updateUtils;
  }

  protected Map<Integer, String> getColumnMap(Iterator<Row> rows) {
    return excelHelper.mapColumns(rows.next());
  }

  protected UserBasic getCurrentUser() {
    return updateUtils.getCurrentUser();
  }

  protected abstract T createNewEntity(UserBasic currentUser);

  protected abstract void validateRequiredColumns(Map<Integer, String> mapColumns, Set<String> errors);

  protected abstract T processCell(int cellIdx, Cell cell, T entity, Set<String> errors,
      Map<Integer, String> mapColumns);
}
