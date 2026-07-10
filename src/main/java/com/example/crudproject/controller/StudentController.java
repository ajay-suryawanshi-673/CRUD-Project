package com.example.crudproject.controller;

import com.example.crudproject.dto.StudentRequestDto;
import com.example.crudproject.dto.StudentResponseDto;
import com.example.crudproject.entity.Student;
import com.example.crudproject.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/students")
public class StudentController {
    private StudentService studentservice;

    public StudentController(StudentService studentservice) {
        this.studentservice = studentservice;
    }


    @PostMapping
    public ResponseEntity<StudentResponseDto> createStudent(@Valid @RequestBody StudentRequestDto studentReqDto){


       StudentResponseDto createdstudent= studentservice.createStudent(studentReqDto);
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(createdstudent);

    }
    @GetMapping("/{id}")

    public ResponseEntity<StudentResponseDto> getstudent(@PathVariable Long id){
        StudentResponseDto studentresponce=studentservice.getstudent(id);

        return ResponseEntity.status(HttpStatus.OK).body(studentresponce);
    }
 @GetMapping
    public ResponseEntity<List<Student>> getallstudent() {
        List<Student> studentList= studentservice.getallstudent();

        return ResponseEntity.status(HttpStatus.OK).body(studentList);
    }

    @PutMapping

    public ResponseEntity<StudentResponseDto> updatestudent(@RequestParam Long id, @RequestBody Student studentReq){
        StudentResponseDto studentresponce=studentservice.updatestudent(id,studentReq);

        return ResponseEntity.ok(studentresponce);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteStudent(@RequestParam Long id){
         studentservice.deletestudent(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<String> deleteStudentsoftly(@RequestParam Long id){
        studentservice.deletestudentsoft(id);


        return ResponseEntity.noContent().build();
    }
}
