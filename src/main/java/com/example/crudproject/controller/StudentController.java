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


    @PostMapping("/create")
    public ResponseEntity<StudentResponseDto> createStudent(@Valid @RequestBody StudentRequestDto studentReqDto){


       StudentResponseDto createdstudent= studentservice.createStudent(studentReqDto);
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(createdstudent);

    }
    @GetMapping("/get")

    public ResponseEntity<Student> getstudent( @RequestParam Long id){
        Student studentresponce=studentservice.getstudent(id);
        if(studentresponce==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentresponce);
    }
 @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getallstudent() {
        List<Student> studentList= studentservice.getallstudent();
        if (studentList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentList);
    }

    @PutMapping("/update")

    public ResponseEntity<Student> updatestudent( @RequestParam Long id,@RequestBody Student studentReq){
        Student studentresponce=studentservice.updatestudent(id,studentReq);
        if(studentresponce==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(studentresponce);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam Long id){
        Boolean isDeleted= studentservice.deletestudent(id);
        if (!isDeleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("deleted");
    }

    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<String> deleteStudentsoftly(@RequestParam Long id){
        Boolean isDeleted=studentservice.deletestudentsoft(id);

        if(!isDeleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("deleted");
    }
}
