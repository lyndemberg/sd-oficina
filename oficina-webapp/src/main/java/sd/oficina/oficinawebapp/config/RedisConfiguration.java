package sd.oficina.oficinawebapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    private final CacheProperties cacheProperties;

    public RedisConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Primary
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
    public <T> RedisTemplate<String, T> redisTemplateOrder() {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionOrder());
        template.setKeySerializer( new StringRedisSerializer() );
        template.setHashValueSerializer( new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer( new GenericJackson2JsonRedisSerializer());
        return template;
    }
    @Bean(name="redisTemplatePerson")
    public <T> RedisTemplate<String, T> redisTemplatePerson() {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionPerson());
        template.setKeySerializer( new StringRedisSerializer() );
        template.setHashValueSerializer( new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer( new GenericJackson2JsonRedisSerializer());
        return template;
    }
    @Bean(name="redisTemplateStore")
    public <T> RedisTemplate<String, T> redisTemplateStore() {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionStore());
        template.setKeySerializer( new StringRedisSerializer() );
        template.setHashValueSerializer( new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer( new GenericJackson2JsonRedisSerializer());
        return template;
    }
    @Bean(name="redisTemplateCustomer")
    public <T> RedisTemplate<String, T> redisTemplateCustomer() {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionCustomer());
        template.setKeySerializer( new StringRedisSerializer() );
        template.setHashValueSerializer( new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer( new GenericJackson2JsonRedisSerializer());
        return template;
    }


}
