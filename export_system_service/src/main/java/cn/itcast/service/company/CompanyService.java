package cn.itcast.service.company;

import cn.itcast.domain.company.Company;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CompanyService {
    /**
     * 查询所有
     */
    public List<Company> findAll();

    void add(Company company);

    void edit(Company company); //

    Company findById(String id);

    void delete(String id);

    PageInfo<Company> findByPage(int pageSize, int pageNum);
}
