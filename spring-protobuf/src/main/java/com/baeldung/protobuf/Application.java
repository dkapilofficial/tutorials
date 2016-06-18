package com.baeldung.protobuf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.baeldung.protobuf.BaeldungTraining.Course;
import com.baeldung.protobuf.BaeldungTraining.Student;
import com.baeldung.protobuf.BaeldungTraining.Student.PhoneNumber;
import com.baeldung.protobuf.BaeldungTraining.Student.PhoneType;

@SpringBootApplication
public class Application {
    @Bean
    RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
        return new RestTemplate(Arrays.asList(hmc));
    }

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    @Bean
    public CourseRepository createDummyCourses() {
        Map<Integer, Course> dummy = new HashMap<>();
        
        Course course1 = Course.newBuilder().setId(1).setCourseName("REST with Spring").addAllStudent(createDummyStudents()).build();
        Course course2 = Course.newBuilder().setId(2).setCourseName("Learn Spring Security").addAllStudent(new ArrayList<Student>()).build();
        
        dummy.put(course1.getId(), course1);
        dummy.put(course2.getId(), course2);
        
        return new CourseRepository(dummy);
    }

    private List<Student> createDummyStudents() {
        List<Student> studentList = new ArrayList<>();

        PhoneNumber phone1 = createPhone("123456", PhoneType.MOBILE);
        Student student1 = createStudent(1, "John", "Doe", "john.doe@baeldung.com", Arrays.asList(phone1));

        PhoneNumber phone2 = createPhone("234567", PhoneType.LANDLINE);
        Student student2 = createStudent(2, "Richard", "Roe", "richard.roe@baeldung.com", Arrays.asList(phone2));

        PhoneNumber phone3_1 = createPhone("345678", PhoneType.MOBILE);
        PhoneNumber phone3_2 = createPhone("456789", PhoneType.LANDLINE);
        Student student3 = createStudent(3, "Jane", "Doe", "jane.doe@baeldung.com", Arrays.asList(phone3_1, phone3_2));

        studentList.addAll(Arrays.asList(student1, student2, student3));
        return studentList;
    }

    private Student createStudent(int id, String firstName, String lastName, String email, List<PhoneNumber> phones) {
        return Student.newBuilder().setId(id).setFirstName(firstName).setLastName(lastName).setEmail(email).addAllPhone(phones).build();
    }

    private PhoneNumber createPhone(String number, PhoneType type) {
        return PhoneNumber.newBuilder().setNumber(number).setType(type).build();
    }
}