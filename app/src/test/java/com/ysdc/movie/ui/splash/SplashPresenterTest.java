package com.ysdc.movie.ui.splash;

import com.ysdc.movie.data.prefs.MyPreferences;
import com.ysdc.movie.data.repository.MovieRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Completable;
import io.reactivex.observers.TestObserver;
import test.helper.rx.RxSchedulersTestRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SplashPresenterTest {

    @Rule
    public RxSchedulersTestRule rxSchedulersTestRule = new RxSchedulersTestRule();
    @Mock
    MovieRepository movieRepository;
    @Mock
    MyPreferences preferences;
    @Mock
    SplashMvpView mvpView;

    private SplashPresenter presenter;

    @Before
    public void setUp() {
        presenter = new SplashPresenter(movieRepository, preferences);
    }

    @Test
    public void testInitSuccess() {
        when(movieRepository.retrieveConfiguration()).thenReturn(Completable.complete());

        TestObserver<Void> testObserver = new TestObserver<>();
        movieRepository.retrieveConfiguration().subscribe(testObserver);

        presenter.onAttach(mvpView);
        presenter.initApplication();

        rxSchedulersTestRule.ioScheduler().triggerActions();
        rxSchedulersTestRule.mainScheduler().triggerActions();
        testObserver.awaitTerminalEvent();
        testObserver.assertTerminated();
        verify(preferences, times(1)).set(MyPreferences.FILTER_FROM_DATE, 0L);
        verify(mvpView, times(1)).applicationInitialized();
    }

    @Test
    public void testInitFail() {
        Exception exception = new Exception();
        when(movieRepository.retrieveConfiguration()).thenReturn(Completable.error(exception));

        TestObserver<Void> testObserver = new TestObserver<>();
        movieRepository.retrieveConfiguration().subscribe(testObserver);

        presenter.onAttach(mvpView);
        presenter.initApplication();

        rxSchedulersTestRule.ioScheduler().triggerActions();
        rxSchedulersTestRule.mainScheduler().triggerActions();
        testObserver.awaitTerminalEvent();
        testObserver.assertTerminated();
        verify(preferences, times(0)).set(MyPreferences.FILTER_FROM_DATE, 0L);
        verify(mvpView, times(1)).onConfigurationRetrivalError(exception);
    }
}
