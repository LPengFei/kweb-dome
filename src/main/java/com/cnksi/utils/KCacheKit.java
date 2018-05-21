package com.cnksi.utils;

import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.token.ITokenCache;
import com.jfinal.token.Token;

import java.util.ArrayList;
import java.util.List;

public class KCacheKit extends CacheKit {
    private static TokenCache tokenCache;

    public static TokenCache getTokenCache() {
        if (tokenCache == null) {
            return tokenCache = new TokenCache();
        } else {
            return tokenCache;
        }
    }

    private static class TokenCache implements ITokenCache {
        public static final String cacheName = "tokenName";

        @Override
        public void put(Token token) {
            if (token != null) {
                KCacheKit.put(cacheName, token.getId(), token);
            }
        }

        @Override
        public void remove(Token token) {
            if (token != null) {
                KCacheKit.remove(cacheName, token.getId());
            }
        }

        @Override
        public boolean contains(Token token) {
            if (token != null) {
                return KCacheKit.get(cacheName, token.getId()) != null;
            } else {
                return false;
            }
        }

        @Override
        public List<Token> getAll() {
            List<String> keys = KCacheKit.getKeys(cacheName);
            List<Token> tokens = new ArrayList<>();
            for (String key : keys) {
                tokens.add(KCacheKit.get(cacheName, key));
            }
            return tokens;
        }
    }
}
