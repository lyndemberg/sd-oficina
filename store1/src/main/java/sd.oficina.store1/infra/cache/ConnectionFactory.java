package sd.oficina.store1.infra.cache;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import sd.oficina.store1.infra.config.PropertiesApplication;

public class ConnectionFactory {

    private static RedisConnectionFactory getConnectionRedis() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        try {
            jedisConFactory.setHostName(PropertiesApplication.getConfig().getString("cache.store.host"));
            jedisConFactory.setPort(PropertiesApplication.getConfig().getInt("cache.store.port"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return jedisConFactory;
    }

    public static <T> RedisTemplate<String, T> getRedisTemplate() {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(getConnectionRedis());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

}
