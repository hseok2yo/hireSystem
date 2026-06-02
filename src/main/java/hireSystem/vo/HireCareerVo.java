package hireSystem.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class HireCareerVo {
	private Integer careerId;
	private Integer resumeId;
	private String companyName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;

	private String currentYn;

	private String jobTitle;
	private String department;
	private String positionName;

	private String duties;

	private Date regDt;
	private Date updDt;
	private String duration;
}
