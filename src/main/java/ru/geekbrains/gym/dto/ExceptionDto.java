package ru.geekbrains.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.gym.exceptions.AppException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {

    private Integer code;
    private String message;

    public ExceptionDto(AppException e){
        setCode(e.getCode());
        setMessage(e.getMessage());
    }
}
