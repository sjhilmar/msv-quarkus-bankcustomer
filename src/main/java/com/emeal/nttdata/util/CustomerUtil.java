package com.emeal.nttdata.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomerUtil {

  public static String getCurrentDateTimeFormatted() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return LocalDateTime.now().format(formatter);
  }
}
