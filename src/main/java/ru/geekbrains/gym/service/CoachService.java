package ru.geekbrains.gym.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.geekbrains.gym.dto.CoachProfileDto;
import ru.geekbrains.gym.exceptions.AppException;
import ru.geekbrains.gym.mapper.CoachMapper;
import ru.geekbrains.gym.model.Coach;
import ru.geekbrains.gym.model.CoachProfile;
import ru.geekbrains.gym.repository.CoachRepository;

@Service
@Data
@RequiredArgsConstructor
public class CoachService {
    private static final Logger log = LoggerFactory.getLogger(CoachService.class);
    private final CoachRepository coachRepository;

    private final CoachMapper coachMapper;

    //coach can create and edit workouts
    public void linkCoachTableWithUser(Long userId) {
        coachRepository.joinCoachTable(userId);
        log.debug("New coach saved successfully");
    }
    public CoachProfile editProfile(Long coachId, CoachProfileDto profileDto){

        Coach coach =  coachRepository.findById(coachId).orElseThrow(
                () -> new AppException("Coach with id " + coachId + " not found")
        );

        CoachProfile profile = null;
        if(coach.getCoachProfile() == null){
            profile = CoachProfile.builder()
                    .personalInfo(profileDto.getPersonalInfo())
                    .linkToPhoto(profileDto.getLinkToPhoto())
                    .build();
        }
        else {
            profile = coach.getCoachProfile();
            profile.setPersonalInfo(profileDto.getPersonalInfo());
            profile.setLinkToPhoto(profileDto.getLinkToPhoto());
        }
        coach.setCoachProfile(profile);
        return coachRepository.save(coach).getCoachProfile();
    }
}
