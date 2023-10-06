package ru.geekbrains.gym.mocks;

import ru.geekbrains.gym.dto.CoachProfileDto;

public class CoachProfileDtoMock {
    public static CoachProfileDto getMock(Long id){
        return CoachProfileDto.builder()
                .id(id)
                .linkToPhoto("MockLink")
                .personalInfo("MockPersonalInfo")
                .build();
    }
}
