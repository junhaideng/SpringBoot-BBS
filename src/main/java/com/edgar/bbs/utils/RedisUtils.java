package com.edgar.bbs.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * redis操作工具类.</br>
 * (基于RedisTemplate)
 */
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存
     */
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新缓存
     */
    public boolean getAndSet(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除缓存
     */
    public boolean delete(final String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
    加一操作
     */
    public Long incr(final String key) {
            return redisTemplate.opsForValue().increment(key);
    }

    /*
    减一操作
     */
    public boolean desc(final String key) {
        try {
            redisTemplate.opsForValue().decrement(key);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
    zset 有序集合操作
     */

    /**
     * 添加一个值
     *
     * @param key    键
     * @param score  member对应的数值
     * @param member 成员 value
     * @return 操作是否成功
     */
    public boolean zadd(final String key, Long score, String member) {
        try {
            redisTemplate.opsForZSet().add(key, member, score);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从高到底进行排序
     *
     * @param key   键
     * @param start 开始
     * @param stop  结束， -1 表示到最后
     * @return 集合
     */

    public Set<String> zrange(final String key, Long start, Long stop) {
        return redisTemplate.opsForZSet().reverseRange(key, start, stop);
    }

    /**
     * 连同 value score一起返回
     * @param key 键
     * @param start 开始
     * @param stop 结束
     * @return set 使用方式参考：
     * Iterator<ZSetOperations.TypedTuple<String>> iterator = redisUtils.zrangeWithScores("file", 0L, -1L).iterator();
     *         while (iterator.hasNext()) {
     *             ZSetOperations.TypedTuple<String> next = iterator.next();
     *             System.out.println(next.getScore());
     *             System.out.println(next.getValue());
     *         }
     */
    public  Set<ZSetOperations.TypedTuple<String>> zrangeWithScores(final String key, Long start, Long stop){
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, stop);
    }

    public boolean zincrby(final String key, final String value, Double step) {
        try {
            redisTemplate.opsForZSet().incrementScore(key, value, step);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}