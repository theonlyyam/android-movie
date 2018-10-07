/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.ysdc.movie.ui.base;

/**
 * Every view (Activity & fragment) must implement this interface. Used also in the presenters as a reference to their views.
 */

import android.support.annotation.StringRes;

public interface MvpView {

    void onError(Throwable throwable);

    void onError(@StringRes int resId);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    void hideKeyboard();
}
