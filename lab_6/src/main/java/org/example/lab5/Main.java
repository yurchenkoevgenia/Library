package org.example.lab5;

import org.example.lab5.config.HibernateUtil;
import org.example.lab5.demo.LibraryDemo;

public class Main {
    public static void main(String[] args) {
        LibraryDemo demo = new LibraryDemo(HibernateUtil.getSessionFactory());
        demo.run();
        HibernateUtil.shutdown();
    }
}
