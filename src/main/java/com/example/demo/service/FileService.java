package com.example.demo.service;

import com.example.demo.entity.File;
import com.example.demo.entity.ListFile;
import com.example.demo.entity.Student;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.ListFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private ListFileRepository listFileRepository;

    //Меняем фотограцию профиля студента
    public ResponseEntity changePhoto(Student student, byte[] photo, String format) {
        File file = fileRepository.findById(student.getFileStudent().getIdFile(), File.class);
        file.setDataFile(photo);
        file.setFormatFile(format);
        file.setListFile(null);
        fileRepository.save(file);
        return ResponseEntity.status(HttpStatus.OK).body("Фотография профиля успешно изменена");
    }

    //Создание нового листа файлов
    public ListFile newListFile() {
        int maxId=0;

        ListFile listFileTest = listFileRepository.findFirstByOrderByIdDesc(ListFile.class);
        if (listFileTest!=null)
            maxId=listFileTest.getIdListFile();

        ListFile listFile = new ListFile();
        listFile.setIdListFile(maxId+1);
        listFileRepository.save(listFile);
        return listFile;
    }


    //Сохраняем файл
    public void saveFile(File file) {
        int maxId = fileRepository.findFirstByOrderByIdDesc(File.class).getIdFile();
        file.setIdFile(maxId+1);
        fileRepository.save(file);
    }


    //Получаем файл по его id
    public <T> T getFileById(int fileId, Class<T> type) {
        return fileRepository.findById(fileId, type);
    }
    //Получаем лист файлов по его id
    public <T> T getListFileById(int listFileId, Class<T> type) {
        return listFileRepository.findById(listFileId, type);
    }
}
