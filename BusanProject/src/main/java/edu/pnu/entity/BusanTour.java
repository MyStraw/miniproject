package edu.pnu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "busantour")
public class BusanTour {

    @Id
    private Integer id;

    private String name;
    private String gugun;
    private Double lat;
    private Double lng;
    private String plage;
    private String cate_with;
    private String cate_season;
    private String cate_nature;
    private String cate_plan;
    private String tags;
    private String title;
    private String subtitle;
    private String main_place;
    private String addr;
    private String itemcntnts;
    private String trfc_info;
    private String usage_day;    
    private String hldy_info;
    private String usage_time;
    private String usage_amou;
    private String middle_siz;
    private String main_img_n;
    private String main_img_t;
    private Integer likecnt;
    private String type;
    
    public String getPostid() {
        return getId().toString();
    }
    
    
}