package com.codewithjay.Config;

import lombok.*;

import java.io.File;


@Getter
@Setter

public class AppConstant {

    public static final String pagesize = "5";
    public static final String pagenumber = "0";
    public static final String sortvalue = "title";
    public static final String sortorder = "ascending";
    public static final String COURSE_UPLODE_BANNER = "Uplode" + File.separator +"Course"+ File.separator +"Banner";
    public static final String VIDEO_UPLODE_BANNER = "Uplode" + File.separator +"Videos"+ File.separator +"Video";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String PENDING = "PENDING";

}