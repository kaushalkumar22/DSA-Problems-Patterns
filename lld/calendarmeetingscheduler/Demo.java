package lld.calendarmeetingscheduler;

import java.util.Objects;

public class Demo {
    public static void main(String[] args) {
        System.out.println("Demo for lld.calendarmeetingscheduler");
    }
}
class User {
    private final Long id;
    private final String email;

    public User(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}