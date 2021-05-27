package com.example.demo.controller;

import com.example.demo.controller.request.CreationRewardRequest;
import com.example.demo.entity.File;
import com.example.demo.entity.Reward;
import com.example.demo.service.FileService;
import com.example.demo.service.LogService;
import com.example.demo.service.RewardService;
import com.example.demo.service.UserService;
import com.example.demo.view.RewardsView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(description = "Контроллер наград")
@RestController
public class RewardController {
    @Autowired
    private RewardService rewardService;
    @Autowired
    private FileService fileService;
    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;

    @ApiOperation("Список наград")
    @GetMapping("/rewards")
    public List<RewardsView> getAllRewards() {
        return rewardService.getAllReward(RewardsView.class);
    }

    @ApiOperation("Создание новой награды - для админа")
    @PostMapping("/admin/createReward")
    public ResponseEntity createReward(CreationRewardRequest creationRewardRequest) {
        Reward reward = new Reward();
        reward.setNameReward(creationRewardRequest.getRewardName());

        File file = new File();
        file.setDataFile(creationRewardRequest.getData());
        file.setFormatFile(creationRewardRequest.getFormat());
        file.setListFile(null);
        fileService.saveFile(file);

        reward.setFileOfReward(file);
        rewardService.saveReward(reward);
        logService.createNewLog(userService.getUserId(), 12, reward.getIdReward(), null, reward.getNameReward());
        return ResponseEntity.status(HttpStatus.OK).body("Награда успешно создана");
    }

    @ApiOperation("Изменение данных о награде - для админа")
    @PutMapping("/admin/changeReward/{rewardId}")
    public ResponseEntity changeReward(@PathVariable
                                           @ApiParam(value = "Id награды. Not null. >0", example = "3")
                                                   int rewardId,
                                       @RequestParam
                                            @ApiParam(value = "Название награды награды. Может не передаваться, тогда эти данные не будут изменены. Если передается -  Not null", example = "Закрытие энки")
                                                   Optional<String> rewardName,
                                       @RequestParam
                                            @ApiParam(value = "Иконка награды в виде byte[]. Может не передаваться, тогда эти данные не будут изменены. Если передается -  Not null")
                                                   Optional<byte[]> fileData,
                                       @RequestParam
                                            @ApiParam(value = "Формат иконки награды. Может не передаваться, тогда эти данные не будут изменены. Если передается -  Not null", example = "jpeg")
                                                   Optional<String> fileFormat) {
        Reward reward = rewardService.getReward(rewardId);
        if (reward==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Награда с указанным id не найдена");

        String oldData="";

        //Если название награды было передано, меняем его
        if (rewardName.isPresent())
        {
            oldData=rewardName.get();
            reward.setNameReward(rewardName.get());
        }
        //Если изображение или его формат были переданы, получаем файл и меняем указанные данные
        if (fileData.isPresent() || fileFormat.isPresent()) {
            int fileId = reward.getFileOfReward().getIdFile();
            File file = fileService.getFileById(fileId, File.class);
            if (file==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Файл с иконкой награды не найден");
            if (fileData.isPresent())
            {
                if (oldData.equals(""))
                    oldData=String.valueOf(fileData.get());
                else
                    oldData=oldData+" "+ fileData.get();
                file.setDataFile(fileData.get());
            }
            if (fileFormat.isPresent())
            {
                if (oldData.equals(""))
                    oldData=fileFormat.get();
                else
                    oldData=oldData+" "+fileFormat.get();
                file.setFormatFile(fileFormat.get());
            }
            fileService.resetFile(file);
        }
        rewardService.saveReward(reward);
        logService.createNewLog(userService.getUserId(), 13, reward.getIdReward(), oldData, reward.getNameReward());
        return ResponseEntity.status(HttpStatus.OK).body("Данные успешно изменены");
    }
}
