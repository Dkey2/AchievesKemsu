package com.example.demo.service;

import com.example.demo.entity.CodeReset;
import com.example.demo.repository.CodeResetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeService {
    @Autowired
    private CodeResetRepository codeResetRepository;

    //Сохранение кода
    public void saveCodeReset(CodeReset codeReset) {
        codeResetRepository.save(codeReset);
    }

    //Удаление кода
    public void deleteCodeReset(CodeReset codeReset) {
        codeResetRepository.delete(codeReset);
    }


    //Получение кода для пользователя по id пользователя
    public CodeReset getCodeReset(int userId) {
        return codeResetRepository.findByUser_Id(userId);
    }

}


