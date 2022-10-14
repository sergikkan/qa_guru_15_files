package org.skan;


import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class HomeWork {

    ClassLoader cl = HomeWork.class.getClassLoader();

//    @Test
//    void jsonTestWithModel() {
//        InputStream is = cl.getResourceAsStream("employee.json");
//        Gson gson = new Gson();
//        Employee employee = gson.fromJson(new InputStreamReader(is), Employee.class);
//        assertThat(Employee.name).isEqualTo("Elena");
//        assertThat(Employee.isEmployee).isTrue();
//        assertThat(Employee.organization.title).isEqualTo("VTB");
//        assertThat(Employee.organization.id).isEqualTo("1234567");
//    }

    @Test
    void zipTest() throws Exception {
        ZipFile zfile = new ZipFile(new File("src/test/resources/test.zip"));
        try(ZipInputStream zip = new ZipInputStream(cl.getResourceAsStream("test.zip"));){
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().contains("sample.pdf")) {
                    try (InputStream inputStream = zfile.getInputStream(entry)) {
                        PDF pdf = new PDF(inputStream);
                     //   assertThat(pdf.author).isEqualTo("null");
                    }
                }
            }
        }
    }
}
