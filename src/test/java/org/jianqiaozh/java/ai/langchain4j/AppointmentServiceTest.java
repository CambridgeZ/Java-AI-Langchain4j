package org.jianqiaozh.java.ai.langchain4j;

import org.jianqiaozh.java.ai.langchain4j.entity.Appointment;
import org.jianqiaozh.java.ai.langchain4j.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppointmentServiceTest {

    @Autowired
    private AppointmentService appointmentService;

    @Test
    void testSave(){
        Appointment appointment = new Appointment();
        appointment.setUsername("张三");
        appointment.setIdCard("123456789123456789");
        appointment.setDepartment("内科");
        appointment.setDate("2025-7-11");
        appointment.setTime("上午");
        appointment.setDoctorName("李四");
        appointmentService.save(appointment);
    }

    @Test
    void testGetOne(){
        Appointment appointment = new Appointment();
        appointment.setUsername("张三");
        appointment.setIdCard("123456789123456789");
        appointment.setDepartment("内科");
        appointment.setDate("2025-7-11");
        appointment.setTime("上午");
        appointment.setDoctorName("李四");

        Appointment appointmentDB = appointmentService.getOne(appointment);
        System.out.println(appointmentDB);
    }

    @Test
    void testRemoveById(){
        appointmentService.removeById(1);
    }



}
