package ru.netology.diplomacloudstorage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Validated
public class Identity {

    @NotBlank
    protected String login;

    @NotBlank
    protected String password;
}
