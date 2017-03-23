package com.baosight.iwater.system.dic.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baosight.iwater.system.dic.service.IDicService;

@RestController
@RequestMapping("/system/dic")
public class SystemDicController {

	@Resource
	private IDicService dicService;
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/init",produces = "text/html;charset=UTF-8")
	public String judge(HttpServletRequest request){
		return dicService.initLoad(request);
	}
	
	
	/**
	 * @param prarentCode
	 * @return
	 * nextLevelNode
	 */
	@RequestMapping(value="/nextLevelNode",produces = "text/html;charset=UTF-8")
	public String queryForNextLevel(HttpServletRequest request){
		return dicService.nextLevelNode(request);
	}
	
	
	/**
	 * @param parentCode
	 * @return
	 * nextLevelNodeForAll
	 */
	@RequestMapping(value="/nextLevelNodeForAll",produces = "text/html;charset=UTF-8")
	public String nextLevelNodeForAll(HttpServletRequest request){
		return dicService.nextLevelNodeForAll(request);
	}
	
	/**
	 * @param dic_code
	 * @return
	 * queryForExist
	 */
	@RequestMapping(value="/queryForExist", produces="text/html;charset=UTF-8")
	public String queryDiccodeForExist(HttpServletRequest request){
		return dicService.queryForExist(request);
	}
	
	
	/**
	 * @param stk-obj
	 * @return
	 * insertForNew
	 */
	@RequestMapping(value="/insertForNew", produces="test/html;charset=UTF-8")
	public String insertForNew(HttpServletRequest request){
		return dicService.insertForNew(request);
	}
	
	
	/**
	 * @param dic_id
	 * @return
	 * update
	 */
	@RequestMapping(value="/update", produces="test/html;charset=UTF-8")
	public String update(HttpServletRequest request){
		return dicService.update(request);
	}
	
	
	/**
	 * @param	dic_id
	 * @return
	 * deleteNode
	 */
	@RequestMapping(value="/deleteNode", produces="test/html;charset=UTF-8")
	public String deleteNode(HttpServletRequest request){
		return dicService.deleteNode(request);
	}
	
	
	/**
	 * @param map{dic_id,is_usable}
	 * @return
	 * updateIsable
	 */
	@RequestMapping(value="/updateIsable", produces="test/html;charset=UTF-8")
	public String updateIsable(HttpServletRequest request){
		return dicService.updateIsable(request);
	}
	
	/**
	 * @param map{dic_id}
	 * @return
	 * loadBeforeUpdate
	 */
	@RequestMapping(value="/loadBeforeUpdate", produces="test/html;charset=UTF-8")
	public String loadBeforeUpdate(HttpServletRequest request){
		return dicService.loadBeforeUpdate(request);
	}
	
	
	/**
	 * @param map{dic_id}
	 * @return
	 * checkNodeForParent
	 */
	@RequestMapping(value="/checkNodeForParent", produces="test/html;charset=UTF-8")
	public String checkNodeForParent(HttpServletRequest request){
		return dicService.queryForDicCode(request);
	}
	
	
	/**
	 * @param map{dic_code,disNum,page}
	 * @return
	 * download
	 */
	@RequestMapping(value="/download", produces="test/html;charset=UTF-8")
	public String download(HttpServletRequest request,HttpServletResponse response){
		return dicService.download(request,response);
	}
}
