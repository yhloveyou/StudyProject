package cn.zz.Study.common;

import lombok.Data;

/**
 * @author admin
 * redis 统一key 前缀
 */
public class RedisPrefixKey {

    /**
     * 登录Token
     */
    public static final RedisNeed LOGIN_TOKEN = new RedisNeed("LoginTK:", 30 * 30 * 30);

    /**
     * 登录次数
     */
    public static final RedisNeed LOGIN_NUM = new RedisNeed("LoginNum:", 30 * 30 * 30);

    /**
     * 内部类 为 RedisPrefixKey提供
     */
    @Data
    public static class RedisNeed {
        //key
        private String key;

        //过期时间
        private Integer expiredTime;

        public RedisNeed(String key, Integer expiredTime) {
            this.key = key;
            this.expiredTime = expiredTime;
        }

        public RedisNeed keyAppend(Object key) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.key).append(key);
            return new RedisNeed(sb.toString(), expiredTime);
        }

        public String getKey() {
            return this.key;
        }

    }
}
