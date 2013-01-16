package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

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
import sk.peterjurkovic.cpr.utils.UserUtils;

@Service("articleService")
@Transactional(propagation = Propagation.REQUIRED)
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private UserService userService;
	
	@Override
	public void saveArticle(Article article) {
		articleDao.save(article);
	}

	@Override
	public void updateArticle(Article article) {
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

}
