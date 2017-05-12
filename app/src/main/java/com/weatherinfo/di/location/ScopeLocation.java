package com.weatherinfo.di.location;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by user on 12.05.17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ScopeLocation {
}
