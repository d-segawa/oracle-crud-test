package org.crudtest.core.constant;

import static org.junit.jupiter.api.Assertions.*;

import org.crudtest.core.properties.ApplicationProperties;
import org.junit.jupiter.api.Test;

class ApplicationPropertiesTest {

    @Test
    void test() {
        String value = ApplicationProperties.LOG_TABLE_NAME.getValue();
        assertNotNull(value);
    }

}
