package com.example.demo.controller;

import com.example.demo.controller.request.CreationGroupRequest;
import com.example.demo.controller.request.CreationStreamRequest;
import com.example.demo.entity.*;
import com.example.demo.view.*;
import com.example.demo.service.EducationService;
import com.example.demo.service.LogService;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Api(description = "Контроллер данных об образовании")
@RestController
public class EducationController {
    @Autowired
    private EducationService educationService;
    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;

    @ApiOperation("Список форм обучения")
    @GetMapping("/education/formEducation")
    public List<FormEducationVew> getAllFormEducation() {
        return educationService.getAllFormEducation(FormEducationVew.class);
    }

    @ApiOperation("Список институтов")
    @GetMapping("/education/institutions")
    public List<InstitutionsView> getAllInstitute() {
        return educationService.getAllInstitute(InstitutionsView.class);
    }

    @ApiOperation("Список направлений раннее выбранного института")
    @GetMapping("/education/stream/{instituteId}")
    public List<StreamView> getStreamForInstitute(@PathVariable
                                       @ApiParam (value = "Id раннее выбранного института. Not null. [1,15]", example = "11")
                                               Integer instituteId) {
        Institute institute = educationService.getInstitute(instituteId);
        if (institute==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Институт с указанным id не найден");
        return educationService.getStreamInstitute(instituteId, StreamView.class);
    }

    @ApiOperation("Список групп раннее выбранного направления")
    @GetMapping("/education/group/{streamId}")
    public List<GroupView> getGroupForStream(@PathVariable
                                            @ApiParam (value = "Id раннее выбранного направления. Not null. >0", example = "3")
                                                    int streamId) {
        Stream stream = educationService.getStream(streamId);
        if (stream==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Направление с указанным id не найдено");
        return educationService.getGroupsForStream(streamId, GroupView.class);
    }

    @ApiOperation("Список институтов в листе институтов по id листа - для модератора")
    @GetMapping("/moderator/institutionsList/{listInstituteId}")
    public List<InstitutionsView> getListInstitute(@PathVariable
                                                        @ApiParam (value = "Id листа институтов. Not null. >0", example = "1")
                                                                int listInstituteId) {
        ListInstitute listInstitute = educationService.getListInstitute(listInstituteId);
        if (listInstitute==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Лист институтов с указанным id не найден");
        return educationService.getInstitutionsForListInstitute(listInstituteId, InstitutionsView.class);
    }

    @ApiOperation("Список листов институтов - для модератора")
    @GetMapping("/moderator/institutionsList")
    public List<ListInstitute> getListsInstitute() {
        return educationService.getListsInstitute(ListInstitute.class);
    }

    @ApiOperation("Создание листа институтов по списку id институтов - для администратора. Институт может принадлежать только одному листу")
    @PostMapping("/admin/newInstitutionsList/{institutionsId}")
    public ResponseEntity newInstitutionsList(@PathVariable
                                                  @ApiParam (value = "Список id институтов - ArrayList<Integer>. Not null")
                                                          ArrayList<Integer> institutionsId) {
        if (institutionsId.size()==0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Список институтов пуст");
        ListInstitute listInstitute = educationService.newInstitutionsList(institutionsId);
        logService.createNewLog(userService.getUserId(), 5, listInstitute.getIdListInstitute(), null, String.valueOf(listInstitute.getIdListInstitute()));
        return ResponseEntity.status(HttpStatus.OK).body("Список успешно сформирован");
    }


    @ApiOperation("Создание нового направления - для админа")
    @PostMapping("/admin/createStream")
    public ResponseEntity createStream(@RequestBody
                                           @ApiParam(value = "Запрос с данными для создания направления")
                                                   CreationStreamRequest creationStreamRequest) {
        Institute institute = educationService.getInstitute(creationStreamRequest.getInstituteId());
        if (institute==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Институт с указанным id не найден");

        Stream streamShort = educationService.getStreamShort(creationStreamRequest.getShortName());
        if (streamShort!=null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Направление с указанным названием уже существует");
        Stream stream = new Stream();
        stream.setStreamName(creationStreamRequest.getShortName());
        stream.setStreamFullName(creationStreamRequest.getFullName());
        stream.setInstituteForStream(educationService.getInstitute(creationStreamRequest.getInstituteId()));
        educationService.saveStream(stream);
        logService.createNewLog(userService.getUserId(), 8, stream.getIdStream(), null, stream.getStreamName());
        return ResponseEntity.status(HttpStatus.OK).body("Направление успешно создано");
    }

    @ApiOperation("Создание новой группы - для админа")
    @PostMapping("/admin/createGroup")
    public ResponseEntity createGroup(@RequestBody
                                          @ApiParam(value = "Запрос с данными для создания группы")
                                                  CreationGroupRequest creationGroupRequest) {
        Stream stream = educationService.getStream(creationGroupRequest.getStreamId());
        if (stream==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Направление с указанным id не найдено");

        Group groupTest = educationService.getGroupByName(creationGroupRequest.getName());
        if (groupTest!=null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Группа с указанным названием уже существует");
        Group group = new Group();
        group.setGroupName(creationGroupRequest.getName());
        group.setStreamForGroup(educationService.getStream(creationGroupRequest.getStreamId()));
        educationService.saveGroup(group);
        logService.createNewLog(userService.getUserId(), 9, group.getIdGroup(), null, group.getGroupName());
        return ResponseEntity.status(HttpStatus.OK).body("Группа успешно создана");
    }
}
