package com.example.demo.service;

import com.example.demo.entity.Reward;
import com.example.demo.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardService {
    @Autowired
    private RewardRepository rewardRepository;

    //Сохранение награды
    public void saveReward(Reward reward) {
        rewardRepository.save(reward);
    }

    //Получение награды по ее id
    public Reward getReward(int rewardId) {
        return rewardRepository.findById(rewardId);
    }

    //Получение списка всех наград
    public <T> List <T> getAllReward(Class<T> type) {
        return rewardRepository.findBy(type);
    }


}
