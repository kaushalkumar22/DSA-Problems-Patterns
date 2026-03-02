package code.aa_practice;



interface RateLimiterStrategy{
    boolean allow();
}

class SlidingWindow implements RateLimiterStrategy{

    @Override
    public boolean allow() {
        return false;
    }
}
class TokenBucket implements RateLimiterStrategy{

    private  long token;
    private int capacity;
    long currTime = System.currentTimeMillis();
    long previousTime ;
    int fillingRate;
    public TokenBucket(int capacity, int tokenRatePreSec){
        this.token = capacity;
        this.capacity = capacity;
        this.previousTime = 0;
        this.fillingRate = tokenRatePreSec;
    }
   public void refill(){
        previousTime = currTime;
        currTime = System.currentTimeMillis();
       System.out.println(token);
        long timeInSec = (currTime-previousTime)/1000 == 0 ? 1: (currTime-previousTime)/1000;
        token = Math.min(capacity,  token-(timeInSec * fillingRate));
        System.out.println(token);
   }
    @Override
    public boolean allow() {

        refill();
        if(token>=1){
            return true;
        }
        return false;
    }
}
class RateLimiter{
    Map<String , RateLimiterStrategy>
    RateLimiterStrategy rateLimiterStrategy;

    public RateLimiter(RateLimiterStrategy rateLimiterStrategy){
        this.rateLimiterStrategy = rateLimiterStrategy;
    }
    public boolean allow(){
       return rateLimiterStrategy.allow();
    }
}

public class Solution {
    public static void main(String[] args) {
        RateLimiter rt = new RateLimiter(new TokenBucket(5,2));
        System.out.println(rt.allow());

    }
}