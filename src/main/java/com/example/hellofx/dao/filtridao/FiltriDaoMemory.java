package com.example.hellofx.dao.filtridao;

import com.example.hellofx.entity.Filtri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FiltriDaoMemory implements FiltriDao {
    private Map<String, List<Filtri>> filtriMap= new HashMap<>();

    private static FiltriDaoMemory instance;

    public static FiltriDaoMemory getInstance() {
        if (instance == null) {
            instance = new FiltriDaoMemory();
        }
        return instance;
    }

    private FiltriDaoMemory() {}

    @Override
    public List<Filtri> loadAllUtente(String username) throws IllegalArgumentException {
        if(filtriMap.containsKey(username)){
            return (List<Filtri>) filtriMap.get(username);
        } else throw new IllegalArgumentException("user not found");
    }

    @Override
    public void storeOne(Filtri filtri, String username) {
        if(filtriMap.containsKey(username)){
            filtriMap.get(username).add(filtri);
        } else {
            List<Filtri> list = new ArrayList<>();
            list.add(filtri);
            filtriMap.put(username, list);
        }
    }

    @Override
    public void deleteAllUtente(String username) {
        if(filtriMap.containsKey(username)){
            filtriMap.remove(username);
        } else throw new IllegalArgumentException();
    }

    @Override
    public void deleteOne(String username, Integer i) {
        if(filtriMap.containsKey(username)){
            if(i>=0 && i<= filtriMap.get(username).size()) {
                List<Filtri> list = filtriMap.get(username);
                list.remove(i);
                filtriMap.put(username, list);
            } else {
                throw new IndexOutOfBoundsException();
            }
        } else throw new IllegalArgumentException();
    }
}
