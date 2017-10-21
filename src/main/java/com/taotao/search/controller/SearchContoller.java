package com.taotao.search.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.search.service.SearchService;
/**
 * 搜索服务Controller
 * @author kim
 *
 */
@Controller
public class SearchContoller {

	@Autowired
	private SearchService searchService;
	
	@Value("${SEARCH_RESULT_ROWS}")
	private Integer SEARCH_RESULT_ROWS;
	@RequestMapping("/search")
	@ResponseBody
	public String search(
			@RequestParam("q") String queryString,
			@RequestParam(defaultValue="1") Integer page,
			Model model){
		
		try {
			//把查询条件进行转码，解决get乱码问题
			queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		SearchResult result = searchService.search(queryString, page, SEARCH_RESULT_ROWS);
		// 把结果传递给页面
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("itemList", result.getItemList());
		model.addAttribute("page", page);
		
		return "search";
	}
	
}
