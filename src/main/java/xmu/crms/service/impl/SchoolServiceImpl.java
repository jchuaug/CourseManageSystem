package xmu.crms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.crms.entity.School;
import xmu.crms.mapper.SchoolMapper;
import xmu.crms.service.SchoolService;

import java.math.BigInteger;
import java.util.List;

/**
 * @author no one
 */
@Service
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    private SchoolMapper schoolMapper;

    @Override
    public List<School> listSchoolByCity(String city) {
        return schoolMapper.listSchoolByCity(city);
    }


    @Override
    public BigInteger insertSchool(School school) {
        schoolMapper.insertSchool(school);
        return school.getId();
    }


    @Override
    public List<String> listProvince() {
        return schoolMapper.listProvince();
    }


    @Override
    public List<String> listCity(String province) {
        return schoolMapper.listCity(province);

    }


    @Override
    public School getSchoolBySchoolId(BigInteger SchoolId) {
        return schoolMapper.getSchoolBySchoolId(SchoolId);

    }
}
