package com.fxb.jeesite.common.qr;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class LogoThread implements Runnable{  
  
    private File file;  
    private String name;  
    private int height;  
    private int width;  
    private int dw;  
    @Override  
    public void run() {  
         BufferedImage image;  
         long start = 0;  
        try {  
            start = System.currentTimeMillis(); 
            image = QRCodeUtil.createImage(QRCodeUtil.URL+name,QRCodeUtil.LogoPath,width,height,dw);  
            ImageIO.write(image,"png",file);
            file=null;  
            image=null;  
            if(QRCodeUtil.DEBUG){  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }    
    }  
    public File getFile() {  
        return file;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public void setFile(File file) {  
        this.file = file;  
    }  
    public int getDw() {  
        return dw;  
    }  
    public void setDw(int dw) {  
        this.dw = dw;  
    }  
    public int getHeight() {  
        return height;  
    }  
    public void setHeight(int height) {  
        this.height = height;  
    }  
    public int getWidth() {  
        return width;  
    }  
    public void setWidth(int width) {  
        this.width = width;  
    }  
}  