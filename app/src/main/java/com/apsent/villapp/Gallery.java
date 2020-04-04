package com.apsent.villapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Gallery implements Serializable {
    private Integer id;
    private Integer vid;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    private String img6;
    private String img7;
    private String img8;
    private String img9;
    private String img10;


    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
        this.img1=images.get(0);
        this.img2=images.get(1);
        this.img3=images.get(2);
        this.img4=images.get(3);
        this.img5=images.get(4);
        this.img6=images.get(5);
        this.img7=images.get(6);
        this.img8=images.get(7);
        this.img9=images.get(8);
        this.img10=images.get(9);

    }

    private List<String> images=new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
        images.add(0,img1);
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
        images.add(1,img2);
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
        images.add(2,img3);
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
        images.add(3,img4);
    }

    public String getImg5() {
        return img5;
    }

    public void setImg5(String img5) {
        this.img5 = img5;
        images.add(4,img5);
    }

    public String getImg6() {
        return img6;
    }

    public void setImg6(String img6) {
        this.img6 = img6;
        images.add(5,img6);
    }

    public String getImg7() {
        return img7;
    }

    public void setImg7(String img7) {
        this.img7 = img7;
        images.add(6,img7);
    }

    public String getImg8() {
        return img8;
    }

    public void setImg8(String img8) {
        this.img8 = img8;
        images.add(7,img8);
    }

    public String getImg9() {
        return img9;
    }

    public void setImg9(String img9) {
        this.img9 = img9;
        images.add(8,img9);
    }

    public String getImg10() {
        return img10;
    }

    public void setImg10(String img10) {
        this.img10 = img10;
        images.add(9,img10);
    }
}
