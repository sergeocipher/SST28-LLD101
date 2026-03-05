public class PhoneValidator {

    public static void requireWhatsAppPhone(String phone) {
        if (phone == null || !phone.startsWith("+")) {
            throw new IllegalArgumentException("phone must start with + and country code");
        }
    }
}