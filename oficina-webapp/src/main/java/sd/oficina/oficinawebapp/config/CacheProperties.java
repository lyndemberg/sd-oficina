package sd.oficina.oficinawebapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheProperties {

    @Value("${cache.order.host}")
    private String cacheOrderHost;
    @Value("${cache.order.port}")
    private int cacheOrderPort;

    @Value("${cache.store.host}")
    private String cacheStoreHost;
    @Value("${cache.store.port}")
    private int cacheStorePort;

    @Value("${cache.person.host}")
    private String cachePersonHost;
    @Value("${cache.person.port}")
    private int cachePersonPort;

    @Value("${cache.customer.host}")
    private String cacheCustomerHost;
    @Value("${cache.customer.port}")
    private int cacheCustomerPort;


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

    public String getCacheStoreHost() {
        return cacheStoreHost;
    }

    public void setCacheStoreHost(String cacheStoreHost) {
        this.cacheStoreHost = cacheStoreHost;
    }

    public int getCacheStorePort() {
        return cacheStorePort;
    }

    public void setCacheStorePort(int cacheStorePort) {
        this.cacheStorePort = cacheStorePort;
    }

    public String getCachePersonHost() {
        return cachePersonHost;
    }

    public void setCachePersonHost(String cachePersonHost) {
        this.cachePersonHost = cachePersonHost;
    }

    public int getCachePersonPort() {
        return cachePersonPort;
    }

    public void setCachePersonPort(int cachePersonPort) {
        this.cachePersonPort = cachePersonPort;
    }

    public String getCacheCustomerHost() {
        return cacheCustomerHost;
    }

    public void setCacheCustomerHost(String cacheCustomerHost) {
        this.cacheCustomerHost = cacheCustomerHost;
    }

    public int getCacheCustomerPort() {
        return cacheCustomerPort;
    }

    public void setCacheCustomerPort(int cacheCustomerPort) {
        this.cacheCustomerPort = cacheCustomerPort;
    }
}
