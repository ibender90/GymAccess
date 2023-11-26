package ru.geekbrains.gym.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.geekbrains.gym.dto.CoachProfileDto;
import ru.geekbrains.gym.exceptions.AppException;
import ru.geekbrains.gym.mapper.CoachProfileMapper;
import ru.geekbrains.gym.model.Coach;
import ru.geekbrains.gym.model.CoachProfile;
import ru.geekbrains.gym.repository.CoachProfileRepository;

@Service
@Data
@RequiredArgsConstructor
public class CoachProfileService {
    private static final Logger log = LoggerFactory.getLogger(CoachService.class);
    private final CoachProfileRepository coachProfileRepository;
    private final CoachProfileMapper coachProfileMapper;


    public CoachProfileDto editProfile(CoachProfileDto profileDto) {

        CoachProfile profileToEdit = coachProfileRepository.findById(profileDto.getId()).orElseThrow(
                ()-> new AppException("Profile with id: " + profileDto.getId() + "not found")
        );
        profileToEdit.setPersonalInfo(profileDto.getPersonalInfo());
        profileToEdit.setLinkToPhoto(profileDto.getLinkToPhoto());

        return coachProfileMapper.toDto(coachProfileRepository.save(profileToEdit));
    }

    public void createProfile(Coach coach) {
        CoachProfile profile = new CoachProfile();
        profile.setCoach(coach);
        coachProfileRepository.save(profile);
        log.debug("Coach profile created successfully");
    }
}
