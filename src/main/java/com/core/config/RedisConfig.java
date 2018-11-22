package com.core.config;

import com.core.redis.RedisMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * @Description:
 * redis配置
 */
@Configuration
@EnableCaching(mode=AdviceMode.PROXY)
public class RedisConfig {
    @Autowired
    RedisProperties redisProperties;

    @Bean
    public CacheManager cacheManager(JedisConnectionFactory jedisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)); // 设置缓存有效期一小时
        redisCacheConfiguration=redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));//设置值的缓存格式
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(jedisConnectionFactory)).cacheDefaults(redisCacheConfiguration).build();
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());//键
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());//值
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
       JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();
       JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
       jedisPoolConfig.setMaxWaitMillis(redisProperties.getJedis().getPool().getMaxWait().toMillis());
       jedisPoolConfig.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
       jedisPoolConfig.setMinIdle(redisProperties.getJedis().getPool().getMinIdle());
       jedisPoolConfig.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive());
       jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
       jedisConnectionFactory.setHostName(redisProperties.getHost());
       jedisConnectionFactory.setPort(redisProperties.getPort());
       jedisConnectionFactory.setPassword(redisProperties.getPassword());
       jedisConnectionFactory.setDatabase(redisProperties.getDatabase());
        jedisConnectionFactory.setTimeout(Integer.parseInt(String.valueOf(redisProperties.getTimeout().toMillis())));
       return jedisConnectionFactory;
    }

    //消息监听器
    @Bean
    RedisMessageListenerContainer container(JedisConnectionFactory jedisConnectionFactory, RedisMessageListener redisMessageListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);
        container.addMessageListener(redisMessageListener, new PatternTopic("*"));
        return container;
    }
}