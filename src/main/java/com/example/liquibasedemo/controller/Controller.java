package com.example.liquibasedemo.controller;

import com.example.liquibasedemo.domain.BulkOperation;
import com.example.liquibasedemo.ExcelExporter;
import com.example.liquibasedemo.service.PersonService;
import com.example.liquibasedemo.domain.Person;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "person", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controller {

    private final PersonService personService;

    public Controller( PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/bulk-create")
    public ResponseEntity<BulkOperation> addPersonsBulk(MultipartFile file) {
        if (file.isEmpty()) throw new RuntimeException("Empty file received");
        return ResponseEntity.ok(personService.bulkAdd(file));
    }

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        String fileName = "person_"+currentDateTime+".xlsx";
        String headerValue = "attachement; filename="+fileName;
        response.setHeader(headerKey,headerValue);
        List<Person> persons = personService.listAll();
        ExcelExporter excelExporter = new ExcelExporter(persons);
        excelExporter.export(response);
    }
}
