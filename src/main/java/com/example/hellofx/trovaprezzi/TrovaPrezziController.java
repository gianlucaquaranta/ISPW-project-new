package com.example.hellofx.trovaprezzi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TrovaPrezziController {

    public List<TrovaPrezziBean> trovaPrezzi(TrovaPrezziBean trovaPrezziBean){
        String className;
        List<TrovaPrezziBean> risultati = new ArrayList<>();
        List<TrovaPrezziBean> temp;
        for(String name: trovaPrezziBean.getVendors()){
            className = "com.example.hellofx.graphiccontroller."+name+"Boundary";
            try {
                Class<?> clazz = Class.forName(className);
                Object object = clazz.getDeclaredConstructor().newInstance();
                Method method = clazz.getMethod("fetchResults", TrovaPrezziBean.class);
                temp = (List<TrovaPrezziBean>) method.invoke(object, trovaPrezziBean);
                risultati.addAll(temp);
            } catch (ClassNotFoundException e) {
                System.out.println("Classe non trovata: " + className);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                System.out.println("Errore nell'invocazione del metodo su: " + className);
            }

        }
        return risultati;

    }

}
