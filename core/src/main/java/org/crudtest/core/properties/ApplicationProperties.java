package org.crudtest.core.properties;

public enum ApplicationProperties {

    LOG_SEQ_NAME("log.seq.name"), LOG_TABLE_NAME("log.table.name"), TRIGGER_NAME_PRIFIX(
            "trigger.name.prifix"), DB_URL("database.url"), DB_USER(
                    "database.user"), DB_PASS(
                            "database.pass"), MANAGE_TABLE_NAME(
                                    "manage.table.name"), LOG_PATH("logfile.path"), LOG_LEVEL("log.level"),DB_CHARSET("database.charset");

    private String name;

    private ApplicationProperties(String name) {
        this.name = name;
    }

    public String getValue() {
        return ApplicationPropertiesReader.prop.getProperty(name);
    }

}
