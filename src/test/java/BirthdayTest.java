import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class BirthdayTest {
    public Birthday birthday;

    @BeforeEach
    void setUp() {
        birthday = new Birthday();
    }

    @Test
    void getBirthday() {
        birthday.setDate(27, 10, 2019);
        Assertions.assertEquals("27.10.2019", birthday.getBirthday());
        birthday.setDate(1, 12, 2006);
        Assertions.assertEquals("1.12.2006", birthday.getBirthday());
        birthday.setDate(9, 8, 1997);
        Assertions.assertEquals("9.8.1997", birthday.getBirthday());
        birthday.setDate(21, 4, 999);
        Assertions.assertEquals("21.4.999", birthday.getBirthday());
    }
}