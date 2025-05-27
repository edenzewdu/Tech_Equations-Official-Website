/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lucy.jsf.util;

import java.util.Base64;

/**
 *
 * @author Lucy
 */
public class URLUtils {
    public static String encode(String url) {
        return Base64.getUrlEncoder().encodeToString(url.getBytes());
    }

    public static String decode(String encodedUrl) {
        return new String(Base64.getUrlDecoder().decode(encodedUrl));
    }
    
}
