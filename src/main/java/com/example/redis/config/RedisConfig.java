package com.example.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

  @Value("${spring.redis.host}")
  private String host;

  @Value("${spring.redis.port}")
  private int port;

  /**
   *  애플리케이션 클라이언트와 Redis 서버 인스턴스 간의 연결 설정을 정의하는 redis 클라이언트를 설정하는 bean
   *  과거에는 redis 클라이언트로 Jedis 를 사용했다하지만 쓰레드 안정성 관련 문제로 이젠 Lettuce 를 주로 사용한다
   * @return
   */
  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(host, port);
  }

  @Bean
  public RedisTemplate<?, ?> redisTemplate() {
    RedisTemplate<?,?> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    return redisTemplate;
  }
}
