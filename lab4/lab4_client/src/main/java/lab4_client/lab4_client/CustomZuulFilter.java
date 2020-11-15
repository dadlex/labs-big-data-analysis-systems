package lab4_client.lab4_client;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class CustomZuulFilter extends ZuulFilter {

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader("Test", "TestSample");
        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    //https://medium.com/@kirill.sereda/spring-cloud-netflix-zuul-api-gateway-%D0%BF%D0%BE-%D1%80%D1%83%D1%81%D1%81%D0%BA%D0%B8-c1e819f042e1
    @Override
    public String filterType() {
        return "pre";
    }

    //first filter
    @Override
    public int filterOrder() {
        return 1;
    }
}