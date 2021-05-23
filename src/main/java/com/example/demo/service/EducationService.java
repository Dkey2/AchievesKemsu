package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.view.AchievementsReceivedView;
import com.example.demo.view.AchievementsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EducationService {
    @Autowired
    private FormEducationRepository formEducationRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ListInstituteRepository listInstituteRepository;
    @Autowired
    private InstituteRepository instituteRepository;
    @Autowired
    private StreamRepository streamRepository;

    //Создаем новый лист институтов и добавляем туда институты. Институт не может одновременно находиться в нескольких листах
    public ListInstitute newInstitutionsList(ArrayList<Integer> institutionsId) {
        int maxId=0;
        ListInstitute listInstituteTest = listInstituteRepository.findFirstByOrderByIdAsc();
        if (listInstituteTest!=null)
            maxId=listInstituteTest.getIdListInstitute();
        ListInstitute listInstitute = new ListInstitute();
        listInstitute.setIdListInstitute(maxId+1);
        listInstitute = listInstituteRepository.save(listInstitute);
        for (Integer integer : institutionsId) {
            Optional<Institute> institute = instituteRepository.findById(integer);
            institute.get().setListInstituteForInstitute(listInstitute);
            instituteRepository.save(institute.get());
        }
        return listInstitute;
    }

    //Получаем форму обучения по ее id
    public FormOfEducation getFormEducation(int formEducationId) { return formEducationRepository.findById(formEducationId); }
    //Получаем институт по его id
    public Institute getInstitute(int instituteId) { return instituteRepository.findById(instituteId); }
    //Получаем направление по его id
    public Stream getStream(int streamId) { return streamRepository.findById(streamId); }
    //Получаем направление по его краткому названию
    public Stream getStreamShort(String shortName) { return streamRepository.findByStreamName(shortName); }
    //Получаем группу по ее id
    public Group getGroup(int groupId) { return groupRepository.findById(groupId); }
    //Получаем группу по ее названию
    public Group getGroupByName(String groupName) { return groupRepository.findByGroupName(groupName); }


    //Получаем лист институтов по его id
    public ListInstitute getListInstitute(int listInstituteId) {
        return listInstituteRepository.findById(listInstituteId);
    }


    //Получаем список форм обучения
    public <T> List <T> getAllFormEducation(Class<T> type) {
        return formEducationRepository.findBy(type);
    }
    //Получаем список институтов
    public <T> List <T> getAllInstitute(Class<T> type) {
        return instituteRepository.findBy(type);
    }
    //Получаем список направлений для раннее выбранного института
    public <T> List <T> getStreamInstitute (int instituteId, Class<T> type) { return streamRepository.findByInstitute_Id(instituteId, type); }
    //Получаем список групп для раннее выбранного направления
    public <T> List <T> getGroupsForStream(int streamId, Class<T> type) { return groupRepository.findByStream_Id(streamId, type); }


    //Получем список институтов для листа институтов по id листа
    public <T> List <T> getInstitutionsForListInstitute(int listInstituteId, Class<T> type) {
        return instituteRepository.findByListInstitute_Id(listInstituteId, type);
    }

    //Получаем лист институтов по его id
    public <T> List <T> getListsInstitute(Class<T> type) {
        return listInstituteRepository.findBy(type);
    }


    //Сохраняем направление
    public void saveStream (Stream stream) { streamRepository.save(stream); }
    //Сохраняем группу
    public void saveGroup (Group group) { groupRepository.save(group); }
}
