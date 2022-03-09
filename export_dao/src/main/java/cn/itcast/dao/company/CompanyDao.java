package cn.itcast.dao.company;

import cn.itcast.domain.company.Company;

import java.util.List;

public interface CompanyDao {
    public List<Company> findAll();

    void edit(Company company);

    void add(Company company);

    Company findById(String id);

    void delete(String id);

}
