package model.factory;
import exceptions.factoryexceptions.ConfigFileNotFound;
import exceptions.factoryexceptions.FactoryException;
import exceptions.factoryexceptions.UnknownFigureTemplate;

import java.io.IOException;

public class Factory {
    private static Factory INSTANCE;
    private static final String configFile = "config.properties";
    private static Configuration configuration;
    private Factory() throws FactoryException {
        try {
            configuration = new Configuration(configFile);
        }
        catch (IOException e) {
            throw new ConfigFileNotFound(configFile);
        }
    }

    public static Object getFigure(final int type) throws FactoryException {
        String strType = "";
        if (type == 0) {
            strType = "JFigure";
        }
        if (type == 1) {
            strType = "IFigure";
        }
        if (type == 2) {
            strType = "OFigure";
        }
        if (type == 3) {
            strType = "LFigure";
        }
        if (type == 4) {
            strType = "ZFigure";
        }
        if (type == 5) {
            strType = "TFigure";
        }
        if (type == 6) {
            strType = "SFigure";
        }
        if (configuration.getConfigurationMap().get(strType) == null) {
            throw new UnknownFigureTemplate(strType);
        }
        return configuration.getConfigurationMap().get(strType);
    }

    public static Factory getInstance() throws FactoryException {
        if (INSTANCE == null) {
            INSTANCE = new Factory();
        }
        return INSTANCE;
    }
}