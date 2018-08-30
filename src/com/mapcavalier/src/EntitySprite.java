package com.mapcavalier.src;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;

public class EntitySprite {
    int x, y;
    int width, height;
    Image img;
    BufferedImage bimg;
    
  
    public EntitySprite(int x, int y, URL url) {
        
        this.x = x;
        this.y = y;
               
        img = new ImageIcon(url).getImage();
        bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bimg.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        width = img.getWidth(null);
        height = img.getHeight(null);

    }
    
    public void setCoord(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void moveX(int a) {
        //move the sprite left/right
        x += a;
    }
    
    public void moveY(int a) {
        //move the sprite up/down
        y += a;
    }
    
    public void draw(Graphics g) {
        g.drawImage(img, x, y, null);
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
}