package sd.oficina.order1.config;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import sd.oficina.order1.Order1Application;

import java.io.File;
import java.net.URISyntaxException;

public class PropertiesApplication {

    public static final String PROPERTIES_FILENAME = "application.properties";

    public static PropertiesConfiguration getConfig() throws ConfigurationException {
        FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                new FileBasedConfigurationBuilder<PropertiesConfiguration>(PropertiesConfiguration.class)
                        .configure(new Parameters().properties()
                                .setFileName(PROPERTIES_FILENAME)
                                .setThrowExceptionOnMissing(true)
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(';'))
                                .setIncludesAllowed(false));
        return builder.getConfiguration();
    }

}
