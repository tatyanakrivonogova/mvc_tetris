package model.factory;
import exceptions.factoryexceptions.FigureTemplateClassNotFound;
import model.figuretemplates.FigureTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class Configuration {
    private final String configFile;
    private final Map<String, FigureTemplate> configurationMap;
    public Configuration(String _configFile) throws IOException, FigureTemplateClassNotFound {
        configFile = _configFile;
        configurationMap = new HashMap<>();
        readConfig();
    }
    public Map<String, FigureTemplate> getConfigurationMap() {
        return configurationMap;
    }
    private void readConfig() throws IOException, FigureTemplateClassNotFound {
        InputStream input = Configuration.class.getClassLoader().getResourceAsStream(configFile);
        Properties properties = new Properties();
        properties.load(input);
        for (String nameOperation : properties.stringPropertyNames()) {
            String nameClassOperation = properties.getProperty(nameOperation);
            try {
                FigureTemplate figureTemplate = (FigureTemplate) Class.forName(nameClassOperation).getDeclaredConstructor().newInstance();
                configurationMap.put(nameOperation, figureTemplate);
            }
            catch (ReflectiveOperationException e) {
                throw new FigureTemplateClassNotFound(nameClassOperation);
            }
        }
    }
}