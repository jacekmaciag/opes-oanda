package pl.jdev.opes_oanda.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import pl.jdev.opes_commons.domain.AbstractEntity;
import pl.jdev.opes_commons.rest.IntegrationClient;

public class AbstractEntityController<E extends AbstractEntity> {
    @Autowired
    IntegrationClient integrationClient;
}
