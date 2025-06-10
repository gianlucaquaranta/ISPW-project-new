package com.example.hellofx.exception;

public class UserNotLoggedException extends RuntimeException {
    public UserNotLoggedException() { super("Devi effettuare il login per poter prenotare il libro!"); }
}
