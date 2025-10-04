package com.sky.mapper;

import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
//    简单的可以用MyBatis的注解来完成
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

}
