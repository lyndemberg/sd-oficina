package sd.oficina.oficinawebapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

    private final CacheProperties cacheProperties;

    public RedisConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean(name = "redisOrderService")
    public RedisConnectionFactory redisConnectionOrder() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setHostName(cacheProperties.getCacheOrderHost());
        jedisConFactory.setPort(cacheProperties.getCacheOrderPort());
        return jedisConFactory;
    }

    @Bean(name = "redisPersonService")
    public RedisConnectionFactory redisConnectionPerson() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setHostName(cacheProperties.getCachePersonHost());
        jedisConFactory.setPort(cacheProperties.getCachePersonPort());
        return jedisConFactory;
    }

    @Bean(name = "redisStoreService")
    public RedisConnectionFactory redisConnectionStore() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setHostName(cacheProperties.getCacheStoreHost());
        jedisConFactory.setPort(cacheProperties.getCacheStorePort());
        return jedisConFactory;
    }

    @Bean(name = "redisCustomerService")
    public RedisConnectionFactory redisConnectionCustomer() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setHostName(cacheProperties.getCacheCustomerHost());
        jedisConFactory.setPort(cacheProperties.getCacheCustomerPort());
        return jedisConFactory;
    }

    @Bean(name="redisTemplateOrder")
    public RedisTemplate<Long, Object> redisTemplateOrder() {
        RedisTemplate<Long, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionOrder());
        return template;
    }
    @Bean(name="redisTemplatePerson")
    public RedisTemplate<Long, Object> redisTemplatePerson() {
        RedisTemplate<Long, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionPerson());
        return template;
    }
    @Bean(name="redisTemplateStore")
    public RedisTemplate<Long, Object> redisTemplateStore() {
        RedisTemplate<Long, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionStore());
        template.
        return template;
    }
    @Bean(name="redisTemplateCustomer")
    public RedisTemplate<Long, Object> redisTemplateCustomer() {
        RedisTemplate<Long, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionCustomer());
        return template;
    }


}
