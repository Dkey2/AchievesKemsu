package com.example.demo.service;

import com.example.demo.entity.Ban;
import com.example.demo.repository.BanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BanService {
    @Autowired
    private BanRepository banRepository;

    //Соханение бана
    public void saveBan(Ban ban) {
        banRepository.save(ban);
    }

    //Удаление бана
    public void deleteBan(Ban ban) {
        banRepository.delete(ban);
    }

}
