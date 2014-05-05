package cz.nlfnorm.web.controllers.admin;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import cz.nlfnorm.entities.Article;
import cz.nlfnorm.enums.ArticleOrder;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.services.ArticleService;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.validators.admin.ArticleValidator;
import cz.nlfnorm.web.editors.DateTimeEditor;
import cz.nlfnorm.web.json.JsonResponse;
import cz.nlfnorm.web.json.JsonStatus;
import cz.nlfnorm.web.pagination.PageLink;
import cz.nlfnorm.web.pagination.PaginationLinker;

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
	
	/**
	 * Zobrazi nastrankovany zoznam aktualit.
	 * 
	 * @param modelMap
	 * @param request
	 * @return String view
	 */
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
	
	
	/**
	 * Odstrani aktualidu, na zaklade daneho ID
	 * 
	 * @param Long ID danej aktuality
	 * @param Model model
	 * @param HttpServletRequest request
	 * @return String view 
	 * @throws ItemNotFoundException, aktualita s danoym identifikatorom neexistuje
	 */
	@RequestMapping( value = "/admin/article/delete/{articleId}", method = RequestMethod.GET)
	public String deleteStandard(@PathVariable Long articleId, ModelMap model, HttpServletRequest request) throws ItemNotFoundException {
		Article article = articleService.getArticleById(articleId);
		if(article == null){
			createItemNotFoundError("Aktualita s ID: "+ articleId + " se v systému nenachází");
		}
		model.put("successDelete", true);
		articleService.deleteArticle(article);
        return showArticlePage(model, request);
	}
	
	
	/**
	 * ZObrazi JSP stranku s formularom, pre pridanie novej aktuality
	 * 
	 * @param ModelMap model
	 * @return String view - JSP stranka
	 */
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
	
	
	/**
	 * Vytvori novu aktualitu
	 * 
	 * @param Article aktualita
	 * @param BindingResult result
	 * @param Model model
	 * @return String view
	 */
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
	
	
	/**
	 * Zobrazi JSP stranku, pre editaciu aktuality
	 * 
	 * @param Long ID danej aktuality
	 * @param ModelMap modelMap
	 * @param request
	 * @return String JSP stranka
	 * @throws ItemNotFoundException, v pipade ak aktualida s danym ID neexistuje
	 */
	@RequestMapping("/admin/article/edit/{articleId}")
	public String showEditForm(@PathVariable Long articleId, ModelMap modelMap, HttpServletRequest request) throws ItemNotFoundException{
		setEditFormView("article-edit");
		Article form = articleService.getArticleById(articleId);
		if(form == null){
			createItemNotFoundError("Aktualita s ID: "+ articleId + " se v systému nenachází");
		}
		if(request.getParameter("successCreate") != null){
			modelMap.put("successCreate", true);
		}
		prepareModel(form,  modelMap);
		return getEditFormView();
	}
	
	
	/**
	 * Ulozi aktuality, spracuje asynchronnu poziadavku, identifikovanu na zaklade RequestBody
	 * 
	 * @param Article article
	 * @param Long ID aktuality
	 * @return RequestBody, v nasom pripade JSON, obsahujuci status aktualizacie
	 */
	@RequestMapping( value = "/admin/article/edit/{articleId}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JsonResponse  processAjaxUpdate(@RequestBody Article article, @PathVariable Long articleId){
		JsonResponse response = new JsonResponse();
		Article persistedArticle = articleService.getArticleById(articleId);
		if(persistedArticle == null){
			return response;
		}
		List<String> errors = articleValidator.validate(article, persistedArticle);
		
		if(errors.size() == 0){
			updateArticle(article, persistedArticle);
			response.setStatus(JsonStatus.SUCCESS);
			response.setResult(persistedArticle.getChanged().toDateTime().getMillis());
		}else{
			response.setResult(errors);
		}

		return response;
	}
	
	/**
	 * Naseptavac, zobrazujuci titulky aktualit
	 * 
	 * @param String term
	 * @return RequestBody, zoznam vysledkov, vo formate JSON
	 */
	@RequestMapping(value = "/admin/article/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<Article>  autocomplete(@RequestBody @RequestParam("term") String query){
		return articleService.autocomplateSearch(query);
	}
	
	
	private void prepareModel(Article form, ModelMap map){
		Map<String, Object> model = new HashMap<String, Object>();
		if(form.getChanged() != null){
			form.setTimestamp(form.getChanged().toDateTime().getMillis());
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
