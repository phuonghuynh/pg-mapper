package net.pg.mapper.util;

public class ClassUtils {

   public static boolean csvHasText(String csvString, String text) {
      String string = "," + csvString + ",";
      return string.contains("," + text + ",");
   }
}
