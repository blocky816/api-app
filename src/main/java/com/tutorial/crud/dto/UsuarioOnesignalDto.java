package com.tutorial.crud.dto;


public class UsuarioOnesignalDto {
    private Long id;

    private Integer externalId;

    private String pushSubscriptionId;
    
    private String pushSubscriptionToken;

    private Boolean suscrito;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExternalId() {
        return externalId;
    }

    public void setExternalId(Integer externalId) {
        this.externalId = externalId;
    }

    public String getPushSubscriptionId() {
        return pushSubscriptionId;
    }

    public void setPushSubscriptionId(String pushSubscriptionId) {
        this.pushSubscriptionId = pushSubscriptionId;
    }

    public String getPushSubscriptionToken() {
        return pushSubscriptionToken;
    }

    public void setPushSubscriptionToken(String pushSubscriptionToken) {
        this.pushSubscriptionToken = pushSubscriptionToken;
    }

    public Boolean getSuscrito() {
        return suscrito;
    }

    public void setSuscrito(Boolean suscrito) {
        this.suscrito = suscrito;
    }
}
