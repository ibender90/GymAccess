package ru.geekbrains.gym.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.gym.mapper.CoachMapper;
import ru.geekbrains.gym.model.Coach;
import ru.geekbrains.gym.model.User;
import ru.geekbrains.gym.repository.CoachRepository;

@Service
@Data
@RequiredArgsConstructor
public class CoachService {
    private static final Logger log = LoggerFactory.getLogger(CoachService.class);
    private final CoachRepository coachRepository;

    private final CoachMapper coachMapper;

    private final CoachProfileService coachProfileService;
    private final UserService userService;

    @Transactional
    public void createCoach(Long userId){
        User user = userService.findByID(userId);
        Coach coach = Coach.builder()
                .user(user)
                .build();
        Coach savedCoach = coachRepository.save(coach);
        log.debug("New coach saved successfully");
        coachProfileService.createProfile(savedCoach);
    }
}
