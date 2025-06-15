package app.model;

import java.io.Serializable;

public class Student implements Serializable {
    public String ogrenciNo;
    public String adSoyad;
    public String bolum;

    public Student(String ogrenciNo, String adSoyad, String bolum) {
        this.ogrenciNo = ogrenciNo;
        this.adSoyad = adSoyad;
        this.bolum = bolum;
    }
    
    // Default constructor for serialization
    public Student() {}
}
