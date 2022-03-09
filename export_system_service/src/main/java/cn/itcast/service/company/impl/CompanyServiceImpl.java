package cn.itcast.service.company.impl;

import cn.itcast.dao.company.CompanyDao;
import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyDao companyDao;
    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    @Override
    public void add(Company company) {
        company.setId(UUID.randomUUID().toString());
        companyDao.add(company);
    }

    @Override
    public void edit(Company company) {
        companyDao.edit(company);
    }

    @Override
    public Company findById(String id) {
        return companyDao.findById(id);
    }

    @Override
    public void delete(String id) {
        companyDao.delete(id);
    }

    @Override
    public PageInfo<Company> findByPage(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<Company> all = companyDao.findAll();
        PageInfo companyPageInfo = new PageInfo(all);
        return companyPageInfo;
    }
}
