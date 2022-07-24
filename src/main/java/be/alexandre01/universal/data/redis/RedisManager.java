package be.alexandre01.universal.data.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisManager {

    private static JedisPool pool;

    public static void init() {
        pool = new JedisPool("127.0.0.1", 6379);
    }

    public static void close() {
        pool.close();
    }

    public static void set(String key, String value) {
        Jedis j = null;
        try {
            j = pool.getResource();
            j.auth("bqN4PUu6526G8SVvZ5g86mSbN62b6H4t3CD4fqpPrwJ5mj4MVi");
            j.set(key, value);
        } finally {
            j.close();
        }
    }

    public static void del(String key) {
        Jedis j = null;
        try {
            j = pool.getResource();
            j.auth("bqN4PUu6526G8SVvZ5g86mSbN62b6H4t3CD4fqpPrwJ5mj4MVi");
            j.del(key);
        } finally {
            j.close();
        }
    }

    public static String get(String key) {
        Jedis j = null;
        try {
            j = pool.getResource();
            j.auth("bqN4PUu6526G8SVvZ5g86mSbN62b6H4t3CD4fqpPrwJ5mj4MVi");
            return j.get(key);
        } finally {
            j.close();
        }
    }
}
