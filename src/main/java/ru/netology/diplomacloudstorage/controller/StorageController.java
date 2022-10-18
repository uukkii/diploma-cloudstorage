package ru.netology.diplomacloudstorage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diplomacloudstorage.dto.FileInfo;
import ru.netology.diplomacloudstorage.model.File;
import ru.netology.diplomacloudstorage.service.StorageService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
public class StorageController {

    private final static String FILE_PATH = "/file";
    private final static String ALL_FILES_PATH = "/list";
    private final static String REQUEST_TOKEN_HEADER = "auth-token";
    private final static String REQUEST_FILENAME_PARAM = "filename";
    private final static String REQUEST_FILE_PARAM = "file";
    private final static String REQUEST_LIMIT = "limit";
    private final static String SUCCESSFUL_UPLOAD_MSG = "File is uploaded successfully";
    private final static String SUCCESSFUL_DELETE_MSG = "File is deleted successfully";
    private final static String SUCCESSFUL_EDIT_MSG = "File is edited successfully";

    private final StorageService filesStorageService;

    @PostMapping(path = FILE_PATH, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadFile(@RequestHeader(REQUEST_TOKEN_HEADER) String authToken,
                                             @RequestParam(REQUEST_FILENAME_PARAM) @NotBlank String fileName,
                                             @RequestParam(REQUEST_FILE_PARAM) @NotNull MultipartFile file) throws IOException {

        filesStorageService.storeFile(fileName, file);
        return new ResponseEntity<>(SUCCESSFUL_UPLOAD_MSG, HttpStatus.OK);
    }

    @GetMapping(path = FILE_PATH, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody
    byte[] downloadFile(@RequestHeader(REQUEST_TOKEN_HEADER) String authToken,
                        @RequestParam(REQUEST_FILENAME_PARAM) @NotBlank String fileName) {
        return filesStorageService.getFile(fileName);
    }

    @DeleteMapping(FILE_PATH)
    public ResponseEntity<String> deleteFile(@RequestHeader(REQUEST_TOKEN_HEADER) String authToken,
                                             @RequestParam(REQUEST_FILENAME_PARAM) @NotBlank String fileName) {
        filesStorageService.deleteFile(fileName);
        return new ResponseEntity<>(SUCCESSFUL_DELETE_MSG, HttpStatus.OK);
    }

    @PutMapping(FILE_PATH)
    public ResponseEntity<String> editFileName(@RequestHeader(REQUEST_TOKEN_HEADER) String authToken,
                                               @RequestParam(REQUEST_FILENAME_PARAM) @NotBlank String currentFileName,
                                               @Valid @RequestBody File newCloudFileName) {
        filesStorageService.editFileName(currentFileName, newCloudFileName);
        return new ResponseEntity<>(SUCCESSFUL_EDIT_MSG, HttpStatus.OK);
    }

    @GetMapping(ALL_FILES_PATH)
    public List<FileInfo> getInfoAboutAllFiles(@RequestHeader(REQUEST_TOKEN_HEADER) String authToken,
                                               @RequestParam(REQUEST_LIMIT) @Min(1) Integer limit) {
        return filesStorageService.getInfoAboutAllFiles(limit);
    }
}
