package cn.edu.nju.software.jzs.entity;

import javax.persistence.*;

/**
 * Created by emengjzs on 2016/4/6.
 */
@Entity
public class RunNo {
    @Id
    @GeneratedValue
    private Long id;

    private Long no;

    @OneToOne(cascade= CascadeType.REFRESH,optional=false)
    @JoinColumn(name = "job_id")
    private Job job;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }


    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
