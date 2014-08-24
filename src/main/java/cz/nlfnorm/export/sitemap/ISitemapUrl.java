package cz.nlfnorm.export.sitemap;

import java.net.URL;
import java.util.Date;

interface ISitemapUrl {

	public abstract Date getLastMod();

	public abstract URL getUrl();

}