package com.oasis.tmsv5.util.tools;

import java.util.Scanner;

public class AsGenerator {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner input = new Scanner( System.in ); // 用System.in初始化Scanner
        System.out.println("请输入java文件绝对路径：");
        String fullName = input.next().replaceAll("\\\\", "/");  // 从控制台读取输入
        System.out.println("请输入as文件包名:");
        String pack = input.next();
        String path = fullName.substring(0,fullName.lastIndexOf("/")+1);
        
        String name = fullName.substring(fullName.lastIndexOf("/")+1,fullName.length());
        
        Class2As ca = new Class2As();
        ca.setFileName(name);
        ca.setPath(path);
        ca.setPackageName(pack);
        ca.generateFromFile();
    }

}
