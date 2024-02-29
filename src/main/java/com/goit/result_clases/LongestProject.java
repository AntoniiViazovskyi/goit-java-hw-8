package com.goit.result_clases;

public class LongestProject {

    private int id;
    private int count;

    public LongestProject(int id, int count) {
        this.id = id;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "LongestProject{" +
                "id=" + id +
                ", count=" + count +
                '}';
    }
}
