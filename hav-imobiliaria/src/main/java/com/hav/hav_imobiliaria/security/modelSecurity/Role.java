package com.hav.hav_imobiliaria.security.modelSecurity;


public enum Role {
    ADMIN,
    REALTOR,
    EDITOR,
    CUSTOMER;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}