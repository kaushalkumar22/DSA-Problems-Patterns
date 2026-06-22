package Java_records_enum;

public class Enum {
    public static void main(String[] args) {
        System.out.println(Animal.DOG.getName());
        Animal a = Animal.DOG;
        if (a == Animal.DOG) {
            System.out.println("Dog");
        }

    }
}
enum Animal {
    DOG("Dog"), CAT("Cat");
    private String name;
    Animal(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
