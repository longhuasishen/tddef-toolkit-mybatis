/*@(#)TdMapperPlugin.java   2015-11-6 
 * Copy Right 2015 Bank of Communications Co.Ltd.
 * All Copyright Reserved
 */

package com.tangdi.def.toolkit.mybatis.generator;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SimpleSelectAllElementGenerator;

/**
 * TODO Document TdMapperPlugin
 * <p>
 * @version 1.0.0,2015-11-6
 * @author li.sy
 * @since 1.0.0
 */
public class TdMapperPlugin extends PluginAdapter{

	public boolean validate(List<String> warnings){
	    return true;
	}
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable){
		MapperGenerator generator = new MapperGenerator();
	    generator.setContext(this.context);
	    generator.setIntrospectedTable(introspectedTable);
	    List<CompilationUnit> units = generator.getCompilationUnits();
	    List generatedFile = new ArrayList();
	    for (CompilationUnit unit : units) {
	      GeneratedJavaFile gjf = new GeneratedJavaFile(unit, this.context.getJavaModelGeneratorConfiguration().getTargetProject(), 
	        this.context.getProperty("javaFileEncoding"), this.context.getJavaFormatter());
	      generatedFile.add(gjf);
	    }
	    return generatedFile;
	}
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable){
		String pack = this.context.getProperty("mapperTargetPackage");
	    document.getRootElement().getAttributes().clear();
	    document.getRootElement().addAttribute(new Attribute("namespace", pack + "." +introspectedTable.getFullyQualifiedTable().getDomainObjectName()+"Dao"));
	    SimpleSelectAllElementGenerator generator = new SimpleSelectAllElementGenerator();
	    generator.setContext(this.context);
	    generator.setIntrospectedTable(introspectedTable);
	    generator.addElements(document.getRootElement());
	    return true;
	}
}