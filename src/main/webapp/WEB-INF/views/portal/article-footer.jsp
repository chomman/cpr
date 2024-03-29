<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<jsp:include page="similar-webpages.jsp" /> 

<div class="pj-news-footer">
	<div class="pj-news-share">
		<!-- Go to www.addthis.com/dashboard to generate a new set of buttons -->
		<a href="https://api.addthis.com/oexchange/0.8/forward/email/offer?url=https://www.nlfnorm.cz<webpage:link webpage="${webpageModel.webpage}" />&pubid=ra-4f7805435b56a706&ct=1&title=<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" />&pco=tbxnj-1.0" target="_blank"><img src="https://cache.addthiscdn.com/icons/v2/thumbs/32x32/email.png" border="0" alt="Email"/></a>
		<a href="https://api.addthis.com/oexchange/0.8/forward/print/offer?url=https://www.nlfnorm.cz<webpage:link webpage="${webpageModel.webpage}" />&pubid=ra-4f7805435b56a706&ct=1&title=<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" />&pco=tbxnj-1.0" target="_blank"><img src="https://cache.addthiscdn.com/icons/v2/thumbs/32x32/print.png" border="0" alt="Print"/></a>
		<a href="https://api.addthis.com/oexchange/0.8/forward/google_plusone_share/offer?url=https://www.nlfnorm.cz<webpage:link webpage="${webpageModel.webpage}" />&pubid=ra-4f7805435b56a706&ct=1&title=<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" />&pco=tbxnj-1.0" target="_blank"><img src="https://cache.addthiscdn.com/icons/v2/thumbs/32x32/google_plusone_share.png" border="0" alt="Google+"/></a>
		<a href="https://api.addthis.com/oexchange/0.8/forward/facebook/offer?url=https://www.nlfnorm.cz<webpage:link webpage="${webpageModel.webpage}" />&pubid=ra-4f7805435b56a706&ct=1&title=<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" />&pco=tbxnj-1.0" target="_blank"><img src="https://cache.addthiscdn.com/icons/v2/thumbs/32x32/facebook.png" border="0" alt="Facebook"/></a>
		<a href="https://api.addthis.com/oexchange/0.8/forward/twitter/offer?url=https://www.nlfnorm.cz<webpage:link webpage="${webpageModel.webpage}" />&pubid=ra-4f7805435b56a706&ct=1&title=<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" />&pco=tbxnj-1.0" target="_blank"><img src="https://cache.addthiscdn.com/icons/v2/thumbs/32x32/twitter.png" border="0" alt="Twitter"/></a>
	</div>
	
	<div class="pj-wrapp-meta">
		<span class="pj-meta-item">
			<span class="label">
				<spring:message code="admin.service.enabled" />: 
			</span>
			<joda:format value="${webpageModel.webpage.changed}" pattern="dd.MM.yyyy"/>
		</span>
		
		<span class="pj-meta-item">
			<span class="label">
				Autor: 
			</span>
			<a href="http://nlfnorm.cz" target="_blank" title="Institut pro testování a certifikaci, a.s.">
				ITC
			</a>
		</span>
	</div>
	<div class="clear"></div>
</div>