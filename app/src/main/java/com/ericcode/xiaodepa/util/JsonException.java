package com.ericcode.xiaodepa.util;

/**
 * Created by tim@inspero.im on 15-1-8.
 */
public class JsonException extends RuntimeException{
    /**
     * Constructs a new {@code RuntimeException} that includes the current stack
     * trace.
     */
    public JsonException() {
    }

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace
     * and the specified detail message.
     *
     * @param detailMessage
     *            the detail message for this exception.
     */
    public JsonException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace,
     * the specified detail message and the specified cause.
     *
     * @param detailMessage
     *            the detail message for this exception.
     * @param throwable
     *            the cause of this exception.
     */
    public JsonException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace
     * and the specified cause.
     *
     * @param throwable
     *            the cause of this exception.
     */
    public JsonException(Throwable throwable) {
        super(throwable);
    }
}
