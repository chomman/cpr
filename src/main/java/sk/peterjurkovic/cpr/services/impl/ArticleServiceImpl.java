package sk.peterjurkovic.cpr.services.impl;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.ArticleDao;
import sk.peterjurkovic.cpr.entities.Article;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.ArticleService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.CodeUtils;
import sk.peterjurkovic.cpr.utils.ParseUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;

@Service("articleService")
@Transactional(propagation = Propagation.REQUIRED)
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private UserService userService;
	
	
	@Override
	public void createArticle(Article article) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		article.setCode( generateProperUrlForAction(article.getTitle(), null) );
		article.setCreatedBy(user);
		article.setEnabled(Boolean.FALSE);
		article.setCreated(new DateTime());
		articleDao.save(article);
		articleDao.flush();
	}

	@Override
	public void updateArticle(Article article) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		article.setChangedBy(user);
		article.setChanged(new DateTime());
		articleDao.update(article);
	}

	@Override
	public void deleteArticle(Article article) {
		articleDao.remove(article);
	}

	@Override
	@Transactional(readOnly = true)
	public Article getArticleById(Long id) {
		return articleDao.getByID(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Article getArticleByCode(String code) {
		return articleDao.getByCode(code);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Article> getAll() {
		return getAll();
	}

	@Override
	public void saveOrUpdate(Article article) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		
		if(article.getId() == null){
			article.setCreatedBy(user);
			article.setCreated(new DateTime());
			articleDao.save(article);
		}else{
			article.setChangedBy(user);
			article.setChanged(new DateTime());
			articleDao.update(article);
		}
	}
	
	
	private String generateProperUrlForAction(String name, Long id) {
        String properUrl = CodeUtils.toSeoUrl(name);
        Article article = getArticleByCode(properUrl);
        if (article == null || (id != null && article.getId().equals(id))) {
            return properUrl;
        } else {
            Long nextId = articleDao.getNextIdValue();
            properUrl = properUrl + nextId;
            return properUrl;
        }
    }

	@Override
	@Transactional(readOnly = true)
	public List<Article> getArticlePage(int pageNumber,	Map<String, Object> criteria) {
		return articleDao.getArticlePage(pageNumber, validateCriteria(criteria));
	}

	
	@Override
	@Transactional(readOnly = true)
	public Long getCountOfArticles(Map<String, Object> criteria) {
		return articleDao.getCountOfArticles(validateCriteria(criteria));
	}
	
	
	private Map<String, Object> validateCriteria(Map<String, Object> criteria){
		if(criteria.size() != 0){
			criteria.put("orderBy", ParseUtils.parseIntFromStringObject(criteria.get("orderBy")));
			criteria.put("publishedSince", ParseUtils.parseDateTimeFromStringObject(criteria.get("publishedSince")));
			criteria.put("publishedUntil", ParseUtils.parseDateTimeFromStringObject(criteria.get("publishedUntil")));
			criteria.put("enabled", ParseUtils.parseStringToBoolean(criteria.get("enabled")));
		}
		return criteria;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Article> autocomplateSearch(String query) {
		return articleDao.autocomplateSearch(query);
	}
}
