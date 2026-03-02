package lld.urlshortner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/* ---------------- Base62 Encoder ---------------- */
class Base62Encoder {
    private static final String CHARS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String encode(long num) {
        StringBuilder code = new StringBuilder();
        while (num > 0) {
            code.append(CHARS.charAt((int) (num % 62)));
            num /= 62;
        }
        return code.reverse().toString();
    }
}

/* ---------------- ID Generator ---------------- */
class IdGenerator {
    // Ensures minimum 7-char Base62 output
    private static final long START_ID = 62L * 62 * 62 * 62 * 62 * 62;
    private final AtomicLong counter = new AtomicLong(START_ID);

    public long nextId() {
        return counter.incrementAndGet();
    }
}

/* ---------------- URL Shortener ---------------- */
class UrlShortener {

    private final IdGenerator idGenerator = new IdGenerator();
    private final Base62Encoder encoder = new Base62Encoder();

    // longUrl -> shortCode (deduplication)
    private final Map<String, String> longToShort = new ConcurrentHashMap<>();

    // shortCode -> longUrl (redirect)
    private final Map<String, String> shortToLong = new ConcurrentHashMap<>();

    public String shorten(String longUrl) {

        if (longUrl == null || longUrl.isBlank()) {
            throw new IllegalArgumentException("Invalid URL");
        }

        // Deduplication
        if (longToShort.containsKey(longUrl)) {
            return longToShort.get(longUrl);
        }

        String shortCode;
        do {
            shortCode = encoder.encode(idGenerator.nextId());
        } while (shortToLong.containsKey(shortCode)); // collision safety

        longToShort.put(longUrl, shortCode);
        shortToLong.put(shortCode, longUrl);

        return shortCode;
    }

    public String shorten(String longUrl, String customAlias) {

        if (customAlias == null || customAlias.isBlank()) {
            throw new IllegalArgumentException("Invalid custom alias");
        }

        if (shortToLong.containsKey(customAlias)) {
            throw new IllegalArgumentException("Alias already exists");
        }

        // Dedup check
        if (longToShort.containsKey(longUrl)) {
            return longToShort.get(longUrl);
        }

        longToShort.put(longUrl, customAlias);
        shortToLong.put(customAlias, longUrl);

        return customAlias;
    }

    public String redirect(String shortCode) {
        String longUrl = shortToLong.get(shortCode);
        if (longUrl == null) {
            throw new IllegalArgumentException("Short URL not found");
        }
        return longUrl;
    }
}

/* ---------------- Demo ---------------- */
public class Demo {
    public static void main(String[] args) {

        UrlShortener shortener = new UrlShortener();

        String s1 = shortener.shorten("https://example.com/page123");
        String s2 = shortener.shorten("https://google.com", "google7");

        System.out.println("Auto Short: " + s1 + " (len=" + s1.length() + ")");
        System.out.println("Custom    : " + s2);
        System.out.println("Redirect  : " + shortener.redirect(s1));
    }
}
