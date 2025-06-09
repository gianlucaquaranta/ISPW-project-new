package com.example.hellofx.dao;


public class FactoryProducer {
    private FactoryProducer() {}

    public static DaoFactory getFactory(String factoryType) {
        switch (factoryType.toLowerCase()) {
            case "db": return DaoDbFactory.getInstance();
            case "file": return DaoFileFactory.getInstance();
            case "memory": return DaoMemoryFactory.getInstance();
            default: throw new IllegalArgumentException("Unknown DaoFactory: " + factoryType);
        }
    }
}
