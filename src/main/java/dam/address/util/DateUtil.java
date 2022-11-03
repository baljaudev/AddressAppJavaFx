package dam.address.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    // Declaramos el patrón de fecha a usar en la conversión:
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    // Pasamos el patrón al 'formateador' de la fecha:
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * Devuelve la fecha dada formateada a String; comprobando si fuera null o no.
     * Patrón usado --> {@link DateUtil#DATE_PATTERN}
     * @param date La fecha para ser retornada como String
     * @return Fecha formateada a String
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * Convierte una String en el formato definido: {@link DateUtil#DATE_PATTERN}
     * a un {@link LocalDate} object.
     *
     * Devuelve null si no pudo convertirse el Stringa LocalDate.
     *
     * @param dateString la fecha como String
     * @return the date object or null if it could not be converted
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Comprueba si un String está en formato válido para devolver una fecha válida.
     *
     * @param dateString
     * @return true if the String is a valid date
     */
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return DateUtil.parse(dateString) != null;
    }
}