package sd.oficina.oficinawebapp.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import sd.oficina.oficinawebapp.customer.grpc.CustomerClient;
import sd.oficina.oficinawebapp.identity.IdentityManager;
import sd.oficina.oficinawebapp.order.grpc.OrderClient;
import sd.oficina.oficinawebapp.person.grpc.PersonClient;
import sd.oficina.oficinawebapp.shared.RescueService;
import sd.oficina.oficinawebapp.store.grpc.EstoqueClient;
import sd.oficina.shared.model.store.Estoque;

@Configuration
@EntityScan("sd.oficina.shared.model")
public class BeanConfig {

    private final HostsProperties hostsProperties;

    public BeanConfig(HostsProperties hostsProperties){
        this.hostsProperties = hostsProperties;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public RescueService provideSingletonRescue(){
        return new RescueService();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public IdentityManager provideSingletonIdentityManager(){
        return new IdentityManager();
    }

    @Bean
    public OrderClient provideOrderClient(){
        return new OrderClient(hostsProperties);
    }

    @Bean
    public PersonClient providePersonClient(){
        return new PersonClient(hostsProperties);
    }

    @Bean
    public CustomerClient provideCustomerClient(){
        return new CustomerClient(hostsProperties);
    }

    @Bean
    public EstoqueClient provideStoreClient(){
        return new EstoqueClient(hostsProperties);
    }


}
