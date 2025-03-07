package model;

import java.util.ArrayList;

public class LorenzWheel {
  private ArrayList<Boolean> cams;
  private int currPos;

  public LorenzWheel(int size) {
    // for each wheel, cams hold size(wheel) # of 0/1 vals representing the config
    // of cams
    this.cams = new ArrayList<>();
    // currPos is the index of bit val that is used for XORing
    this.currPos = 0;
    // cam/notch initialization (all 0's)
    for (int i = 0; i < size; i++) {
      cams.add(false);
    }
  }

  // customize a certain cam/notch val (0 or 1)
  public void camReset(int camPos, boolean camVal) {
    if (camPos >= 0 && camPos < cams.size()) {
      cams.set(camPos, camVal);
    } else {
      System.out.println("Error: cam/notch position out of range");
    }
  }

  // step the wheel
  public void step() {
    currPos = (currPos + 1) % cams.size();
  }

  // bit val displayed at current cam/notch position
  public boolean displayedBitVal() {
    return cams.get(currPos);
  }
}
