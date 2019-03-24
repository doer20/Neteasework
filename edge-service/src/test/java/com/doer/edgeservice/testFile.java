package com.doer.edgeservice;

import java.io.File;

public class testFile {

    public boolean mkdir(){
        String path = "/usr/local/neteasework/img";
        File file = new File(path);
        boolean flag = false;
        if (!file.exists()){
            flag = file.mkdirs();
        }
        return flag;
    }

    public static void main(String[] args){
        System.out.println(new testFile().mkdir());
    }
}
