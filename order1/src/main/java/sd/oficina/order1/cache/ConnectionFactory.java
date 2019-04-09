package sd.oficina.order1.cache;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import sd.oficina.order1.config.PropertiesApplication;

public class ConnectionFactory {

    private static RedisConnectionFactory getConnectionRedis(){
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        try {
            jedisConFactory.setHostName(PropertiesApplication.getConfig().getString("cache.order.host"));
            jedisConFactory.setPort(PropertiesApplication.getConfig().getInt("cache.order.port"));
            return jedisConFactory;
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RedisTemplate<String, Object> getRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(getConnectionRedis());
        template.setKeySerializer( new StringRedisSerializer() );
        template.setHashValueSerializer( new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer( new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
