/*@(#)MapperGenerator.java   2015-11-6 
 * Copy Right 2015 Bank of Communications Co.Ltd.
 * All Copyright Reserved
 */

package com.tangdi.def.toolkit.mybatis.generator;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

/**
 * TODO Document MapperGenerator
 * <p>
 * @version 1.0.0,2015-11-6
 * @author li.sy
 * @since 1.0.0
 */
public class MapperGenerator extends AbstractJavaGenerator{
	public List<CompilationUnit> getCompilationUnits()
	  {
	    String pack = this.context.getProperty("mapperTargetPackage");
	    System.out.println(pack);
	    FullyQualifiedJavaType type = new FullyQualifiedJavaType(pack + "." + this.introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Dao");

	    int size = this.introspectedTable.getPrimaryKeyColumns().size();
	    String primaryKeyType = "com.tangdi.def.toolkit.mybatis.data.NoId";
	    if (size != 0) {
	      primaryKeyType = (size > 1) ? this.introspectedTable.getPrimaryKeyType() : ((IntrospectedColumn)this.introspectedTable.getPrimaryKeyColumns().get(0)).getFullyQualifiedJavaType().toString();
	    }
	    Interface interfaze = new Interface(type);
	    interfaze.setVisibility(JavaVisibility.PUBLIC);
	    interfaze.addImportedType(new FullyQualifiedJavaType("com.tangdi.def.toolkit.mybatis.data.BaseDao"));
	    if(this.introspectedTable.getAllColumns().size() > size){
	    	interfaze.addImportedType(new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType()));
	    }
	    if (size == 0) {
	      interfaze.addImportedType(new FullyQualifiedJavaType("com.tangdi.def.toolkit.mybatis.data.NoId"));
	    }
	    else if (size > 1) {
	      interfaze.addImportedType(new FullyQualifiedJavaType(this.introspectedTable.getPrimaryKeyType()));
	    }
	    
	    if(this.introspectedTable.getAllColumns().size() == size){
	    	interfaze.addSuperInterface(new FullyQualifiedJavaType("com.tangdi.def.toolkit.mybatis.data.BaseDao<" + primaryKeyType + "," + primaryKeyType + ">"));
	    }else{
	    	interfaze.addSuperInterface(new FullyQualifiedJavaType("com.tangdi.def.toolkit.mybatis.data.BaseDao<" + this.introspectedTable.getBaseRecordType() + "," + primaryKeyType + ">"));
	    }
	    List answer = new ArrayList();
	    answer.add(interfaze);
	    return answer;
	  }
}
