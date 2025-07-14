package org.jianqiaozh.java.ai.langchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.jianqiaozh.java.ai.langchain4j.entity.Appointment;
import org.jianqiaozh.java.ai.langchain4j.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentTools {

    @Autowired
    private AppointmentService appointmentService;

    @Tool(name="预约挂号", value = "根据参数，先执行queryDepartment查询是否可预约，并直接给用户回答是否可预约，并让用户确认所有预约,如果用户没有提供具体的医生姓名，请从向量存储当中找到一位医生")
    public String bookAppointment(Appointment appointment) {
        Appointment appointmentDB = appointmentService.getOne(appointment);

        if (appointmentDB == null) {
            appointment.setId(null);
            if(appointmentService.save(appointment)) {
                return "预约成功， 并返回预约详情";
            } else {
                return "预约失败";
            }
        }

        return "您已经在相同的时间有预约";
    }

    @Tool(name = "取消预约挂号", value = "根据参数，查询预约是否已经存在，如果存在就删除预约记录并返回true，否则就返回false ")
    public String cancelAppointment(Appointment appointment) {
        Appointment appointmentDB = appointmentService.getOne(appointment);
        if (appointmentDB == null) {
            return "没有查到您的预约记录";
        } else {
            if(appointmentService.removeById(appointment.getId())) {
                return "取消预约成功";
            }
            else {
                return "取消预约失败";
            }
        }
    }

    @Tool(name = "查询是否有号源", value = "根据科室名称，日期，时间和医生查询是否有号源，并返回给用户")
    public boolean queryDepartment(
            @P(value = "科室名称") String name,
            @P(value = "日期")String date,
            @P(value = "时间：可选值为上午下午") String time,
            @P(value = "医生名称", required = false) String doctorName
    ){
        System.out.println("查询是否有号源");
        System.out.println("科室名称：" + name);
        System.out.println("日期：" + date);
        System.out.println("时间：" + time);
        System.out.println("医生名称：" + doctorName);

        // TODO 维护医生的排班信息
        // 没有指定医生的名字则根据其他的条件查询是否有其他可以预约的医生， 如果有则返回true, 否则返回false

        // 如果有指定医生，则判断这个医生有没有排班，没有的话就返回false
        // 如果有排班则判断医生排班的时间是不是已经预约满，约满了就返回false, 否则返回true

        return true;

    }


}
