package org.crudtest.service;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class HtmlPrintServiceTest {

    @Test
    void testPrint() {
        HtmlPrintService service = new HtmlPrintService();
        Path path = Paths.get("/Applications/Eclipse_4.7.2.app/Contents/workspace/crud-test/src/main/resources",
                "sample_result.html");

//        service.print("TRANSACTION", path);
    }

}
