package ru.netology.diplomacloudstorage.dto;

import lombok.Value;

@Value
public class FileInfo {
    String filename;
    long size;
}
