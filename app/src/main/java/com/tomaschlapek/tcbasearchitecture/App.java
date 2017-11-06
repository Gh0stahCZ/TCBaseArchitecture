//package com.tomaschlapek.tcbasearchitecture;
//
//import android.app.Activity;
//import android.app.Application;
//import android.content.Context;
//import android.content.res.AssetManager;
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.graphics.drawable.Drawable;
//import android.os.Build.VERSION;
//import android.os.Build.VERSION_CODES;
//import android.support.multidex.MultiDex;
//import android.util.DisplayMetrics;
//
//import android.support.v4.content.ContextCompat;
//
//import com.facebook.stetho.Stetho;
//import com.tomaschlapek.tcbasearchitecture.core.component.DaggerKAppComponent;
//import com.tomaschlapek.tcbasearchitecture.core.component.KAppComponent;
//import com.tomaschlapek.tcbasearchitecture.helper.KRealmHelper;
//import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.KSampleActivity;
//import com.tomaschlapek.tcbasearchitecture.widget.KDebugTree;
//import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
//
//import io.realm.Realm;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.ref.WeakReference;
//import java.util.Locale;
//
//import javax.inject.Inject;
//
//import dagger.android.AndroidInjector;
//import dagger.android.DispatchingAndroidInjector;
//import dagger.android.HasActivityInjector;
//import timber.log.Timber;
//
///**
// * Application class.
// */
//public class App extends Application implements HasActivityInjector {
//
//  /* Public Constants *****************************************************************************/
//
//  public static final String INIT_ACTIVITY_NAME = KSampleActivity.class.getName();
//
//  /* Public Constants *****************************************************************************/
//
//  @Inject
//  DispatchingAndroidInjector<Activity> mAndroidInjector;
//
//  /* Private Constants ****************************************************************************/
//
//  /**
//   * Application component used for dependency injection.
//   */
//  private static KAppComponent sAppComponent;
//
//  /**
//   * Application context.
//   */
//  private static WeakReference<Context> sContextReference;
//
//  /**
//   * Application resources.
//   */
//  private static Resources sResources;
//
//  /**
//   * Application assets.
//   */
//  private static AssetManager sAssets;
//
//  /**
//   * Display metrics of primary display.
//   */
//  private static DisplayMetrics sDisplayMetrics;
//
//  //  /**
//  //   * Icon font from resources.
//  //   */
//  //  private static Typeface sIconFont;
//
//  /**
//   * Indicates that the application is initializes.
//   */
//  private static boolean sIsInitialized;
//
//  /**
//   * Indicates that the application went to background.
//   */
//  private static boolean sIsInBackground;
//
//  /**
//   * Indicates that the application is in dual-pane mode.
//   */
//  private static boolean sIsDualPane;
//
//  /**
//   * Indicates that the application displays two columns in portrait.
//   */
//  private static boolean sHasTwoColumnsInPortrait;
//
//  /* Private Attributes ***************************************************************************/
//
//  //  /**
//  //   * Reference watcher for Leak Canary.
//  //   */
//  //  private RefWatcher mRefWatcher;
//
//  /* Public Static Methods ************************************************************************/
//
//
//  public static KAppComponent getAppComponent() {
//    return sAppComponent;
//  }
//
//  /**
//   * Returns application context.
//   */
//  public static Context getContext() {
//    return sContextReference.get();
//  }
//
//  /**
//   * Indicates that the application is initializes.
//   */
//  public static boolean isInitialized() {
//    return sIsInitialized;
//  }
//
//  /**
//   * Marks the application as initialized.
//   */
//  public static void setInitialized(boolean isInitialized) {
//    sIsInitialized = isInitialized;
//  }
//
//  /**
//   * Indicates that the application went to background.
//   */
//  public static boolean isInBackground() {
//    return sIsInBackground;
//  }
//
//  /**
//   * Marks if the application went to background.
//   */
//  public static void setInBackground(boolean isInBackground) {
//    sIsInBackground = isInBackground;
//  }
//
//  /**
//   * Indicates the orientation is portrait.
//   */
//  public static boolean isPortrait() {
//    return sResources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
//  }
//
//  /**
//   * Indicates the orientation is landscape.
//   */
//  public static boolean isLandscape() {
//    return !isPortrait();
//  }
//
//  /**
//   * Indicates that the application is in dual-pane mode.
//   */
//  public static boolean isDualPane() {
//    return sIsDualPane;
//  }
//
//  /**
//   * Indicates that the application displays two columns in portrait.
//   */
//  public static boolean hasTwoColumnsInPortrait() {
//    return sHasTwoColumnsInPortrait;
//  }
//
//  /**
//   * @see Resources#getString(int)
//   */
//  public static String getResString(int id) {
//    return sResources.getString(id);
//  }
//
//  /**
//   * @see Resources#getString(int, Object...)
//   */
//  public static String getResString(int resourceId, Object... formatArgs) {
//    return sResources.getString(resourceId, formatArgs);
//  }
//
//  /**
//   * @see Resources#getBoolean(int)
//   */
//  public static boolean getResBoolean(int id) {
//    return sResources.getBoolean(id);
//  }
//
//  /**
//   * @see Resources#getDimensionPixelSize(int)
//   */
//  public static int getResDimension(int id) {
//    return sResources.getDimensionPixelSize(id);
//  }
//
//  /**
//   * @see ContextCompat#getColor(Context, int)
//   */
//  public static int getResColor(int id) {
//    return ContextCompat.getColor(getContext(), id);
//  }
//
//  /**
//   * @see ContextCompat#getDrawable(Context, int)
//   */
//  public static Drawable getResDrawable(int id) {
//    return ContextCompat.getDrawable(getContext(), id);
//  }
//
//  /**
//   * @see Resources#getBoolean(int)
//   */
//  public static Locale getLocale() {
//    if (VERSION.SDK_INT >= VERSION_CODES.N) {
//      return sResources.getConfiguration().getLocales().get(0);
//    } else {
//      return sResources.getConfiguration().locale;
//    }
//  }
//
//  /**
//   * Opens stream of specified asset file.
//   *
//   * @param assetFileName Asset file name.
//   *
//   * @return Opened stream of asset file.
//   */
//  public static InputStream getAssetFileStream(String assetFileName) {
//    InputStream result = null;
//    try {
//      result = sAssets.open(assetFileName);
//    } catch (IOException exception) {
//      Timber.e(exception, exception.getMessage());
//    }
//    return result;
//  }
//
//  /**
//   * Converts PX to DP on primary screen.
//   *
//   * @param px PX to convert.
//   *
//   * @return Resulting DP.
//   */
//  public static int pxToDp(int px) {
//    return (int) ((px / sDisplayMetrics.density) + 0.5);
//  }
//
//  /**
//   * Converts DP to PX on primary screen.
//   *
//   * @param dp DP to convert.
//   *
//   * @return Resulting PX.
//   */
//  public static int dpToPx(int dp) {
//    return (int) ((dp * sDisplayMetrics.density) + 0.5);
//  }
//
//  /* Public Methods *******************************************************************************/
//
//  @Override
//  protected void attachBaseContext(Context base) {
//    super.attachBaseContext(base);
//    MultiDex.install(this);
//  }
//
//  @Override
//  public void onCreate() {
//    super.onCreate();
//
//    // Start monitoring crashes.
//    if (BuildConfig.DEBUG_MODE) {
//      Timber.plant(new KDebugTree());
//    } else {
//      // TODO Uncomment after Crashlytics addition
//      //  Fabric.with(this, new Crashlytics());
//      //  Timber.plant(new KrashlyticsTree());
//      Timber.plant(new KDebugTree());
//    }
//
//    sAppComponent =
//      DaggerKAppComponent.builder()
//        .application(this)
//        .build();
//
//    sAppComponent.inject(this);
//
//    Stetho.initialize(
//      Stetho.newInitializerBuilder(this)
//        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//        .enableWebKitInspector(RealmInspectorModulesProvider
//          .builder(this).withLimit(1000).build())
//        .build());
//
//    //    // Initialize Leak Canary, if enabled (and debug mode is on).
//    //    if (BuildConfig.LEAK_CANARY_ENABLED && !LeakCanary.isInAnalyzerProcess(this)) {
//    //      mRefWatcher = LeakCanary.install(this);
//    //    }
//
//    // Initialize attributes.
//    sContextReference = new WeakReference<>(this);
//    sResources = getResources();
//    sAssets = getAssets();
//    sDisplayMetrics = sResources.getDisplayMetrics();
//    sIsInitialized = false;
//    sIsInBackground = true;
//    sIsDualPane = sResources.getBoolean(R.bool.is_dual_pane);
//    sHasTwoColumnsInPortrait = sResources.getBoolean(R.bool.has_two_columns_in_portrait);
//    //    sIconFont = Typeface.createFromAsset(sAssets, "fonts/icons_font.ttf");
//
//    Realm.init(this);
//    KRealmHelper.initializeRealmConfig();
//  }
//
//  @Override
//  public AndroidInjector<Activity> activityInjector() {
//    return mAndroidInjector;
//  }
//
//
//  /* Private Methods ******************************************************************************/
//  /* Getters / Setters ****************************************************************************/
//  /* Inner classes ********************************************************************************/
//
//
//}
