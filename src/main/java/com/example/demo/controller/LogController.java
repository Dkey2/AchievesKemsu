package com.example.demo.controller;

import com.example.demo.entity.Operation;
import com.example.demo.entity.Role;
import com.example.demo.service.LogService;
import com.example.demo.service.UserService;
import com.example.demo.view.LogView;
import com.example.demo.view.LogsView;
import com.example.demo.view.TypeOperationView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Api(description = "Контроллер логов")
@RestController
public class LogController {
    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;

    @ApiOperation("Список логов для модератора")
    @GetMapping("/moderator/getLogs")
    public List<LogsView> getLogsForModerator() {
        return logService.getLogsForModerator(LogsView.class);
    }


    @ApiOperation("Конкретный лог для админа/модера")
    @GetMapping("/upr/getLog/{logId}")
    public LogView getLogsForAdmin(@PathVariable
                                       @ApiParam (value = "Id лога. Not null. >0", example = "12")
                                               int logId) {
        LogView log = logService.getLog(logId, LogView.class);
        if (log==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Лог с указанным id не найден");
        else
            return log;
    }

    @ApiOperation("Список логов для админа")
    @GetMapping("/admin/getLogs")
    public List<LogsView> getLogsForAdmin(@RequestParam
                                              @ApiParam(value = "Id типа операции. Фильтр, может не передаваться. Если передается - Not null. [1,16]", example = "14")
                                                      Optional<Integer> operationId,
                                          @RequestParam
                                          @ApiParam(value = "Id роли пользователя. Фильтр, может не передаваться. Если передается - Not null. [1,3]", example = "2")
                                                  Optional<Integer> roleId) {


        if (operationId.isEmpty()) {
            if (roleId.isEmpty())
                return logService.getAllLogs(LogsView.class);
            else
            {
                Role role = userService.getRole(roleId.get());
                if (role==null)
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Роль с указанным id не найдена");
                return logService.getLogsByRole(roleId.get(), LogsView.class);
            }
        }
        else {
            Operation operation = logService.getOperation(operationId.get(), Operation.class);
            if (operation==null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Тип операции с указанным id не найден");
            if (roleId.isEmpty())
                return logService.getLogsByOperation(operationId.get(),LogsView.class);
            else
                return logService.getLogsByRoleAndOperation(roleId.get(), operationId.get(), LogsView.class);
        }
    }

    @ApiOperation("Список типов операций для админа")
    @GetMapping("/admin/getTypeOperation")
    public List<TypeOperationView> getTypeOperation() {
        return logService.getAllTypeOperation(TypeOperationView.class);
    }
}
