package filters;

/**
 * Created by codephillip on 21/02/17.
 */
import play.mvc.EssentialFilter;
import play.filters.cors.CORSFilter;
import play.http.DefaultHttpFilters;

import javax.inject.Inject;

public class UrlFilter extends DefaultHttpFilters {
    @Inject public UrlFilter(CORSFilter corsFilter) {
        super(corsFilter);
    }
}