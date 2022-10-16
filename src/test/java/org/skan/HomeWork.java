package org.skan;


import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import com.opencsv.CSVReader;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skan.model.WorkTeam;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class HomeWork {

    ClassLoader cl = HomeWork.class.getClassLoader();

    @DisplayName("parsing json file with Jackson")
    @Test
    void jsonTest() throws Exception {
        File file = new File("src/test/resources/team.json");
        ObjectMapper objectMapper = new ObjectMapper();
        WorkTeam workTeam = objectMapper.readValue(file, WorkTeam.class);
        assertThat(workTeam.QA).isEqualTo("Serhii");
        assertThat(workTeam.Backend.get(0)).isEqualTo("Alex");
        assertThat(workTeam.projects.banking).isEqualTo("mobile");
    }

    @DisplayName("Parsing zip file")
    @Test
    void zipTest() throws Exception {
        ZipFile zfile = new ZipFile(new File("src/test/resources/test.zip"));
        try(ZipInputStream zip = new ZipInputStream(cl.getResourceAsStream("test.zip"));){
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().contains("sample.pdf")) {
                    try (InputStream inputStream = zfile.getInputStream(entry)) {
                        PDF pdf = new PDF(inputStream);
                        assertThat(pdf.text).contains("A Simple PDF File");
                    }
                }
                if (entry.getName().contains("file_example.xls")) {
                    try (InputStream inputStream = zfile.getInputStream(entry)) {
                        XLS xls = new XLS(inputStream);
                        assertThat(xls.excel.getSheetAt(0)
                                .getRow(1).getCell(1)
                                .getStringCellValue())
                                .isEqualTo("Dulce");
                    }
                }
                if (entry.getName().contains("qa_guru.csv")) {
                    try (InputStream inputStream = zfile.getInputStream(entry)) {
                        CSVReader csv = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                        List<String[]> content =csv.readAll();
                        String[] row = content.get(1);
                        assertThat(row[0]).isEqualTo("LastName");
                    }
                }
            }
        }
    }
}
