<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<article class="pj-widget-artice">
	<h3 class="pj-widget"><a:localizedValue object="${webpageModel.portalProduct}" fieldName="name" /></h3>
	<a:localizedValue object="${webpageModel.portalProduct}" fieldName="description" />						
	<c:if test="${webpageModel.portalProduct.portalProductType.id == 2}">
		<div class="pub-nav">
			<a href="${webpageModel.portalProduct.onlinePublication.previewUrl}" target="_blank" class="online-pub-preview pj-radius">
				<spring:message  code="onlniePublication.preview" />
			</a>
			<a href="<a:url href="/widget/registrace" linkOnly="true" />${webpageModel.params}&amp;pid=${webpageModel.portalProduct.id}" class="online-pub-extend pj-radius">
				<spring:message  code="onlinePublication.orderProduct" />
			</a>
		</div>
	</c:if>
</article> 
<div class="pj-news-footer pj-registrace">
	<a:url href="/widget/registrace/1${webpageModel.params}" cssClass="pj-go-back">
		&laquo; <spring:message code="back" />
	</a:url>
	<div class="pj-news-share">
		<a href="https://api.addthis.com/oexchange/0.8/forward/email/offer?url=https://www.nlfnorm.cz/informacni-portal/online-publikace/${webpageModel.portalProduct.id}&pubid=ra-4f7805435b56a706&ct=1&title=<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" />&pco=tbxnj-1.0" target="_blank"><img src="https://cache.addthiscdn.com/icons/v2/thumbs/16x16/email.png" border="0" alt="Email"/></a>
		<a href="https://api.addthis.com/oexchange/0.8/forward/print/offer?url=https://www.nlfnorm.cz/informacni-portal/online-publikace/${webpageModel.portalProduct.id}&pubid=ra-4f7805435b56a706&ct=1&title=<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" />&pco=tbxnj-1.0" target="_blank"><img src="https://cache.addthiscdn.com/icons/v2/thumbs/16x16/print.png" border="0" alt="Print"/></a>
		<a href="https://api.addthis.com/oexchange/0.8/forward/google_plusone_share/offer?url=https://www.nlfnorm.cz/informacni-portal/online-publikace/${webpageModel.portalProduct.id}&pubid=ra-4f7805435b56a706&ct=1&title=<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" />&pco=tbxnj-1.0" target="_blank"><img src="https://cache.addthiscdn.com/icons/v2/thumbs/16x16/google_plusone_share.png" border="0" alt="Google+"/></a>
		<a href="https://api.addthis.com/oexchange/0.8/forward/facebook/offer?url=https://www.nlfnorm.cz/informacni-portal/online-publikace/${webpageModel.portalProduct.id}&pubid=ra-4f7805435b56a706&ct=1&title=<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" />&pco=tbxnj-1.0" target="_blank"><img src="https://cache.addthiscdn.com/icons/v2/thumbs/16x16/facebook.png" border="0" alt="Facebook"/></a>
		<a href="https://api.addthis.com/oexchange/0.8/forward/twitter/offer?url=https://www.nlfnorm.cz/informacni-portal/online-publikace/${webpageModel.portalProduct.id}&pubid=ra-4f7805435b56a706&ct=1&title=<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" />&pco=tbxnj-1.0" target="_blank"><img src="https://cache.addthiscdn.com/icons/v2/thumbs/16x16/twitter.png" border="0" alt="Twitter"/></a>
	</div>
	<div class="clear"></div>
</div>