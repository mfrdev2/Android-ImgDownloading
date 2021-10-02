package com.frabbi.mydownloaddemo;

public class InFo {
    private int id;
    private int img;
    private  String imgName;

    public InFo(int id, int img, String imgName) {
        this.id = id;
        this.img = img;
        this.imgName = imgName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
