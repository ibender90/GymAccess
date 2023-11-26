package ru.geekbrains.gym.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.gym.dto.WorkoutDto;
import ru.geekbrains.gym.exceptions.AppException;
import ru.geekbrains.gym.mapper.WorkoutMapper;
import ru.geekbrains.gym.model.Coach;
import ru.geekbrains.gym.model.Workout;
import ru.geekbrains.gym.repository.CoachRepository;
import ru.geekbrains.gym.repository.WorkoutRepository;

import java.util.LinkedList;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final WorkoutMapper workoutMapper;
    private final CoachRepository coachRepository;

    @Transactional
    public WorkoutDto createWorkout(WorkoutDto workoutDto, String coachEmail){

        Coach coach =  coachRepository.findByEmail(coachEmail).orElseThrow(()->
                new AppException("Coach with email " + coachEmail + " not found", 400));

        Workout newWorkout = workoutMapper.toEntity(workoutDto);
        newWorkout.setId(null);
        newWorkout.setCoach(coach);

        return workoutMapper.toDto(workoutRepository.save(newWorkout));
    }

    //todo test
    public List<WorkoutDto> findAll(Long coachId){
        List<Workout> list = workoutRepository.findAllByCoach(coachId).orElseThrow(() -> new AppException("No workouts found for this coach"));
        return workoutMapper.toDtos(list);
    }

    public  WorkoutDto editWorkout(){
        return null;
    }

    public WorkoutDto findWorkout(){
        return null;
    }

    public void deleteWorkout(){

    }

}
