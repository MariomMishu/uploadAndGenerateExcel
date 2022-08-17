package com.example.liquibasedemo;

import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Date of creation 01-Oct-2020
 *
 * @author Mariom
 * @since 0.0.1
 */
@Service
@Slf4j
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public BulkOperation bulkAdd(MultipartFile file) {

        var operation = this.processFile(file);
        int count = 0;
        try {
            for (var request : operation.getPersonList()) {
                try {
                    var entity = new PersonEntity();
                    BeanUtils.copyProperties(request, entity);
                    personRepository.save(entity);
                    count++;
                } catch (Exception ex) {
                }
            }
        } catch (Exception e) {
            log.error("Exception in adding person", e);
        }
        var message =
                operation.isFailed()
                        ? operation.getMessage()
                        : count > 0 ? "Total " + count + " person added." : "No person added";
        return operation.setMessage(message).setPersonList(null);
    }

    public List<Person> listAll() {
        var entity = personRepository.findAll();
        return entity.stream().map(
                e -> {
                    var domain = new Person();
                    BeanUtils.copyProperties(e, domain);
                    return domain;
                }).collect(Collectors.toList());

    }

    private BulkOperation processFile(MultipartFile file) {
        List<Person> personRequests = new ArrayList<>();
        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException("file is.not.valid");
        }
        XSSFSheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        var errorResponseMessage = new StringBuilder();
        boolean isFailed = false;
        for (int row = sheet.getFirstRowNum() + 1; row <= sheet.getLastRowNum(); row++) {
            var person = new Person();

            Row ro = sheet.getRow(row);
            try {
                person.setFirstName(this.getCellValueIfValid(sheet, ro.getCell(0), 0));
                person.setLastName(
                        ro.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());

                person.setStreet1(
                        ro.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                person.setPhoneNumber1(this.getCellValueIfValid(sheet, ro.getCell(4), 4));
                person.setPhoneNumber2(this.getCellValueIfValid(sheet, ro.getCell(5), 5));

                personRequests.add(person);
            } catch (Exception e) {
                isFailed = true;
                var rowNumber = "Row: " + row;
                errorResponseMessage.append(rowNumber).append(", ").append(e.getMessage()).append(". ");
                log.error(e.getMessage());
            }
        }
        return new BulkOperation()
                .setPersonList(personRequests)
                .setMessage(errorResponseMessage.toString())
                .setFailed(isFailed);
    }

    private String getCellValueIfValid(XSSFSheet sheet, Cell ce, int column) {
        DataFormatter dataFormatter = new DataFormatter();
        String columnName = sheet.getRow(0).getCell(column).getRichStringCellValue().getString();

        if (StringUtils.isBlank(dataFormatter.formatCellValue(ce)))
            throw new RuntimeException(
                    columnName + ": " + "not empty");
        if (ce.getCellType().equals(CellType.FORMULA)) {
            return ce.getRichStringCellValue().getString();
        }
        return dataFormatter.formatCellValue(ce);
    }
}
