package pl.jdev.opes_nuntius.domain;

import lombok.Data;
import org.springframework.messaging.Message;
import pl.jdev.opes_commons.rest.message.request.RequestType;

@Data
public class RequestModel {
    private RequestType type;
    private String auth;
    private String forEntity;
    private Message body;
}
