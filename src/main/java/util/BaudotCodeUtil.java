package util;

import java.util.HashMap;
import java.util.Map;

public class BaudotCodeUtil {
  // function to convert from decimal to binary
  private static String decToBi(int decimalNum) {
    if (decimalNum == 0) {
      return "00000";
    }
    String binNum = "";
    while (decimalNum > 0) {
      binNum += decimalNum % 2;
      decimalNum /= 2;
    }
    if (binNum.length() < 5) {
      while (binNum.length() < 5) {
        binNum = "0" + binNum;
      }
    }
    return binNum;
  }

  // initialize two arrays to store the Baudot codes and their corresponding
  // characters (ITA-2 standard); for the Baudot codes, we will have least
  // significant bit on the rightmost position
  private static String[] ltrArr = { "NULL", "E", "LF", "A", "SP", "S", "I", "U", "CR", "D", "R", "J", "N", "F", "C",
      "K", "T", "Z", "L", "W", "H", "Y", "P", "Q", "O", "B", "G", "FIG", "M", "X", "V", "LTR" };
  private static String[] figArr = { "NULL", "3", "LF", "-", "SP", "'", "8", "7", "CR", "ENQ", "4", "BELL", ",", "!",
      ":", "(", "5", "+", ")", "2", "$", "6", "0", "1", "9", "?", "&", "FIG", ".", "/", ";", "LTR" };
  private static String mode = "LTR";

  private static Map<String, String> baudotToLtr = new HashMap<>();
  private static Map<String, String> baudotToFig = new HashMap<>();
  private static Map<String, String> ltrToBaudot = new HashMap<>();
  private static Map<String, String> figToBaudot = new HashMap<>();

  private static Map<String, Map<String, String>> allMappings() {
    for (int i = 0; i <= 30; i++) {
      String binNum = decToBi(i);
      baudotToLtr.put(binNum, ltrArr[i]);
      baudotToFig.put(binNum, figArr[i]);
      ltrToBaudot.put(ltrArr[i], binNum);
      figToBaudot.put(figArr[i], binNum);
    }
    Map<String, Map<String, String>> mappings = new HashMap<>();
    mappings.put("baudotToLtr", baudotToLtr);
    mappings.put("baudotToFig", baudotToFig);
    mappings.put("ltrToBaudot", ltrToBaudot);
    mappings.put("figToBaudot", figToBaudot);

    return mappings;
  }

  public static String convertToBaudot(String input) {
    Map<String, Map<String, String>> mappings = allMappings();

    if (mode.equals("LTR")) {
      return mappings.get("ltrToBaudot").get(input.toUpperCase());
    } else {
      return mappings.get("figToBaudot").get(input);
    }
  }

  public static String convertFromBaudot(String input) {
    Map<String, Map<String, String>> mappings = allMappings();

    if (mode.equals("LTR")) {
      return mappings.get("baudotToLtr").get(input);
    } else {
      return mappings.get("baudotToFig").get(input);
    }
  }

  public static void switchMode() {
    if (mode.equals("LTR")) {
      mode = "FIG";
    } else {
      mode = "LTR";
    }
  }
}
