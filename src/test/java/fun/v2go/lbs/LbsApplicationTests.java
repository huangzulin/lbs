package fun.v2go.lbs;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class LbsApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    void init() {

        redisTemplate.opsForGeo().add("lbs", new Point(114.008159, 22.64936), "address1");

        redisTemplate.opsForGeo().add("lbs", new Point(114.002913, 22.646721), "com");

        redisTemplate.opsForGeo().add("lbs", new Point(114.015476, 22.630466), "address2");

    }

    @Test
    public void test1() {
        init();
        Distance distance = redisTemplate.opsForGeo().distance("lbs", "address1", "com", RedisGeoCommands.DistanceUnit.METERS);
        assert distance.getValue() != 0;
    }

    @Test
    public void test2() {
        init();
        Distance distance = redisTemplate.opsForGeo().distance("lbs", "address1", "address2", RedisGeoCommands.DistanceUnit.METERS);
        assert distance.getValue() != 0;
    }

    @Test
    public void test3() {
        init();
        Distance distance = redisTemplate.opsForGeo().distance("lbs", "address1", "address1", RedisGeoCommands.DistanceUnit.METERS);
        assert distance.getValue() == 0;
    }


}
