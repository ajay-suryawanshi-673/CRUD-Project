package com.example.crudproject.service;

import com.example.crudproject.dto.StudentRequestDto;
import com.example.crudproject.dto.StudentResponseDto;
import com.example.crudproject.entity.Student;
import com.example.crudproject.exception.DuplicateResourceException;
import com.example.crudproject.exception.ResourceNotFoundException;
import com.example.crudproject.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service

public class StudentService {

    private StudentRepository studentrepository;
    public StudentService(StudentRepository studentrepository) {
        this.studentrepository = studentrepository;
    }



  public StudentResponseDto createStudent(StudentRequestDto studentReqDto){

        Student student=mapToEntity(studentReqDto);

        if(emailExists(student)){
            throw new DuplicateResourceException("aleready exists");
        }

       Student studentresponse = studentrepository.save(student);
        return mapToDto(studentresponse);
  }




    public StudentResponseDto getstudent(Long id) {
        Student studentresp= studentrepository
                .findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Student With id"+id+"Not found"));
        return mapToDto(studentresp);
    }

    public List<Student> getallstudent() {
       List<Student> students= studentrepository.findByDeletedIsFalse();
       return students;
    }

    public StudentResponseDto updatestudent(Long id, Student studentReq) {
        Student existingstudent= studentrepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(()->new ResourceNotFoundException("not found"));


        existingstudent.setAge(studentReq.getAge());
        existingstudent.setName(studentReq.getName());
        existingstudent.setRollNo(studentReq.getRollNo());
        existingstudent.setEmail(studentReq.getEmail());
        existingstudent.setSubject(studentReq.getSubject());
        existingstudent.setDeleted(false);
         Student savedstudent=studentrepository.save(existingstudent);
         
         return mapToDto(savedstudent);
    }

    public void deletestudent(Long id) {
       Student studenttobedeleted=studentrepository
               .findById(id)
               .orElseThrow(()->new ResourceNotFoundException("not found"));
       studentrepository.delete(studenttobedeleted);
    }

    public void deletestudentsoft(Long id) {
          Student studenttobedeleted=studentrepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(()->new ResourceNotFoundException("not found"));
        studentrepository.delete(studenttobedeleted);


        studenttobedeleted.setDeleted(true);
          studentrepository.save(studenttobedeleted);

    }

    private Student mapToEntity(StudentRequestDto studentReqDto) {
        Student student=new Student();
        student.setName(studentReqDto.getName());
        student.setSubject(studentReqDto.getSubject());
        student.setEmail(studentReqDto.getEmail());
        student.setRollNo(studentReqDto.getRollNo());
        student.setAge(studentReqDto.getAge());
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        student.setDeleted(false);
        return student;
    }

    private StudentResponseDto mapToDto(Student studentresponse) {
        StudentResponseDto responseDto=new StudentResponseDto();

        responseDto.setAge(studentresponse.getAge());
        responseDto.setId(studentresponse.getId());
        responseDto.setEmail(studentresponse.getEmail());
        responseDto.setName(studentresponse.getName());
        responseDto.setSubject(studentresponse.getSubject());
        responseDto.setMessage("student saved");
        responseDto.setCreatedAt(studentresponse.getCreatedAt());
        responseDto.setUpdatedAt(studentresponse.getUpdatedAt());
        return responseDto;

    }
    private boolean emailExists(Student student){
        return studentrepository.existsByEmail(student.getEmail());
    }


}
