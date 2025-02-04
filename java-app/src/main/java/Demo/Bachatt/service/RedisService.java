package Demo.Bachatt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public String get(String key) {
        try {
            Object o = redisTemplate.opsForValue().get(key);
            return (String) o;
        } catch (Exception e) {
            System.out.println("Exception "+e);
            return null;
        }
    }

    public void set(String key, String value, Long ttl) {
        try {
            redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("Exception "+e);
        }
    }


}
