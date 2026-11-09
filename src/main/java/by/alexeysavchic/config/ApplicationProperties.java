package by.alexeysavchic.config;

import by.alexeysavchic.exception.CannotReadPropertyFileException;
import by.alexeysavchic.exception.FileDidntExistException;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ApplicationProperties {
    private final Map<String, Object> properties = init();
    private static ApplicationProperties applicationProperties;
    private final static String applicationPath="application.yaml";
    private final static String datasourcePath="datasource";

    private ApplicationProperties() {
    }

    private Map<String, Object> init() {
        Map<String, Object> properties = null;
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(applicationPath)) {
            Yaml yaml = new Yaml();
            properties = yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new FileDidntExistException("Property file doesn't exist", e, false, false);
        } catch (IOException e) {
            throw new CannotReadPropertyFileException("Cannot read property file exception", e, false, false);
        }
        return properties;
    }

    private Map<String, Object> databaseProperties() {
        Map<String, Object> databaseProperties = (Map<String, Object>) properties.get(datasourcePath);
        return databaseProperties;
    }

    public static ApplicationProperties getApplicationProperties() {
        if (applicationProperties == null) {
            applicationProperties = new ApplicationProperties();
        }
        return applicationProperties;
    }

    public String getUrl() {
        return (String) databaseProperties().get("url");
    }

    public String getUsername() {
        return (String) databaseProperties().get("username");
    }

    public Integer getMaximumPoolSize() {
        return (Integer) databaseProperties().get("maximumPoolSize");
    }

    public Integer getMinimumIdle() {
        return (Integer) databaseProperties().get("minimumIdle");
    }
}
