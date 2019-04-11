package sd.oficina.oficinawebapp.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import sd.oficina.oficinawebapp.identity.IdentityManager;
import sd.oficina.oficinawebapp.shared.RescueService;

@Configuration
public class BeanConfig {

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

}
