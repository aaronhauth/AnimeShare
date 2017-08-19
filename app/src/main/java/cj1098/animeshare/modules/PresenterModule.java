package cj1098.animeshare.modules;

import cj1098.animedetails.AnimeDetailsMvp;
import cj1098.animedetails.AnimeDetailsPresenter;
import cj1098.animeshare.home.animelist.AnimeListMvp;
import cj1098.animeshare.home.animelist.AnimeListPresenter;
import cj1098.animeshare.home.HomeHeadlessMvp;
import cj1098.animeshare.home.HomeHeadlessPresenter;
import cj1098.animeshare.service.AnimeRequestService;
import cj1098.animeshare.util.DatabaseUtil;
import cj1098.animeshare.util.DeviceUtil;
import cj1098.animeshare.util.Preferences;
import cj1098.animeshare.home.search.SearchMvp;
import cj1098.animeshare.home.search.SearchPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by chris on 11/20/16.
 */

@Module (includes = {
        DeviceModule.class,
        UtilModule.class,
        PreferencesModule.class,
        ServiceModule.class
})
public class PresenterModule {

    @Provides
    HomeHeadlessMvp.Presenter providesHomeHeadlessPresenter(DeviceUtil deviceUtil, DatabaseUtil databaseUtil) {
        return new HomeHeadlessPresenter(deviceUtil, databaseUtil);
   }

    @Provides
    AnimeListMvp.Presenter providesAnimeListPresenter(DatabaseUtil databaseUtil, Preferences preferences, AnimeRequestService requestService) {
        return new AnimeListPresenter(databaseUtil, preferences, requestService);
    }

    @Provides
    SearchMvp.Presenter providesSearchPresenter(AnimeRequestService requestService, Preferences preferences) {
        return new SearchPresenter(requestService, preferences);
    }

    @Provides
    AnimeDetailsMvp.Presenter providesAnimeDetailsPresenter() {
        return new AnimeDetailsPresenter();
    }
}
