package com.baosight.iwater.system.dic.dao;

import java.util.List;
import java.util.Map;
import com.baosight.iwater.system.dic.pojo.CodeDic;


public interface DicMapper {

	/**
	 * 基础查询-所有字段，无参数
	 * @return
	 */
	public CodeDic baseQuery();
	
	
	/**
	 * 树查询
	 * @author gaoh
	 */
	List<Map<String,String>> queryForTree(Map<String,String> map);
	
	/**
	 * oracle 分页查询 显示
	 * @param map
	 * @return
	 */
	List<CodeDic> orclQueryForChildnode(Map<String,Object> map);
	
	/**
	 * oracle 分页查询  excel
	 * @param map
	 * @return
	 */
	List<Map<String,String>> orclQueryForChildnodes(Map<String,Object> map);
	
	/**
	 * 根据父节点查询下级字典项
	 * @author gaoh
	 */
	List<CodeDic> queryForChildnode(Map<String,Object> map);
	List<Map<String,String>> queryForChildnodes(Map<String,Object> map);
	
	
	/**
	 * 根据父节点查询下级字典项的个数
	 * @author gaoh
	 */
	int countChildnode(Map<String,Object> map);
	
	
	/**
	 * 根据节点编码查询节点是否存在
	 * @param map{dic_code}
	 * @return
	 */
	int queryCountForCode(Map<String,Object> map);
	
	/**
	 * 插入新节点
	 * @param map{全部字段}
	 */
	int insertForNew(Map<String,String> map);
	
	/**
	 * 根据节点id（主键）更新节点信息
	 * @param map{dic_id,其他字段}
	 */
	int update(Map<String,String> map);
	
	/**
	 * 根据节点id（主键）更新节点信息
	 * @param map{dic_id}
	 */
	int deleteForId(Map<String,String> map);
	
	/**
	 * 更改节点启用状态（is_usable）
	 * @param map{dic_id,is_usable}
	 */
	int updateIsable(Map<String,String> map);
	
	
	/**
	 * 修改节点信息前，先查询该节点原信息
	 * @param dic_id
	 */
	CodeDic queryBeforeUpdate(String dic_id);
	
	
	List<Map<String,String>> queryForExportTest(String str);
}
