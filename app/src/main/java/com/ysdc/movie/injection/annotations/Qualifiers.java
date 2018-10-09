package com.ysdc.movie.injection.annotations;

import javax.inject.Qualifier;

/**
 * Class use to qualify a parameter. For example, the context might be for an activity or application. We don't use it in this app but i am use to always add
 * it in case we need to
 */
public class Qualifiers {

    @Qualifier
    public @interface ForApplication {
    }

    @Qualifier
    public @interface ForActivity {
    }

}
