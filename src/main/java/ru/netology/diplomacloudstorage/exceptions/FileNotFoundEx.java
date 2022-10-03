package ru.netology.diplomacloudstorage.exceptions;

public class FileNotFoundEx extends RuntimeException {
    public FileNotFoundEx(String msg) {
        super(msg);
    }
}
