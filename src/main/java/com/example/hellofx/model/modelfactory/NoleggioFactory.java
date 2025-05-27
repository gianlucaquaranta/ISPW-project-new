package com.example.hellofx.model.modelfactory;

import com.example.hellofx.model.Noleggio;

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
