package com.example.crudproject.service;

import com.example.crudproject.dto.StudentRequestDto;
import com.example.crudproject.dto.StudentResponseDto;
import com.example.crudproject.entity.Student;
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
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
       Student studentresponse = studentrepository.save(student);
        return mapToDto(studentresponse);
  }




    public Student getstudent(Long id) {
        Optional<Student> studentresp=studentrepository.findByIdAndIsDeletedFalse(id);
        if(studentresp.isPresent()){
            return studentresp.get();
        }
        return null;
    }

    public List<Student> getallstudent() {
       List<Student> students= studentrepository.findByDeletedIsFalse();
       return students;
    }

    public Student updatestudent(Long id, Student studentReq) {
        Optional<Student> existingstudent= studentrepository.findByIdAndIsDeletedFalse(id);
        if(existingstudent.isEmpty()){
            return null;
        }
        Student studenttosave = existingstudent.get();
        studenttosave.setAge(studentReq.getAge());
        studenttosave.setName(studentReq.getName());
        studenttosave.setRollNo(studentReq.getRollNo());
        studenttosave.setEmail(studentReq.getEmail());
        studenttosave.setSubject(studentReq.getSubject());
        studenttosave.setDeleted(false);
       return  studentrepository.save(studenttosave);
    }

    public Boolean deletestudent(Long id) {
       Boolean isStudent = studentrepository.existsById(id);

       if(!isStudent) return false;
       studentrepository.deleteById(id);
       return true;
    }

    public Boolean deletestudentsoft(Long id) {
          Optional<Student> existingStudent=
                  studentrepository.findByIdAndIsDeletedFalse(id);

          if(existingStudent.isEmpty()){
              return false;
          }
          Student studentToSave=existingStudent.get();
          studentToSave.setDeleted(true);
          studentrepository.save(studentToSave);
          return true;
    }

    private Student mapToEntity(StudentRequestDto studentReqDto) {
        Student student=new Student();
        student.setName(studentReqDto.getName());
        student.setSubject(studentReqDto.getSubject());
        student.setEmail(studentReqDto.getEmail());
        student.setRollNo(studentReqDto.getRollNo());
        student.setAge(studentReqDto.getAge());

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


}
