package com.oasis.tmsv5.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;



public class GeneratorDDL {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    System.out.println("请输入类的全路径包含类名（多个类用 ,隔开）：");
	    String classNames = br.readLine();
	    if (!classNames.isEmpty()) {
	    	classNames = classNames.trim();
	 	    String [] classArray = classNames.split(",");
	 	    for (String className : classArray) {
	 	    	Class clazz = Class.forName(className);
	 			table(clazz);
	 			seq(clazz) ;
	 			primaryKey(clazz);
	 			
	 			//生成MaBatis的resultMap映射，如果不需要可以注释该行代码
	 			System.out.println(columnChange4MyBatis(clazz));
	 			//根据field生成column,如果不需要可以注释该行代码
	 			System.out.println(column(clazz));
	 	    }
	    } 
	}
	
	@SuppressWarnings("unchecked")
	private static String column(Class clazz) {
		StringBuffer classFields = new StringBuffer("---field--- \n");
		Field[] fArr = clazz.getDeclaredFields();
		for (int i = 0; i < fArr.length; i++) {
			Field f = fArr[i];
			String fieldName = f.getName();
			Class type = f.getType(); 
			StringBuffer columnName = new StringBuffer();
			for (int j = 0; j < fieldName.length(); j++) {
				char c = fieldName.charAt(j);
				if (Character.isUpperCase(c)) {
					columnName.append("_");
					columnName.append(Character.toLowerCase(c));
				} else {
					columnName.append(c);
				}
			}

			classFields.append(" @Column(name = \"" + columnName + "\")"  + "\n");
			classFields.append(" private " + type.getSimpleName() + " " + fieldName + ";\n");
			classFields.append("\r\n");
		}
		return classFields.toString();
	}
	
	@SuppressWarnings("unchecked")
	private static String columnChange4MyBatis(Class clazz) {
		StringBuffer strChange = new StringBuffer();
		String className = clazz.getSimpleName();
		strChange.append("<resultMap type=\""+className+"\" id=\""+className+"Map\">" + "\n");
		Field[] fArr = clazz.getDeclaredFields();
		for(int i=0;i<fArr.length;i++){
			Field f = fArr[i];
			if(f.isAnnotationPresent(Column.class)){
				Column column = f.getAnnotation(Column.class);
				String columnName = column.name();
				String fieldName = f.getName();
				if(!fieldName.equals(columnName)){
					strChange.append("<result property=\""+fieldName+"\" column=\""+columnName+"\"/>" + "\n");
				}
			}
		}
		strChange.append("<result property=\"domainId\" column=\"domain_id\"/>" + "\n");
		strChange.append("<result property=\"creatorId\" column=\"CREATOR_ID\"/>" + "\n");
		strChange.append("<result property=\"updatorId\" column=\"UPDATOR_ID\"/>" + "\n");
		//strChange.append("<result property=\"loginToken\" column=\"LOGIN_TOKEN\"/>" + "\n");
		strChange.append("</resultMap> \n");
		return strChange.toString();
	}

	@SuppressWarnings("unchecked")
	private static String table(Class clazz) {
		StringBuffer sql = new StringBuffer();
		if(clazz.isAnnotationPresent(Table.class)){
			//该class存在Table类型的注解，获取指定的表名
			Table table = (Table) clazz.getAnnotation(Table.class);
			String tableName = table.name();
			sql.append("--create table " + tableName + "\n");
			sql.append("create table " + tableName + " (");
		}
		Field[] fArr = clazz.getDeclaredFields();
		List<String> columnList = getColumns(fArr);
		//拼接解析后的成员变量信息成创建表语句
		for(int i=0;i<columnList.size();i++){
			if(i==(columnList.size()-1)){
				sql.append("\n"+columnList.get(i)+")");
			}else{
				sql.append("\n"+columnList.get(i)+",");
			}
		}
		sql.append(";");
		sql.append("\n");
		System.out.println(sql.toString());
		return sql.toString();
	}
	
	/**
	 * 用来解析所有成员变量的方法
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getColumns(Field[] fArr){
		List<String> result = new ArrayList<String>();
		String columnName="";
		String columnLength= "";
		String columnType = "";
		for(int i=0;i<fArr.length;i++){
			Field f = fArr[i];
			columnName = f.getName();
			if("serialVersionUID".equalsIgnoreCase(columnName)){
                continue;
            }
			boolean haveLength = true;
			if(f.isAnnotationPresent(Id.class)){
				//columnName = f.getName();
				columnLength = "19";
				String str = columnName+" number"+"("+columnLength+")";
				result.add(str);
			}else{
				if(f.isAnnotationPresent(Column.class)){
					columnName = f.getAnnotation(Column.class).name();
				}/*else{
					columnName = f.getName();
				}*/
				Class type = f.getType();
				if(Integer.class == type || Long.class == type){
					columnLength = "19";
					columnType = "number";
				} else if (Float.class == type || Double.class == type ) {
					columnLength = "19,4";
					columnType = "number";
				} else if(Date.class == type){
					if(f.isAnnotationPresent(Temporal.class)){
						if("TIMESTAMP".equalsIgnoreCase(f.getAnnotation(Temporal.class).value().toString())){
							columnLength = "6";
							columnType = "timestamp";
						}else if("DATE".equalsIgnoreCase(f.getAnnotation(Temporal.class).value().toString())){
							columnType = "DATE";
							haveLength = false;
						}
					}else{
						haveLength = false;
						columnType = "DATE";
					}
					
				}else{
					columnLength = "255";
					columnType = "varchar2";
				} 
				String strColumnLength = "";
				if( haveLength ){
					strColumnLength = " ( "+columnLength+" ) ";
				}
				String str = columnName+" "+columnType + strColumnLength;
				result.add(str);
			}
		}
		result.add("domain_id number(19) ");
		result.add("createdtime timestamp(6) ");
		result.add("updatedtime timestamp(6) ");
		result.add("creator_id number(19) ");
		result.add("updator_id number(19) ");
		result.add("lockversion number(19) ");
		
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private static String seq(Class clazz) {
		StringBuffer sequence = new StringBuffer();
		if (clazz.isAnnotationPresent(SequenceGenerator.class)) {
			// 该class存在Table类型的注解，获取指定的表名
			SequenceGenerator seq = (SequenceGenerator) clazz
					.getAnnotation(SequenceGenerator.class);
			String seqName = seq.name();
			sequence.append("--create sequence " + seqName + "\n");
			sequence.append("create sequence " + seqName );
			sequence.append(" minvalue 1 " );
			sequence.append(" maxvalue 999999999999999999999999999 ");
			sequence.append(" start with 60000 ");
			sequence.append(" increment by 1 ");
			sequence.append(" cache 20; ");
			sequence.append("\n");
		}
		System.out.println(sequence.toString());
		return sequence.toString();
	}
	
	@SuppressWarnings("unchecked")
	private static String primaryKey(Class clazz) {
		 StringBuffer key = new StringBuffer();
		  
		if (clazz.isAnnotationPresent(Table.class)) {
			// 该class存在Table类型的注解，获取指定的表名
			Table table = (Table) clazz
					.getAnnotation(Table.class);
			String tableName = table.name();
			key.append("--create key " + key + "\n");
			key.append("alter table "+ tableName +" add primary key (ID);");
			key.append("\n");
		}
		System.out.println(key.toString());
		return key.toString();
	}
	
}
