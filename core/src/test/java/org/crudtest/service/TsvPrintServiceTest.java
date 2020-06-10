package org.crudtest.service;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class TsvPrintServiceTest {

    @Test
    void testPrint() {
        TsvPrintService service = new TsvPrintService();
        Path path = Paths.get("/Applications/Eclipse_4.7.2.app/Contents/workspace/crud-test/src/main/resources",
                "sample_result.tsv");
        service.print("TRANSACTION", path);
    }

}
