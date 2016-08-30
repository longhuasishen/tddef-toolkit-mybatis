package com.tangdi.def.toolkit.mybatis.generator;

import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

/***
 * 生成实体bean的注释
 * @author li.sy
 * 2016-04-23 15:17:23
 */
public class TdCommentGenerator implements CommentGenerator{

	/**
     * 给字段添加数据库备注
     *
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/**");
            StringBuilder sb = new StringBuilder();
            sb.append(" * ");
            sb.append(introspectedColumn.getRemarks());
            field.addJavaDocLine(sb.toString());
            field.addJavaDocLine(" */");
        }
    }

	@Override
	public void addClassComment(InnerClass arg0, IntrospectedTable arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addClassComment(InnerClass arg0, IntrospectedTable arg1,
			boolean arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addComment(XmlElement arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addConfigurationProperties(Properties arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEnumComment(InnerEnum arg0, IntrospectedTable arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFieldComment(Field arg0, IntrospectedTable arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addGeneralMethodComment(Method arg0, IntrospectedTable arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addGetterComment(Method arg0, IntrospectedTable arg1,
			IntrospectedColumn arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addJavaFileComment(CompilationUnit arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRootComment(XmlElement arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSetterComment(Method arg0, IntrospectedTable arg1,
			IntrospectedColumn arg2) {
		// TODO Auto-generated method stub
		
	}

}
