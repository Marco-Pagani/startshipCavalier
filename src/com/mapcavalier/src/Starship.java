package com.mapcavalier.src;


import java.net.URL;

public class Starship extends EntitySprite {
    
    int hp = 5;
    int score;
    int shotsFired;
    boolean left, right;

    public Starship(int x, int y, URL url) {
        
        super(x, y, url);
    }
    
    public boolean fire(Starship a, Laser b) {
        b.setCoord(a.x + (a.width / 2 ), a.y);
        score++;
        
        return true;
    }
    
    
}