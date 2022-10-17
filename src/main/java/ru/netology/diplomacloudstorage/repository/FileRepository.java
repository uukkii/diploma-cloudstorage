package ru.netology.diplomacloudstorage.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.netology.diplomacloudstorage.dto.FileInfo;
import ru.netology.diplomacloudstorage.model.File;
import ru.netology.diplomacloudstorage.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findFileByUserAndFilename(User user, String filename);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update File c set c.filename = :filename where c.id = :id")
    void editFileName(@Param("fileName") String filename, @Param("id") long id);

    List<FileInfo> findAllByUser(User user);
}