package com.atxbai.online.common.constants;

public class Constant {

    public static final Integer RESUME_AUDIT_SUCCESS = 1;

    public static final Integer RESUME_NO_AUDIT=0;

    public static final Integer RESUME_REFUSE = -1;

    //隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
    public static final int BATCH_COUNT = 100;


}
