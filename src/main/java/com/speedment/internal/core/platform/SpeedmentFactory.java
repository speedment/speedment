package com.speedment.internal.core.platform;

import com.speedment.Speedment;

public class SpeedmentFactory {

   public static Speedment newSpeedmentInstance() {
       return new SpeedmentImpl();
   }
    
}
