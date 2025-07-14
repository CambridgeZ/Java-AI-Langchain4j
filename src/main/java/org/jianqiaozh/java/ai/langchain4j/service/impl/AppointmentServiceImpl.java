package org.jianqiaozh.java.ai.langchain4j.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jianqiaozh.java.ai.langchain4j.entity.Appointment;
import org.jianqiaozh.java.ai.langchain4j.mapper.AppointmentMapper;
import org.jianqiaozh.java.ai.langchain4j.service.AppointmentService;
import org.springframework.stereotype.Service;


@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {
    /**
     *  查询订单是否存在
     * @param appointment
     * @return
     */

    @Override
    public Appointment getOne(Appointment appointment) {
        LambdaQueryWrapper<Appointment> queryWrapper = new LambdaQueryWrapper<Appointment>();

        queryWrapper.eq(Appointment::getUsername, appointment.getUsername());
        queryWrapper.eq(Appointment::getIdCard, appointment.getIdCard());
        queryWrapper.eq(Appointment::getDepartment, appointment.getDepartment());
        queryWrapper.eq(Appointment::getDate, appointment.getDate());
        queryWrapper.eq(Appointment::getTime, appointment.getTime());

//        System.out.println("已经执行查询信息其中查询" + appointment);

        Appointment appointmentDB = baseMapper.selectOne(queryWrapper);

//        System.out.println(appointmentDB);

        return appointmentDB;
    }
}
