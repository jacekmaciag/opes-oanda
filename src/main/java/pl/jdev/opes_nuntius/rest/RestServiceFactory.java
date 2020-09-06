package pl.jdev.opes_nuntius.rest;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes_commons.rest.RestService;

@Data
@Component
public class RestServiceFactory implements FactoryBean<RestService> {

    private int factoryId;
    private RestTemplate restTemplate;
    private String host;

    @Override
    public RestService getObject() throws Exception {
        return null;
//        return new ConfigurableClient(restTemplate, host);
    }

    @Override
    public Class<?> getObjectType() {
        return RestService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


}
