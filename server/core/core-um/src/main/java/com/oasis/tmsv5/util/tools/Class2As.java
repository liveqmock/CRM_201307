package com.oasis.tmsv5.util.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Class2As {

    private static final String NEW_LINE = "\r\n";

    private static final String TAB = "    ";

    private static final String SPAC = " ";

    private static final String IMPORT_BASEVO = "    import com.oasis.tmsv5.vo.BaseVO;";

    private static final String IMPORT_COLL = "    import mx.collections.ArrayCollection;";

    private static final String BINDABLE = "    [Bindable]\r\n";

    private static final String REMOTE = "    [RemoteClass(alias=\"?\")]\r\n";

    private static final String CLASS = "    public class ? extends com.oasis.tmsv5.vo.BaseVO\r\n";

    private static final String CONTRUCTER = "        public function ?(){}";

    private String fileName;

    private String path;

    private String packageName;

    private StringBuilder asLine = new StringBuilder();;

    public void generateFromFile() {
        try {
            FileReader fr = new FileReader(path + "/" + fileName);
            BufferedReader br = new BufferedReader(fr);
            String classLine = "";
            writeHead();
            while ((classLine = br.readLine()) != null) {
                if (!classLine.trim().isEmpty() && classLine != null) {
                    convertLine(classLine.trim());
                }
            }
            writeEnd();
            wiriteFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void wiriteFile() {
        String asname = path + getASName()+".as";
        File filename = new File(asname);
        try {
            filename.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RandomAccessFile mm = null;
        try {
            mm = new RandomAccessFile(filename, "rw");
            mm.writeBytes(asLine.toString());
            System.out.println("生成文件"+filename);
        } catch (IOException e1) {
            // TODO 自动生成 catch 块
            e1.printStackTrace();
        } finally {
            if (mm != null) {
                try {
                    mm.close();
                } catch (IOException e2) {
                    // TODO 自动生成 catch 块
                    e2.printStackTrace();
                }
            }
        }
    }

    private void writeHead() {
        asLine.append("package").append(SPAC).append(packageName).append(NEW_LINE);
        appendLeftBrackets();
        asLine.append(IMPORT_BASEVO).append(NEW_LINE);
        asLine.append(NEW_LINE);
        asLine.append(IMPORT_COLL).append(NEW_LINE);
        asLine.append(NEW_LINE);
        asLine.append(BINDABLE);
        asLine.append(REMOTE.replaceAll("\\?", getRemoteClassName()));
        asLine.append(CLASS.replaceAll("\\?", getASName()));
        asLine.append(TAB);
        appendLeftBrackets();
        asLine.append(NEW_LINE);
        asLine.append(CONTRUCTER.replaceAll("\\?", getASName()));
        asLine.append(NEW_LINE);
    }

    private void writeEnd() {
        asLine.append("     }");
        asLine.append(NEW_LINE);
        appendRightBrackets();
    }

    private String getRemoteClassName() {
        String fullName = path + fileName;
        fullName = fullName.replace('/', '.');
        int begainIndex = fullName.indexOf("com.");
        int endIndex = fullName.lastIndexOf(".java");
        fullName = fullName.substring(begainIndex, endIndex);
        return fullName;
    }

    private String getASName() {
        return fileName.substring(0, fileName.indexOf("VO"));
    }

    /**
     * 根据java文件中的属性生成as文件
     * 
     * @param asLine
     * @param classLine
     */
    private void convertLine(String classLine) {
        switch (getLineType(classLine)) {
        case PACKAGE:
            appendPackage(classLine);
            return;
        case CLASS:
            appendCalss(classLine);
            return;
        case FIELD:
            appendField(classLine);
            return;
        case OTHER:
            return;
        }
    }

    private void appendPackage(String classLine) {
        return;
    }

    private void appendCalss(String classLine) {
        return;
    }

    private void appendField(String classLine) {
        String[] split = splitLine(classLine);
        String newLine = "        public var $:?;\r\n";
        newLine = newLine.replaceAll("\\$", split[1]);
        if (split[0].equalsIgnoreCase("string")) {
            newLine = newLine.replaceAll("\\?", "String");
        } else if (split[0].equalsIgnoreCase("boolean")) {
            newLine = newLine.replaceAll("\\?", "Boolean");
        } else if (split[0].equalsIgnoreCase("date") || split[0].equalsIgnoreCase("timestamp")) {
            newLine = newLine.replaceAll("\\?", "Date");
        } else if (split[0].equalsIgnoreCase("long") || split[0].equalsIgnoreCase("double") || split[0].equalsIgnoreCase("int")
                || split[0].equalsIgnoreCase("Integer") || split[0].equalsIgnoreCase("Float")) {
            newLine = newLine.replaceAll("\\?", "Number");

        } else if (split[0].indexOf("Set") != -1 || split[0].indexOf("List") != -1) {
            newLine = newLine.replaceAll("\\?", "ArrayCollection");
        }
        asLine.append(newLine);
    }

    private String[] splitLine(String classLine) {
        String[] ret = { "", "" };
        int space_pos_1 = classLine.indexOf(" ");
        int space_pos_2 = classLine.lastIndexOf(" ");
        ret[0] = classLine.substring(space_pos_1, space_pos_2).trim();
        ret[1] = classLine.substring(space_pos_2, classLine.length() - 1).trim();
        return ret;
    }

    private void appendLeftBrackets() {
        asLine.append("{").append(NEW_LINE);
    }

    private void appendRightBrackets() {
        asLine.append("}").append(NEW_LINE);
    }

    /**
     * 判断语句类型
     * 
     * @param classLine
     * @return
     */
    private LineType getLineType(String classLine) {
        if (classLine.trim().startsWith("package")) {
            return LineType.PACKAGE;
        } else if (classLine.indexOf("serialVersionUID") != -1) {
            return LineType.OTHER;
        } else if (classLine.trim().startsWith("public class")) {
            return LineType.CLASS;
        } else if (classLine.trim().startsWith("import")) {
            return LineType.IMPORT;
        } else if (classLine.trim().startsWith("private")) {
            return LineType.FIELD;
        } else if (classLine.trim().startsWith("public")) {
            return LineType.METHOD;
        }
        return LineType.OTHER;
    }

    public void generateFromPath() {

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public StringBuilder getAsLine() {
        return asLine;
    }

    public void setAsLine(StringBuilder asLine) {
        this.asLine = asLine;
    }

    public static void main(String[] args) {
        Class2As test = new Class2As();
        test.setFileName("test.java");
        test.setPath("D:/works/workspace/tmsv5/server-part/common/src/main/java/com/oasis/tmsv5/common/vo/security/");
        test.setFileName("AccountVO.java");
        test.setPackageName("com.oasis.tmsv5.vo.security");
        test.generateFromFile();
        System.out.println(test.getAsLine().toString());
    }

}
