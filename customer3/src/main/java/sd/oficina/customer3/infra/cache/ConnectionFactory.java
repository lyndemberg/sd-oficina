package sd.oficina.customer3.infra.cache;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class ConnectionFactory {

    private static RedisConnectionFactory getConnectionRedis() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();

        jedisConFactory.setHostName("cache-customer");
        jedisConFactory.setPort(6379);

        jedisConFactory.afterPropertiesSet();

        return jedisConFactory;
    }

    public static <T> RedisTemplate<String, T> getRedisTemplate() {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(getConnectionRedis());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

}
