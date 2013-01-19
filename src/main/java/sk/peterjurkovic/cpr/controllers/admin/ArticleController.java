package sk.peterjurkovic.cpr.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import sk.peterjurkovic.cpr.entities.Article;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.enums.ArticleOrder;
import sk.peterjurkovic.cpr.services.ArticleService;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.validators.admin.ArticleValidator;
import sk.peterjurkovic.cpr.web.editors.DateTimeEditor;
import sk.peterjurkovic.cpr.web.json.JsonResponse;
import sk.peterjurkovic.cpr.web.json.JsonStatus;
import sk.peterjurkovic.cpr.web.pagination.PageLink;
import sk.peterjurkovic.cpr.web.pagination.PaginationLinker;

@Controller
@SessionAttributes("article")
public class ArticleController extends SupportAdminController {
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private DateTimeEditor dateTimeEditor;
	@Autowired
	private ArticleValidator articleValidator;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(DateTime.class, this.dateTimeEditor);
    }
	
	public ArticleController(){
		setTableItemsView("article");
		setEditFormView("article-edit");
	}
	
	
	@RequestMapping("/admin/articles")
    public String showArticlePage(ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		List<PageLink>paginationLinks = getPaginationItems(request, params, currentPage);
		List<Article> articles = articleService.getArticlePage( currentPage, params);
		model.put("articles", articles);
		model.put("paginationLinks", paginationLinks);
		model.put("orders", ArticleOrder.getAll());
		model.put("tab", 1);
		model.put("params", params);
		modelMap.put("model", model);
        return getTableItemsView();
    }
	
	
	@RequestMapping( value = "/admin/article/delete/{articleId}", method = RequestMethod.GET)
	public String deleteStandard(@PathVariable Long articleId, ModelMap model, HttpServletRequest request) {
		Article article = articleService.getArticleById(articleId);
		if(article == null){
			createItemNotFoundError();
		}
		model.put("successDelete", true);
		articleService.deleteArticle(article);
        return showArticlePage(model, request);
	}
	
	@RequestMapping("/admin/article/add")
	public String addNewArticle(ModelMap modelMap){
		setEditFormView("article-add");
		Article form = new Article();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab", 2);
		modelMap.put("model", model);
		modelMap.addAttribute("article", form);
		return getEditFormView(); 
	}
	
	
	@RequestMapping(value = "/admin/article/add", method = RequestMethod.POST)
	public String processCreate(@Valid Article article, BindingResult result, ModelMap model){
		setEditFormView("article-add");
		
		if(!result.hasErrors()){
			articleService.createArticle(article);
			if(article.getId() != null){
				return "redirect:/admin/article/edit/" + article.getId() + "?successCreate=1";
			}
		}
		return getEditFormView(); 
	}
	
	
	
	@RequestMapping("/admin/article/edit/{articleId}")
	public String showEditForm(@PathVariable Long articleId, ModelMap modelMap, HttpServletRequest request){
		setEditFormView("article-edit");
		logger.info("showEditForm");
		Article form = articleService.getArticleById(articleId);
		if(form == null){
			createItemNotFoundError();
		}
		//ArticleForm form = createArticleForm( article);
		if(request.getParameter("successCreate") != null){
			modelMap.put("successCreate", true);
		}
		prepareModel(form,  modelMap);
		return getEditFormView();
	}
	
	/*
	@RequestMapping( value = "/admin/article/edit/{articleId}", method = RequestMethod.POST)
	public String processUpdate(Article form, BindingResult result,@PathVariable Long articleId, ModelMap model){
		setEditFormView("article-edit");
		logger.info("processUpdate");
		Article article = articleService.getArticleById(articleId);
		if(article == null){
			createItemNotFoundError();
		}
		articleValidator.validate(result, form);
		if(!result.hasErrors()){
			updateArticle(form, article);
			model.put("successCreate", true);
		}
		return getEditFormView();
	}
	
	*/
	@RequestMapping( value = "/admin/article/edit/{articleId}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JsonResponse  processAjaxUpdate(@RequestBody Article article, @PathVariable Long articleId){
		JsonResponse response = new JsonResponse();
		logger.info("processAjaxUpdate");
		Article persistedArticle = articleService.getArticleById(articleId);
		if(persistedArticle == null){
			return response;
		}
		List<String> errors = articleValidator.validate(article, persistedArticle);
		
		if(errors.size() == 0){
			updateArticle(article, persistedArticle);
			response.setStatus(JsonStatus.SUCCESS);
			response.setResult(persistedArticle.getChanged().getMillis());
		}else{
			response.setResult(errors);
		}

		return response;
	}
	
	
	private void prepareModel(Article form, ModelMap map){
		Map<String, Object> model = new HashMap<String, Object>();
		if(form.getChanged() != null){
			form.setTimestamp(form.getChanged().getMillis());
		}
		map.addAttribute("article", form);
		model.put("articleId", form.getId());
		map.put("model", model); 
	}
	
	private void updateArticle(Article form, Article persistedArticle){
		persistedArticle.setTitle(form.getTitle());
		persistedArticle.setHeader(form.getHeader());
		persistedArticle.setArticleContent(form.getArticleContent());
		persistedArticle.setEnabled(form.getEnabled());
		persistedArticle.setPublishedSince(form.getPublishedSince());
		persistedArticle.setPublishedUntil(form.getPublishedUntil());
		articleService.updateArticle(persistedArticle);
	}
	
	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params,int currentPage){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl("/admin/articles");
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount( articleService.getCountOfArticles(params).intValue() );
		return paginger.getPageLinks(); 
	}
	
}
