package com.udemy.course.ormtool.model;

public enum Currency {
    USD{
        @Override
        public float convertTo(Currency targetCurrency, float amount) {
            switch (targetCurrency) {
                case USD:
                    return amount;
                case EURO:
                    return amount / 2;
            }
            throw new RuntimeException(String.format("Wrong Currency Type: %s", targetCurrency));
        }
    },
    EURO {
        @Override
        public float convertTo(Currency targetCurrency, float amount) {
            switch (targetCurrency) {
                case USD:
                    return amount * 2;
                case EURO:
                    return amount;
            }
            throw new RuntimeException(String.format("Wrong Currency Type: %s", targetCurrency));
        }
    };

    public abstract float convertTo(Currency targetCurrency, float amount);
}
