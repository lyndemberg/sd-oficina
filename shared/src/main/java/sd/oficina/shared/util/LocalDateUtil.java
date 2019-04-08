package sd.oficina.shared.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateUtil {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate toLocalDate(String string){
        return LocalDate.parse(string,formatter);
    }

    public static String toString(LocalDate localDate){
        return localDate.format(formatter);
    }
}
