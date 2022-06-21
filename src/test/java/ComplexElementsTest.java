import org.junit.jupiter.api.*;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ComplexElementsTest {

    String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
    String date;
    String month;
    String day;

    @BeforeEach
    void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 100);
        date =  new SimpleDateFormat("dd.MM.yyyy").format(calendar.getTime());
        month = monthNames[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR);
        day = Integer.toString(calendar.get(Calendar.DATE));
        open("http://localhost:9999");
    }

    @Test
    void shouldTestAllFieldsCorrectly() {
        open("http://localhost:9999");
        int Day = 3;
        String date = LocalDate.now().plusDays(Day).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        $("[data-test-id=city] input").setValue("Кр");
        $$("div.popup__content div").find(exactText("Краснодар")).click();
        $("span[data-test-id='date'] button").click();

        while (!$("div.calendar__name").getText().equals(month)) {
            $$("div.calendar__arrow.calendar__arrow_direction_right").get(1).click();
        }

        $$("table.calendar__layout td").find(text(day)).click();
        $("[data-test-id=name] input").setValue("Ежков Владислав");
        $("[data-test-id=phone] input").setValue("+79183477293");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $x("//*[@class=\"notification__content\"]").should(appear, Duration.ofSeconds(15));
    }
}