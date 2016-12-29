package cj1098.animeshare.animelist;

import cj1098.animeshare.service.AnimeRequestService;
import cj1098.animeshare.util.DatabaseUtil;
import cj1098.animeshare.util.Preferences;
import cj1098.base.BasePresenter;
import cj1098.event.AccessTokenRetrievedEvent;
import cj1098.event.BatchResponseFinishedEvent;
import cj1098.event.InitialDatabaseStorageEventEnded;
import cj1098.event.RxBus;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chris on 11/27/16.
 */

public class AnimeListPresenter extends BasePresenter<AnimeListMvp.View> implements AnimeListMvp.Presenter{

    private CompositeSubscription mCompositeSubscription;
    private RxBus mRxBus;
    private DatabaseUtil mDatabaseUtil;
    private Preferences mPreferences;
    private AnimeRequestService mService;

    public AnimeListPresenter(DatabaseUtil databaseUtil, Preferences preferences, AnimeRequestService service) {
        mRxBus = RxBus.getInstance();
        mDatabaseUtil = databaseUtil;
        mPreferences = preferences;
        mService = service;
    }

    @Override
    public void subscribeToObservables() {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(mRxBus.register(BatchResponseFinishedEvent.class, this::updateAnimeList));
        mCompositeSubscription.add(mRxBus.register(AccessTokenRetrievedEvent.class, this::storeAccessToken));
    }

    @Override
    public void unsubscribeFromObservables() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
    }

    // region AnimeListMvp presenter interface methods
    @Override
    public void makeBatchCall(String page) {
        if (isAttached()) {
            mService.getAnimeBatch(mPreferences.getAccessToken(), page);
        }
    }

    @Override
    public void makeAuthRequest() {
        mService.getAccessTokenAndMakeFirstBatchRequest();
    }

    // endregion

    private void storeAccessToken(AccessTokenRetrievedEvent accessTokenReceivedEvent) {
        mPreferences.setAccessToken(accessTokenReceivedEvent.getAccessToken());
    }

    private void updateAnimeList(BatchResponseFinishedEvent responseFinishedEvent) {
        if (isAttached()) {
            getView().updateAnimeList(responseFinishedEvent.getAnimeList());
        }
    }
}
