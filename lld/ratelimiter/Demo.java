
package lld.ratelimiter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.function.Supplier;

//Core Interfaces
interface RateLimiterStrategy {
    boolean allow();
}

//ONE Algorithm to Implement (Token Bucket)
//This is the best one to code in interviews
//(others explained verbally)
class TokenBucketStrategy implements RateLimiterStrategy {
    private final int capacity;
    private final int refillRatePerSec;
    private double tokens;
    private long lastRefillTime;

    public TokenBucketStrategy(int capacity, int refillRatePerSec) {
        this.capacity = capacity;
        this.refillRatePerSec = refillRatePerSec;
        this.tokens = capacity;
        this.lastRefillTime = System.currentTimeMillis();
    }

    private void refill() {
        long now = System.currentTimeMillis();
        double tokensToAdd = (now - lastRefillTime) / 1000.0 * refillRatePerSec;
        tokens = Math.min(capacity, tokens + tokensToAdd);
        lastRefillTime = now;
    }
    @Override
    public synchronized boolean allow() {
        refill();
        if (tokens >= 1) {
            tokens--;
            return true;
        }
        return false;
    }
}

//RateLimiter (Facade)
class RateLimiter {
    private final Map<String, RateLimiterStrategy> userLimiters = new ConcurrentHashMap<>();
    private final Supplier<RateLimiterStrategy> strategySupplier;

    public RateLimiter(Supplier<RateLimiterStrategy> strategySupplier) {
        this.strategySupplier = strategySupplier;
    }

    public boolean allowRequest(String userId) {
        RateLimiterStrategy limiter =
                userLimiters.computeIfAbsent(userId, id -> strategySupplier.get());
        return limiter.allow();
    }
}


//Demo Usage
public class Demo {
    public static void main(String[] args) throws InterruptedException {

        RateLimiter limiter =
                new RateLimiter(() -> new TokenBucketStrategy(5, 1));

        String user = "user-1";

        for (int i = 0; i < 10; i++) {
            System.out.println(
                    "Request " + i + " allowed: " + limiter.allowRequest(user)
            );
            Thread.sleep(300);
        }
    }
}


