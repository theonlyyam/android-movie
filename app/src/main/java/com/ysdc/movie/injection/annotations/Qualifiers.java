package com.ysdc.movie.injection.annotations;

import javax.inject.Qualifier;

public class Qualifiers {

    @Qualifier
    public @interface ForApplication {
    }

    @Qualifier
    public @interface ForActivity {
    }

}
