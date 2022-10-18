package ru.netology.diplomacloudstorage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diplomacloudstorage.dto.FileInfo;
import ru.netology.diplomacloudstorage.exceptions.DataValidationEx;
import ru.netology.diplomacloudstorage.exceptions.FileNotFoundEx;
import ru.netology.diplomacloudstorage.model.File;
import ru.netology.diplomacloudstorage.model.User;
import ru.netology.diplomacloudstorage.repository.FileRepository;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final static String DVE_MSG = "Error store file: Incorrect file name, file this such name already exists";
    private final static String FNF_MSG = "Error delete file: Incorrect file name, file is not found";

    private final FileRepository fileRepository;

    @Transactional
    public File storeFile(String fileName, MultipartFile multipartFile) throws IOException {
        User user = getCurrentUser();
        if (fileRepository.findFileByUserAndFilename(user, fileName).isPresent()) {
            throw new DataValidationEx(DVE_MSG);
        }
        File cloudFile = File.builder()
                .filename(fileName)
                .initialFilename(multipartFile.getOriginalFilename())
                .size(multipartFile.getSize())
                .contentType(multipartFile.getContentType())
                .bytes(multipartFile.getBytes())
                .user(user)
                .build();
        return fileRepository.save(cloudFile);
    }

    @Transactional
    public File deleteFile(String fileName) {
        User user = getCurrentUser();
        File cloudFile = fileRepository.findFileByUserAndFilename(user, fileName)
                .orElseThrow(() -> new FileNotFoundEx(FNF_MSG));
        fileRepository.deleteById(cloudFile.getId());
        return cloudFile;
    }

    public byte[] getFile(String fileName) {
        User user = getCurrentUser();
        File cloudFile = fileRepository.findFileByUserAndFilename(user, fileName)
                .orElseThrow(() -> new FileNotFoundEx(FNF_MSG));
        return cloudFile.getBytes();
    }

    @Transactional
    public String editFileName(String currentFileName, File newFileName) {
        User user = getCurrentUser();
        File cloudFile = fileRepository.findFileByUserAndFilename(user, currentFileName)
                .orElseThrow(() -> new FileNotFoundEx(FNF_MSG));
        if (fileRepository.findFileByUserAndFilename(user, newFileName.getFilename()).isPresent()) {
            throw new DataValidationEx(DVE_MSG);
        }
        fileRepository.editFileName(newFileName.getFilename(), cloudFile.getId());
        return newFileName.getFilename();
    }

    public List<FileInfo> getInfoAboutAllFiles(int limit) {
        User user = getCurrentUser();
        List<FileInfo> info = fileRepository.findAllByUser(user);
        if (info.size() <= limit) {
            return info;
        } else {
            return info.subList(0, limit);
        }
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}