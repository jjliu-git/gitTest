package cn.itcast.domain.system;

import lombok.Data;

import java.io.Serializable;

@Data
public class Dept implements Serializable {
    private String id;

    private String deptName;

    private Dept parent;//父部门对象，用于封装父部门的所有数据

    private Integer state;

    private String companyId;

    private String companyName;

}
