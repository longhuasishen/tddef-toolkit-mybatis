package com.tangdi.def.toolkit.mybatis.generator;

import java.util.Iterator;
import java.util.List;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.config.Context;

/***
 * add pageation sql in mapper.xml,only support mysql and oracle db
 * @author li.sy
 * 2016-04-22 14:04:21
 */
public class AddPaginationElementGenerator extends AbstractXmlElementGenerator
{
  public void addElements(XmlElement parentElement)
  {
	//add base_column_list when columns = primarykeycolumns
	if(this.introspectedTable.getAllColumns().size() == this.introspectedTable.getPrimaryKeyColumns().size()){
		XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id", introspectedTable.getBaseColumnListId()));
        context.getCommentGenerator().addComment(answer);
        StringBuilder sb = new StringBuilder();
        Iterator iter = introspectedTable.getNonBLOBColumns().iterator();
        do
        {
            if(!iter.hasNext())
                break;
            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase((IntrospectedColumn)iter.next()));
            if(iter.hasNext())
                sb.append(", ");
            if(sb.length() > 80)
            {
                answer.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
        } while(true);
        if(sb.length() > 0)
            answer.addElement(new TextElement(sb.toString()));
        if(context.getPlugins().sqlMapBaseColumnListElementGenerated(answer, introspectedTable))
            parentElement.addElement(answer);
    }
	//add header sql
    XmlElement sqlHeader = new XmlElement("sql");
    sqlHeader.addAttribute(new Attribute("id", "pageHeader"));
    this.context.getCommentGenerator().addComment(sqlHeader);

    XmlElement chooseElement = new XmlElement("choose");
    XmlElement whenElement = new XmlElement("when");
    whenElement.addAttribute(new Attribute("test", "_databaseId == 'mysql'"));
    chooseElement.addElement(whenElement);
    
    XmlElement otherElement = new XmlElement("otherwise");
    XmlElement ifElement = new XmlElement("if");
    ifElement.addAttribute(new Attribute("test", "start != null and end != null"));
    ifElement.addElement(new TextElement("select * from (select a.*, rownum rn from ("));
    otherElement.addElement(ifElement);
    
    chooseElement.addElement(otherElement);
    sqlHeader.addElement(chooseElement);
    parentElement.addElement(sqlHeader);
    
    //add footer sql
    XmlElement sqlFooter = new XmlElement("sql");
    sqlFooter.addAttribute(new Attribute("id", "pageFooter"));
    this.context.getCommentGenerator().addComment(sqlFooter);

    XmlElement chooseElement2 = new XmlElement("choose");
    
    XmlElement whenElement2 = new XmlElement("when");
    whenElement2.addAttribute(new Attribute("test", "_databaseId == 'mysql'"));
    XmlElement ifElementMySql = new XmlElement("if");
    ifElementMySql.addAttribute(new Attribute("test", "start != null and limit != null"));
    ifElementMySql.addElement(new TextElement("limit ${start}, ${limit}"));
    whenElement2.addElement(ifElementMySql);
    chooseElement2.addElement(whenElement2);
    
    XmlElement otherElement2 = new XmlElement("otherwise");
    XmlElement ifElement2 = new XmlElement("if");
    ifElement2.addAttribute(new Attribute("test", "start != null and end != null"));
    ifElement2.addElement(new TextElement(") a where rownum &lt;= #{end}) where rn &gt;= #{start}"));
    otherElement2.addElement(ifElement2);
    
    chooseElement2.addElement(otherElement2);
    sqlFooter.addElement(chooseElement2);
    parentElement.addElement(sqlFooter);
    
    //selectAllByPager
    XmlElement pageElement = new XmlElement("select");

    pageElement.addAttribute(new Attribute("id", "selectByPager"));
    pageElement.addAttribute(
      new Attribute("resultMap", 
      this.introspectedTable.getBaseResultMapId()));
    pageElement.addAttribute(new Attribute("parameterType", "map"));
    this.context.getCommentGenerator().addComment(pageElement);

    XmlElement includeHeadElement = new XmlElement("include");
    includeHeadElement.addAttribute(new Attribute("refid", "pageHeader"));
    pageElement.addElement(includeHeadElement);
    pageElement.addElement(new TextElement("select"));
    XmlElement basicMapElement = new XmlElement("include");
    basicMapElement.addAttribute(new Attribute("refid", "Base_Column_List"));
    pageElement.addElement(basicMapElement);
    StringBuilder sb = new StringBuilder();
    sb.setLength(0);
    sb.append(" from ");
    sb.append(this.introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
    pageElement.addElement(new TextElement(sb.toString()));
    
    XmlElement whereElement = new XmlElement("trim");
    whereElement.addAttribute(new Attribute("prefix", "WHERE"));
    whereElement.addAttribute(new Attribute("prefixOverrides", "AND"));
    pageElement.addElement(whereElement);
    
    List columns = this.introspectedTable.getAllColumns();
    for(int i = 0 ; i < columns.size() ; i++ ){
    	IntrospectedColumn column = (IntrospectedColumn)columns.get(i);
    	String columnName = column.getActualColumnName();
    	XmlElement ifExistElement = new XmlElement("if");
    	ifExistElement.addAttribute(new Attribute("test ", columnName + " !=null"));
    	sb.setLength(0);
        sb.append(" and ").
           append(columnName).
           append(" = #{").
           append(columnName).
           append(",jdbcType=").
           append(column.getJdbcTypeName()).
           append("}");
    	ifExistElement.addElement(new TextElement(sb.toString()));
    	whereElement.addElement(ifExistElement);
    }
    XmlElement includeFooterElement = new XmlElement("include");
    includeFooterElement.addAttribute(new Attribute("refid", "pageFooter"));
    pageElement.addElement(includeFooterElement);
    parentElement.addElement(pageElement);
    
    addCondition(parentElement);
  }
  /***
   * 添加条件查询
   * @param parentElement
   */
  public void addCondition(XmlElement parentElement){
	//selectAllByPager
	    XmlElement pageElement = new XmlElement("select");

	    pageElement.addAttribute(new Attribute("id", "selectByCondition"));
	    pageElement.addAttribute(
	      new Attribute("resultMap", 
	      this.introspectedTable.getBaseResultMapId()));
	    pageElement.addAttribute(new Attribute("parameterType", "map"));
	    this.context.getCommentGenerator().addComment(pageElement);


	    pageElement.addElement(new TextElement("select"));
	    XmlElement basicMapElement = new XmlElement("include");
	    basicMapElement.addAttribute(new Attribute("refid", "Base_Column_List"));
	    pageElement.addElement(basicMapElement);
	    StringBuilder sb = new StringBuilder();
	    sb.setLength(0);
	    sb.append(" from ");
	    sb.append(this.introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
	    pageElement.addElement(new TextElement(sb.toString()));
	    
	    XmlElement whereElement = new XmlElement("trim");
	    whereElement.addAttribute(new Attribute("prefix", "WHERE"));
	    whereElement.addAttribute(new Attribute("prefixOverrides", "AND"));
	    pageElement.addElement(whereElement);
	    
	    List columns = this.introspectedTable.getAllColumns();
	    for(int i = 0 ; i < columns.size() ; i++ ){
	    	IntrospectedColumn column = (IntrospectedColumn)columns.get(i);
	    	String columnName = column.getActualColumnName();
	    	XmlElement ifExistElement = new XmlElement("if");
	    	ifExistElement.addAttribute(new Attribute("test ", columnName + " !=null"));
	    	sb.setLength(0);
	        sb.append(" and ").
	           append(columnName).
	           append(" = #{").
	           append(columnName).
	           append(",jdbcType=").
	           append(column.getJdbcTypeName()).
	           append("}");
	    	ifExistElement.addElement(new TextElement(sb.toString()));
	    	whereElement.addElement(ifExistElement);
	    }
	    parentElement.addElement(pageElement);
  }
}
