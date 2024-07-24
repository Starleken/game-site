package org.leafall.tokenservicestarter.service;

import org.leafall.tokenservicestarter.model.AuthContext;

public class AuthContextHolder {

    private static final ThreadLocal<AuthContext> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void set(AuthContext context) {
        CONTEXT_HOLDER.set(context);
    }

    public static AuthContext get() {
        return CONTEXT_HOLDER.get();
    }

    public static void remove() {
        CONTEXT_HOLDER.remove();
    }
}
