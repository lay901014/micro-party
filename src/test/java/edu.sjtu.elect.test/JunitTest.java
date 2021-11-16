package edu.sjtu.party.test;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @description:
 * @author: lay
 * @create: 2021/10/08 17:38
 **/
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:context.xml"})
@WebAppConfiguration
public class JunitTest {

    @Test
    public void test() {

//        List<HospitalVo> l = appointmentService.getHospitalAppointmentInfo("1");

//        System.out.println(l.size());
    }
}
