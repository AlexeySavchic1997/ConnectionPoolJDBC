package by.alexeysavchic.config;

import by.alexeysavchic.exception.CannotReadPropertyFileException;
import by.alexeysavchic.exception.ErrorMessages;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.io.InputStream;

public class ApplicationProperties {
    private static ApplicationProperties applicationProperties;
    private final static String applicationPath = System.getProperty("app.properties");
    private final JsonNode properties = init();

    private ApplicationProperties() {
    }

    public static ApplicationProperties getApplicationProperties() {
        if (applicationProperties == null) {
            applicationProperties = new ApplicationProperties();
        }
        return applicationProperties;
    }

    private JsonNode init() {
        JsonNode properties;
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(applicationPath)) {
            YAMLMapper yamlMapper = new YAMLMapper();
            properties = yamlMapper.readTree(inputStream);
        } catch (IOException e) {
            throw new CannotReadPropertyFileException(ErrorMessages.cannotReadPropertyFile, e, false, false);
        }
        return properties;
    }

    public JsonNode databaseProperties() {
        return properties.path("datasource");
    }
}
