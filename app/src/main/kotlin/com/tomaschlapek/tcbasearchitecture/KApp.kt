//package com.tomaschlapek.tcbasearchitecture
//
//import android.app.Activity
//import android.app.Application
//import android.content.Context
//import android.content.res.AssetManager
//import android.content.res.Configuration
//import android.content.res.Resources
//import android.graphics.drawable.Drawable
//import android.os.Build
//import android.support.v4.content.ContextCompat
//import android.util.DisplayMetrics
//import com.facebook.stetho.Stetho
//import com.tomaschlapek.tcbasearchitecture.core.component.AppComponent
//import com.tomaschlapek.tcbasearchitecture.core.component.DaggerAppComponent
//import com.tomaschlapek.tcbasearchitecture.helper.RealmHelper
//import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.KSampleActivity
//import com.tomaschlapek.tcbasearchitecture.util.DebugTree
//import com.uphyca.stetho_realm.RealmInspectorModulesProvider
//import dagger.android.AndroidInjector
//import dagger.android.DispatchingAndroidInjector
//import dagger.android.HasActivityInjector
//import io.realm.Realm
//import timber.log.Timber
//import java.io.IOException
//import java.io.InputStream
//import java.lang.ref.WeakReference
//import java.util.*
//import javax.inject.Inject
//
///**
// * Created by tomaschlapek on 18/9/17.
// */
//class KApp : Application(), HasActivityInjector {
//
//  /* Private Constants ****************************************************************************/
//  /* Private Attributes ***************************************************************************/
//
//  val INIT_ACTIVITY_NAME = KSampleActivity::class.java.name
//
//  @Inject
//  lateinit internal var mAndroidInjector: DispatchingAndroidInjector<Activity>
//
//  /**
//   * Application component used for dependency injection.
//   */
//
//  companion object {
//
//    @JvmStatic
//    lateinit private var sAppComponent: AppComponent
//
//    /**
//     * Application context.
//     */
//    @JvmStatic
//    lateinit private var sContextReference: WeakReference<Context>
//
//    /**
//     * Application resources.
//     */
//    @JvmStatic
//    lateinit private var sResources: Resources
//
//
//    /**
//     * Application assets.
//     */
//    @JvmStatic
//    lateinit private var sAssets: AssetManager
//
//    /**
//     * Display metrics of primary display.
//     */
//    @JvmStatic
//    lateinit private var sDisplayMetrics: DisplayMetrics
//
//    /**
//     * Indicates that the application is initializes.
//     */
//    @JvmStatic
//    private var sIsInitialized: Boolean = false
//
//
//    /**
//     * Indicates that the application went to background.
//     */
//    @JvmStatic
//    private var sIsInBackground: Boolean  = false
//
//    /**
//     * Indicates that the application is in dual-pane mode.
//     */
//    @JvmStatic
//    private var sIsDualPane: Boolean = false
//
//    /**
//     * Returns application context.
//     */
//    fun getContext(): Context? {
//      return sContextReference?.get()
//    }
//
//    /**
//     * Indicates that the application is initializes.
//     */
//    fun isInitialized(): Boolean {
//      return sIsInitialized
//    }
//
//    /**
//     * Marks the application as initialized.
//     */
//    fun setInitialized(isInitialized: Boolean) {
//      sIsInitialized = isInitialized
//    }
//
//    /**
//     * Indicates that the application went to background.
//     */
//    fun isInBackground(): Boolean {
//      return sIsInBackground
//    }
//
//    /**
//     * Marks if the application went to background.
//     */
//    fun setInBackground(isInBackground: Boolean) {
//      sIsInBackground = isInBackground
//    }
//
//    /**
//     * Indicates the orientation is portrait.
//     */
//    fun isPortrait(): Boolean {
//      return sResources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
//    }
//
//    /**
//     * Indicates the orientation is landscape.
//     */
//    fun isLandscape(): Boolean {
//      return !isPortrait()
//    }
//
//    /**
//     * Indicates that the application is in dual-pane mode.
//     */
//    fun isDualPane(): Boolean {
//      return sIsDualPane
//    }
//
//    /**
//     * Returns country code of the current locale.
//     */
//    fun getCountryCode(): String {
//      return sResources.configuration.locale.country
//    }
//
//    /**
//     * @see Resources.getString
//     */
//    fun getResString(id: Int): String {
//      return sResources.getString(id)
//    }
//
//    /**
//     * @see Resources.getString
//     */
//    fun getResString(resourceId: Int, vararg formatArgs: Any): String {
//      return sResources.getString(resourceId, *formatArgs)
//    }
//
//    /**
//     * @see Resources.getBoolean
//     */
//    fun getResBoolean(id: Int): Boolean {
//      return sResources.getBoolean(id)
//    }
//
//    /**
//     * @see Resources.getDimensionPixelSize
//     */
//    fun getResDimension(id: Int): Int {
//      return sResources.getDimensionPixelSize(id)
//    }
//
//    /**
//     * @see ContextCompat.getColor
//     */
//    fun getResColor(id: Int): Int {
//      return ContextCompat.getColor(getContext(), id)
//    }
//
//    /**
//     * @see ContextCompat.getDrawable
//     */
//    fun getResDrawable(id: Int): Drawable {
//      return ContextCompat.getDrawable(getContext(), id)
//    }
//
//    /**
//     * @see Resources.getBoolean
//     */
//    fun getLocale(): Locale {
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//        return sResources.configuration.locales.get(0)
//      } else {
//        return sResources.configuration.locale
//      }
//    }
//
//    /**
//     * Opens stream of specified asset file.
//
//     * @param assetFileName Asset file name.
//     * *
//     * *
//     * @return Opened stream of asset file.
//     */
//    fun getAssetFileStream(assetFileName: String): InputStream? {
//      var result: InputStream? = null
//      try {
//        result = sAssets.open(assetFileName)
//      } catch (exception: IOException) {
//        Timber.e(exception, exception.message)
//      }
//
//      return result
//    }
//
//    /**
//     * Converts PX to DP on primary screen.
//
//     * @param px PX to convert.
//     * *
//     * *
//     * @return Resulting DP.
//     */
//    fun pxToDp(px: Int): Int {
//      return (px / sDisplayMetrics.density + 0.5).toInt()
//    }
//
//    /**
//     * Converts DP to PX on primary screen.
//
//     * @param dp DP to convert.
//     * *
//     * *
//     * @return Resulting PX.
//     */
//    fun dpToPx(dp: Int): Int {
//      return (dp * sDisplayMetrics.density + 0.5).toInt()
//    }
//
//    @JvmStatic
//    fun getAppComponent(): AppComponent {
//      return sAppComponent
//    }
//  }
//
//  /* Constructor **********************************************************************************/
//  /* Public Static Methods ************************************************************************/
//
//  override fun activityInjector(): AndroidInjector<Activity> {
//    return mAndroidInjector
//  }
//
//
//  /* Public Methods *******************************************************************************/
//  override fun onCreate() {
//    super.onCreate()
//
//    // Start monitoring crashes.
//    if (BuildConfig.DEBUG_MODE) {
//      Timber.plant(DebugTree())
//    } else {
//      // TODO Uncomment after Crashlytics addition
//      //  Fabric.with(this, new Crashlytics());
//      //  Timber.plant(new CrashlyticsTree());
//      Timber.plant(DebugTree())
//    }
//
//    sAppComponent = DaggerAppComponent.builder()
//      .application(this)
//      .build()
//
//    sAppComponent.inject(this)
//
//    //    initLifecycle();
//
//    Stetho.initialize(
//      Stetho.newInitializerBuilder(this)
//        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//        .enableWebKitInspector(RealmInspectorModulesProvider
//          .builder(this).withLimit(1000).build())
//        .build())
//
//    // Initialize attributes.
//    sContextReference = WeakReference<Context>(this)
//    sResources = resources
//    sAssets = assets
//    sDisplayMetrics = sResources.getDisplayMetrics()
//    sIsInitialized = false
//    sIsInBackground = true
//    sIsDualPane = sResources.getBoolean(R.bool.is_dual_pane)
//
//    Realm.init(this)
//    RealmHelper.initializeRealmConfig()
//  }
//}