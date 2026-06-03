package hireSystem.vo;

import java.util.Date;
import lombok.Data;

@Data
public class HireSkillVo {
    private Integer skillId;
    private Integer resumeId;
    private String  skillName;
    private Date    regDt;
}
