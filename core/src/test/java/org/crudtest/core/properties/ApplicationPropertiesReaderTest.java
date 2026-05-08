package org.crudtest.core.properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ApplicationPropertiesReaderTest {

    @AfterEach
    void tearDown() {
        System.clearProperty("crud-test.properties.path");
        System.clearProperty("log.table.name");
        System.clearProperty("database.user");
    }

    /**
     * 外部ファイル・システムプロパティが未指定の場合、クラスパスの値が読み込まれること。
     */
    @Test
    void testLoadFromClasspathOnly() {
        Properties props = ApplicationPropertiesReader.load();

        assertNotNull(props.getProperty("log.table.name"));
        assertEquals("XXX_CRUD_LOG_T", props.getProperty("log.table.name"));
        assertEquals("system", props.getProperty("database.user"));
    }

    /**
     * 外部ファイルのキーがクラスパスと重複する場合、外部ファイルの値が優先されること。
     */
    @Test
    void testExternalFileOverridesClasspath() throws IOException {
        File tempFile = File.createTempFile("crud-test", ".properties");
        try {
            try (FileWriter fw = new FileWriter(tempFile)) {
                fw.write("database.user=external_user\n");
            }
            System.setProperty("crud-test.properties.path", tempFile.getAbsolutePath());

            Properties props = ApplicationPropertiesReader.load();

            assertEquals("external_user", props.getProperty("database.user"));
            // 外部ファイルに存在しないキーはクラスパスの値を保持する
            assertEquals("XXX_CRUD_LOG_T", props.getProperty("log.table.name"));
        } finally {
            tempFile.delete();
        }
    }

    /**
     * システムプロパティは外部ファイル・クラスパスより優先されること。
     */
    @Test
    void testSystemPropertyOverridesAll() throws IOException {
        File tempFile = File.createTempFile("crud-test", ".properties");
        try {
            try (FileWriter fw = new FileWriter(tempFile)) {
                fw.write("database.user=external_user\n");
            }
            System.setProperty("crud-test.properties.path", tempFile.getAbsolutePath());
            System.setProperty("database.user", "system_user");

            Properties props = ApplicationPropertiesReader.load();

            assertEquals("system_user", props.getProperty("database.user"));
        } finally {
            tempFile.delete();
        }
    }

    /**
     * システムプロパティがクラスパスのキーを直接上書きできること（外部ファイル不使用）。
     */
    @Test
    void testSystemPropertyOverridesClasspath() {
        System.setProperty("log.table.name", "OVERRIDE_LOG_T");

        Properties props = ApplicationPropertiesReader.load();

        assertEquals("OVERRIDE_LOG_T", props.getProperty("log.table.name"));
    }
}
