package com.mapcavalier.src;

import java.net.URL;

public class EnemyShip extends Starship {
    
    public EnemyShip(int x, int y, URL url) {
        super(x, y, url);
    }
    
    public void fire(Laser a, EnemyShip b) {
        a.x = b.x + 39;
        a.y = b.y + 67;
    }
}