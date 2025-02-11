package com.tarapaca.api.users.infrastructure.helpers;

import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic.DeliveryZoneBasicData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.document_type.DocumentTypeData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.membership.MembershipData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodData;
import com.tarapaca.api.product.infrastructure.repository.product.ProductData;
import com.tarapaca.api.product.infrastructure.repository.product_category.ProductCategoryData;
import com.tarapaca.api.product.infrastructure.repository.product_type.ProductTypeData;
import com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.status.StatusData;
import com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.office.OfficeData;
import com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.role.RoleData;
import com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.user.UserData;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.tarapaca.api.shared.Globals.EXCEL_TYPE;

@Component
@AllArgsConstructor
public class ExcelHelper {


    public boolean hasExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();

        return contentType != null && contentType.equals(EXCEL_TYPE);
    }

    public HashMap<Integer, String> mapColumns(Row row) {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        Iterator<Cell> cellsInRow = row.iterator();
        while (cellsInRow.hasNext()) {
            Cell currentCell = cellsInRow.next();
            int cellIdx = currentCell.getColumnIndex();
            if (getValue(currentCell) == null) {
                continue;
            }
            String cellValue = getValue(currentCell).toString();
            cellValue = StringUtils.capitalize(cellValue);
            map.put(cellIdx, cellValue);
        }

        return map;
    }

    public String getCleanCellValue(Cell cell, boolean toUpperCase) {
        Object value = getValue(cell);
        String cellValue = value != null ? value.toString().trim() : "";
        cellValue = cellValue.replaceAll("\\s+", " ");
        if (toUpperCase) {
            cellValue = cellValue.toUpperCase();
        }
        return cellValue;
    }

    public String getCleanEmailAddress(String emailAddress) {
        return emailAddress.replaceAll("\\s+", "").toLowerCase().trim();
    }

    private Object getValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return getCellValueAsString(cell);
//              return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case ERROR:
                return null;
//                return cell.getErrorCellValue();
            case FORMULA:
                switch (cell.getCachedFormulaResultType()) {
                    case BOOLEAN:
                        return cell.getBooleanCellValue();
                    case NUMERIC:
                        return getCellValueAsString(cell);
//                        return cell.getNumericCellValue();
                    case STRING:
                        return cell.getStringCellValue();
                    case ERROR:
                        return null;
//                        return cell.getErrorCellValue();
                    default:
                        return cell.getCellFormula();
                }
            case BLANK:
                return null;
            case _NONE:
                return null;
            default:
                return null;
        }
    }

    private String getCellValueAsString(Cell cell) {
        DataFormatter formatter = new DataFormatter();
        cell.setCellType(CellType.STRING);

        return formatter.formatCellValue(cell);
    }

    public int getNumberOfNonEmptyCells(Sheet sheet, int columnIndex) {
        int numOfNonEmptyCells = 0;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(columnIndex);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    numOfNonEmptyCells++;
                }
            }
        }

        return numOfNonEmptyCells;
    }

    public HashMap<String, DocumentTypeData> mapDocumentTypes(List<DocumentTypeData> documentTypes) {
        HashMap<String, DocumentTypeData> map = new HashMap<String, DocumentTypeData>();
        for (DocumentTypeData documentType : documentTypes) {
            map.put(documentType.getName(), documentType);
        }

        return map;
    }

    public HashMap<String, NeighborhoodData> mapNeighborhoods(List<NeighborhoodData> neighborhoodList) {
        HashMap<String, NeighborhoodData> map = new HashMap<String, NeighborhoodData>();
        for (NeighborhoodData neighborhood : neighborhoodList) {
            map.put(neighborhood.getName(), neighborhood);
        }

        return map;
    }

    public HashMap<String, CityBasicData> mapCities(List<CityBasicData> cityList) {
        HashMap<String, CityBasicData> map = new HashMap<String, CityBasicData>();
        for (CityBasicData city : cityList) {
            map.put(city.getName(), city);
        }

        return map;
    }

    public HashMap<String, DeliveryZoneBasicData> mapDeliveryZones(List<DeliveryZoneBasicData> deliveryZoneList) {
        HashMap<String, DeliveryZoneBasicData> map = new HashMap<String, DeliveryZoneBasicData>();
        for (DeliveryZoneBasicData deliveryZone : deliveryZoneList) {
            map.put(deliveryZone.getName(), deliveryZone);
        }

        return map;
    }

    public HashMap<String, MembershipData> mapMemberships(List<MembershipData> membershipList) {
        HashMap<String, MembershipData> map = new HashMap<String, MembershipData>();
        for (MembershipData membership : membershipList) {
            map.put(membership.getName().toString(), membership);
        }

        return map;
    }

    public HashMap<String, CustomerData> mapCustomers(List<CustomerData> customerList) {
        HashMap<String, CustomerData> map = new HashMap<String, CustomerData>();
        for (CustomerData customer : customerList) {
            map.put(customer.getDocumentNumber(), customer);
        }

        return map;
    }

    public HashMap<String, ProductData> mapProducts(List<ProductData> productList) {
        HashMap<String, ProductData> map = new HashMap<String, ProductData>();
        for (ProductData product : productList) {
            map.put(product.getReference(), product);
        }

        return map;
    }

    public HashMap<String, ProductTypeData> mapProductTypes(List<ProductTypeData> productTypeList) {
        HashMap<String, ProductTypeData> map = new HashMap<String, ProductTypeData>();
        for (ProductTypeData productType : productTypeList) {
            map.put(productType.getName(), productType);
        }

        return map;
    }

    public HashMap<String, ProductCategoryData> mapProductCategories(List<ProductCategoryData> productCategoryList) {
        HashMap<String, ProductCategoryData> map = new HashMap<String, ProductCategoryData>();
        for (ProductCategoryData productCategory : productCategoryList) {
            map.put(productCategory.getName(), productCategory);
        }

        return map;
    }

    public HashMap<String, StatusData> mapStatuses(List<StatusData> statusList) {
        HashMap<String, StatusData> map = new HashMap<String, StatusData>();
        for (StatusData status : statusList) {
            map.put(status.getName().toString(), status);
        }

        return map;
    }

    public HashMap<String, UserData> mapUsers(List<UserData> userList) {
        HashMap<String, UserData> map = new HashMap<String, UserData>();
        for (UserData user : userList) {
            map.put(user.getUsername(), user);
        }

        return map;
    }

    public HashMap<String, RoleData> mapRoles(List<RoleData> roleList) {
        HashMap<String, RoleData> map = new HashMap<String, RoleData>();
        for (RoleData role : roleList) {
            map.put(role.getName().toString(), role);
        }

        return map;
    }

    public HashMap<String, OfficeData> mapOffices(List<OfficeData> officeList) {
        HashMap<String, OfficeData> map = new HashMap<String, OfficeData>();
        for (OfficeData office : officeList) {
            map.put(office.getName().toString(), office);
        }

        return map;
    }

    public Integer getIndexColumn(String columnName, HashMap<Integer, String> mapColumns) {
        for (Integer key : mapColumns.keySet()) {
            if (mapColumns.get(key).equals(columnName)) {
                return key;
            }
        }

        return -1; // Column not found. Default value.
    }
}
