package com.mapcavalier.src;

import java.net.URL;

public class Background extends EntitySprite {
    
    public Background(int x, int y, URL url) {
        
        super(x, y, url);
        
    }
    
    public void recycle(Background a, Background b) {
        
        //loop two background images
        if (b.y < -b.height) {
            b.y = a.y + b.height;
        } else if (b.y > a.height) {
            b.y = a.y - b.height;
        } else if (a.y < -a.height) {
            a.y = b.y + a.height;
        } else if (a.y > b.height) {
            a.y = b.y - a.height;
        }
        
    }
}