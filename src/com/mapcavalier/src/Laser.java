package com.mapcavalier.src;


import java.net.URL;

public class Laser extends EntitySprite {
    boolean fired;
    
    public Laser(int x, int y, URL url) {
        super(x, y, url);
    }
    
    public boolean hitTest(int x, int y) {

        boolean isHit = false;

        int globalToLocalX = x - this.x;
        int globalToLocalY = y - this.y;
        if (globalToLocalX >= 0 && globalToLocalX < bimg.getWidth()) {

            if (globalToLocalY >= 0 && globalToLocalY < bimg.getHeight()) {

                int color = bimg.getRGB(globalToLocalX, globalToLocalY);
                int alpha = (color >> 24) & 0xff;
                if (alpha > 0) {
                    isHit = true;
                }
            }
        }

        return isHit;
    }
    
    public void shoot(Starship a, Laser b) {
        b.x = a.x + (a.width / 2);
        b.y = a.y;
    }
   
    
}