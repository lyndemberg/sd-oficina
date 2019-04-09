package sd.oficina.oficinawebapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {

    @Value("${cache.order.host}")
    private String cacheOrderHost;
    @Value("${cache.order.port}")
    private int cacheOrderPort;


    public String getCacheOrderHost() {
        return cacheOrderHost;
    }

    public void setCacheOrderHost(String cacheOrderHost) {
        this.cacheOrderHost = cacheOrderHost;
    }

    public int getCacheOrderPort() {
        return cacheOrderPort;
    }

    public void setCacheOrderPort(int cacheOrderPort) {
        this.cacheOrderPort = cacheOrderPort;
    }
}
