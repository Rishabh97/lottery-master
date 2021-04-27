package com.esignlive.lottery.exceptions;

public class PlayingWithoutPurchaseException extends RuntimeException{

    public PlayingWithoutPurchaseException()
    {
        super();
    }

    public PlayingWithoutPurchaseException(final String message, final Throwable exception)
    {
        super(message, exception);
    }

    public PlayingWithoutPurchaseException(final String message)
    {
        super(message);
    }

    public PlayingWithoutPurchaseException(final Throwable exception)
    {
        super(exception);
    }
}
