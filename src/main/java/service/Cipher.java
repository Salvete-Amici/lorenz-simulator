package service;

import java.util.ArrayList;

import model.LorenzWheel;
import util.BaudotCodeUtil;

public class Cipher {
  private LorenzWheel[] chiWheels;
  private LorenzWheel[] psiWheels;
  private LorenzWheel muWheel1, muWheel2;

  public Cipher() {
    int[] chiCamNum = { 41, 31, 29, 26, 23, 43 };
    int[] psiCamNum = { 43, 37, 51, 53, 59 };

    chiWheels = new LorenzWheel[5];
    psiWheels = new LorenzWheel[5];

    for (int i = 0; i < 5; i++) {
      chiWheels[i] = new LorenzWheel(chiCamNum[i]);
      psiWheels[i] = new LorenzWheel(psiCamNum[i]);
    }

    muWheel1 = new LorenzWheel(61);
    muWheel2 = new LorenzWheel(37);
  }

  // Chi Wheels always step as a new character gets processed; Mu/Motor Wheel #1
  // alwasy steps, and if its output bit is 1, then Mu/Motor Wheel #2 also steps;
  // Psi Wheels step when the output bit of Mu/Motor #2 is 1
  public void wheelSteppingLogic() {
    for (LorenzWheel chiWheel : chiWheels) {
      chiWheel.step();
    }

    muWheel1.step();

    if (muWheel1.displayedBitVal() == true) {
      muWheel2.step();
    }

    if (muWheel2.displayedBitVal() == true) {
      for (LorenzWheel psiWheel : psiWheels) {
        psiWheel.step();
      }
    }
  }

  // XOR the binary sequences from corresponding Chi Wheels and Psi Wheels
  public boolean[] generateKeystream() {
    boolean[] keystream = new boolean[5];
    for (int j = 0; j < 5; j++) {
      keystream[j] = chiWheels[j].displayedBitVal() ^ psiWheels[j].displayedBitVal();
    }
    return keystream;
  }

  // method to encipher or decipher a message by XORing with the keystream
  public String encipherOrDecipher(String message) {
    ArrayList<String> enOrDecryptedMessage = new ArrayList<>();
    for (int i = 0; i < message.length(); i++) {
      // convert a character in message string to baudot code string
      String baudot = BaudotCodeUtil.convertToBaudot(Character.toString(message.charAt(i)));
      boolean[] kStr = generateKeystream();
      StringBuilder enOrDecryptedBaudot = new StringBuilder();
      for (int k = 0; k < 5; k++) {
        String XORed = (baudot.charAt(k) - 0 == 1) ^ (kStr[k]) ? "1" : "0";
        enOrDecryptedBaudot.append(XORed);

      }
      enOrDecryptedMessage.add(enOrDecryptedBaudot.toString());
    }
    StringBuilder res = new StringBuilder();
    for (int j = 0; j < enOrDecryptedMessage.size(); j++) {
      res.append(BaudotCodeUtil.convertFromBaudot(enOrDecryptedMessage.get(j)));
    }
    return res.toString();
  }
}
