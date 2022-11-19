package com.example.redis.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class CacheConfig {

  private final RedisConnectionFactory redisConnectionFactory;

  /**
   * setKeySerializer, setValueSerializer 설정해주는 이유는 Spring - Redis 간
   * 데이터 직렬화, 역직렬화 시 사용하는 방식이 JdkSerializationRedisSerializer 직렬화 방식이기 때문입니다.
   * 캐싱할 API의 응답값 데이터 타입이 String 타입이라면 jdk 직렬화 방식도 가능하지만
   * 대부분의 반환값 타입은 Json 등의 다른 데이터 타입일텐데 이때 jdk 직렬화 방식으로는 Serializer 에러가 발생합니다.
   * 동작에는 문제가 없지만 redis-cli을 통해 직접 데이터를 보려고 할 때 알아볼 수 없는 형태로 출력됩니다.
   * 따라서 설정에서 json 포맷 지원을 위한 Jackson2JsonRedisSerializer 또는 GenericJackson2JsonRedisSerializer 로 변경을 합니다.
   *
   * @return
   */
  @Bean
  public CacheManager cacheManager() {
    RedisCacheConfiguration redisConfiguration = RedisCacheConfiguration.defaultCacheConfig()
      .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
      .serializeValuesWith(
        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
//      .prefixCacheNameWith("data") // 키값의 prefix
      .entryTtl(Duration.ofSeconds(120)) // time-to-live 120초
      .disableCachingNullValues(); // 널값 금지(캐싱시 unless("#result == null") 필수

    return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory) // redis connection으로 Lettuce를 이용
      .cacheDefaults(redisConfiguration).build();
  }
}