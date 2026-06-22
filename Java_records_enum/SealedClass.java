package Java_records_enum;

public class SealedClass {

}
sealed interface Payment permits CreditCard, UPI, Cash {}
non-sealed class CreditCard implements Payment {}
final class UPI implements Payment {}
final class Cash implements Payment {}

