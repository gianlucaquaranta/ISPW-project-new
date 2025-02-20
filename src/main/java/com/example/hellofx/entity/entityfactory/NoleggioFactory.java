package com.example.hellofx.entity.entityfactory;

import com.example.hellofx.entity.Noleggio;

public class NoleggioFactory {

        private static NoleggioFactory instance = null;
        public static NoleggioFactory getInstance() {
            if (instance == null) {
                instance = new NoleggioFactory();
            }
            return instance;
        }
        private NoleggioFactory() {}

        public Noleggio createNoleggio() { return new Noleggio(); }
}
