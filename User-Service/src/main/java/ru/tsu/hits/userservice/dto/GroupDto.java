package ru.tsu.hits.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupDto {

    private String groupNumber;

    private List<UserDto> students;
}
