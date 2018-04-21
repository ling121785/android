package com.example.businessmodule.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/21.
 */

public class GiftAnimationBean {
    private String url;
    private int height;
    private int width;
    private double seconds;
    private List<PieceBean> list;

    public GiftAnimationBean() {
    }

    public GiftAnimationBean(String url, int height, int width, double seconds) {
        this.url = url;
        this.height = height;
        this.width = width;
        this.seconds = seconds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }

    public List<PieceBean> getList() {
        return list;
    }

    public void setList(List<PieceBean> list) {
        this.list = list;
    }

    public static class PieceBean{
        private int height;
        private int width;
        private int x;
        private int y;

        public PieceBean() {
        }

        public  PieceBean(int height, int width, int x, int y) {
            this.height = height;
            this.width = width;
            this.x = x;
            this.y = y;
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

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
