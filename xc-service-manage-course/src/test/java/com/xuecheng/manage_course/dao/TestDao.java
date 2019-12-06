package com.xuecheng.manage_course.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDao {
    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeachplanMapper teachplanMapper;
    @Test
    public void testCourseBaseRepository(){
        Optional<CourseBase> optional = courseBaseRepository.findById("402885816240d276016240f7e5000002");
        if(optional.isPresent()){
            CourseBase courseBase = optional.get();
            System.out.println(courseBase);
        }

    }

    @Test
    public void testCourseMapper(){
        CourseBase courseBase = courseMapper.findCourseBaseById("402885816240d276016240f7e5000002");
        System.out.println(courseBase);

    }
    @Test
    public void testFindTeachplan(){
        TeachplanNode teachplanNode = teachplanMapper.selectList("4028e581617f945f01617f9dabc40000");
        System.out.println(teachplanNode);
    }
    @Test
    public void testPageHelper(){
        //查询第1页，每页显示10条记录
        PageHelper.startPage(2,10);
        Page<CourseBase> courseList = courseMapper.findCourseList();

        List<CourseBase> result = courseList.getResult();

        long total = courseList.getTotal();

        System.out.println(result);
    }

    @Test
    public void testBeanUtils(){
        CoursePub coursePub=new CoursePub();
        coursePub.setPic("group1");

        CoursePub coursePub2=new CoursePub();
        coursePub2.setPic("group2");

        BeanUtils.copyProperties(coursePub, coursePub2);

        System.out.println(coursePub2);

    }

    public static void main(String[] args) {
        CoursePub coursePub=new CoursePub();
        coursePub.setPic(null);

        CoursePub coursePub2=new CoursePub();
        coursePub2.setPic("group2");

        BeanUtils.copyProperties(coursePub, coursePub2);

        System.out.println(coursePub2);
    }
}
