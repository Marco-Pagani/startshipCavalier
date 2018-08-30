package com.mapcavalier.src;

public class AnimationSprite {

    int x, y;
    int currentFrame;

    public AnimationSprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void animate(int a, int b) {
        a++;
        currentFrame = a;
        


        if (a >= b) {
            a = 0;
            currentFrame = 0;
        }

    }
}