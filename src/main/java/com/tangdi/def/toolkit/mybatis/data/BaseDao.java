package com.tangdi.def.toolkit.mybatis.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Document BaseDao
 * <p>
 * 
 * @version 1.0.0,2015-11-6
 * @author li.sy
 * @since 1.0.0
 */

public interface BaseDao<T, ID> {
	int deleteByPrimaryKey(ID id);

	int insert(T record) ;

	int insertSelective(T record) ;

	T selectByPrimaryKey(ID id) ;

	int updateByPrimaryKeySelective(T record) ;

	int updateByPrimaryKey(T record) ;

	List<T> selectAll() ;
	
	List<T> selectByPager(Map param) ;
	
	List<T> selectByCondition(Map param) ;
}
