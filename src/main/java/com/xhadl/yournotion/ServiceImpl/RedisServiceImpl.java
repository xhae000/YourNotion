package com.xhadl.yournotion.ServiceImpl;

import com.xhadl.yournotion.Repository.SurveySeeRepository;
import com.xhadl.yournotion.Repository.UserRepository;
import com.xhadl.yournotion.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, Boolean> redisTemplate;
    @Autowired
    private SurveySeeRepository surveySeeRepository;
    @Autowired
    private UserRepository userRepository;

    public RedisServiceImpl(RedisTemplate<String, Boolean> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    private boolean canIncreaseSeeCount(String key) {
        return !Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /* 짧은 시간에 조회수를 여러번 증가시키는 걸 방지하기 위해 redis 사용*/
    @Override
    public int increaseSeeCount(int surveyId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = userRepository.getUserId(userDetails.getUsername());
        String key = userId + " " + surveyId;

        System.out.println(surveySeeRepository.getSeeCount(surveyId).toString());

        if (!canIncreaseSeeCount(key))
            return surveySeeRepository.getSeeCount(surveyId);
        System.out.println(30811);
        redisTemplate.opsForValue().set(key, true);
        // 3시간 뒤에 소멸 (3시간마다 조회수 증가시키기 가능)
        redisTemplate.expire(key, 10800L, TimeUnit.SECONDS);

        // db에 조회수를 증가시키고, 증가된 조회수 반환
        return surveySeeRepository
                .increaseSeeCount(surveyId, surveySeeRepository.getSeeCount(surveyId)+1);
    }
}
