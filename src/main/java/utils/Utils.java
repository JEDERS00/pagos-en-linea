/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author JEDERS
 */
public class Utils {

    public static boolean areEmpty(String... values) {
        for (String value : values) {
            if (StringUtils.isBlank(value)) {
                return true;
            }
        }
        return false;
    }
}
