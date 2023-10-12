package com.example.prehack.web.controller;


import com.example.prehack.model.Task;
import com.example.prehack.service.TaskService;
import com.example.prehack.web.dto.TaskDTO;
import com.example.prehack.web.dto.UserEmailsForProjectDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {


}
