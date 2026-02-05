package net.abbas.common.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Data Not Found");
    }
}
