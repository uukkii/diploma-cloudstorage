package ru.netology.diplomacloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column(nullable = false)
    protected String filename;

    @Column(nullable = false)
    protected String initialFilename;

    @Column(nullable = false)
    protected String contentType;

    @Column(nullable = false)
    protected long size;

    @Lob
    @Column(name = "file", columnDefinition = "MID")
    protected byte[] bytes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    protected User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return id == file.id && size == file.size && file.equals(file.filename) && Objects.equals(initialFilename, file.initialFilename) && Objects.equals(contentType, file.contentType) && Arrays.equals(bytes, file.bytes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, filename, initialFilename, contentType, size);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

    @Override
    public String toString() {
        return "File[" +
                "filename='" + filename + '\'' +
                ", initialFilename='" + initialFilename + '\'' +
                ", contentType='" + contentType + '\'' +
                ", size=" + size +
                ']';
    }
}
