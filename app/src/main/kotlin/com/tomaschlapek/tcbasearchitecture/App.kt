package com.tomaschlapek.tcbasearchitecture

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.multidex.MultiDex
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import com.facebook.stetho.Stetho
import com.tomaschlapek.tcbasearchitecture.core.component.DaggerKAppComponent
import com.tomaschlapek.tcbasearchitecture.core.component.KAppComponent
import com.tomaschlapek.tcbasearchitecture.helper.KRealmHelper
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.KSampleActivity
import com.tomaschlapek.tcbasearchitecture.widget.KDebugTree
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject

/**
 * Application class.
 */
class App : Application(), HasActivityInjector {

  /* Public Constants *****************************************************************************/

  @Inject
  lateinit var mAndroidInjector: DispatchingAndroidInjector<Activity>

  /* Public Methods *******************************************************************************/

  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
    MultiDex.install(this)
  }

  override fun onCreate() {
    super.onCreate()

    // Start monitoring crashes.
    if (BuildConfig.DEBUG_MODE) {
      Timber.plant(KDebugTree())
    } else {
      // TODO Uncomment after Crashlytics addition
      //  Fabric.with(this, new Crashlytics());
      //  Timber.plant(new KrashlyticsTree());
      Timber.plant(KDebugTree())
    }


    sAppComponent = DaggerKAppComponent.builder()
      .application(this)
      .build()

    sAppComponent!!.inject(this)

    Stetho.initialize(
      Stetho.newInitializerBuilder(this)
        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
        .enableWebKitInspector(RealmInspectorModulesProvider
          .builder(this).withLimit(1000).build())
        .build())

    //    // Initialize Leak Canary, if enabled (and debug mode is on).
    //    if (BuildConfig.LEAK_CANARY_ENABLED && !LeakCanary.isInAnalyzerProcess(this)) {
    //      mRefWatcher = LeakCanary.install(this);
    //    }

    // Initialize attributes.
    sContextReference = WeakReference<Context>(this)
    sResources = resources
    sAssets = assets
    sDisplayMetrics = sResources!!.displayMetrics
    isInitialized = false
    isInBackground = true
    isDualPane = sResources!!.getBoolean(R.bool.is_dual_pane)
    sHasTwoColumnsInPortrait = sResources!!.getBoolean(R.bool.has_two_columns_in_portrait)
    //    sIconFont = Typeface.createFromAsset(sAssets, "fonts/icons_font.ttf");

    Realm.init(this)
    KRealmHelper.initializeRealmConfig()
  }

  override fun activityInjector(): AndroidInjector<Activity> {
    return mAndroidInjector
  }

  companion object {

    /* Public Constants *****************************************************************************/

    val INIT_ACTIVITY_NAME = KSampleActivity::class.java.name

    /* Private Constants ****************************************************************************/
    /* Private Attributes ***************************************************************************/
    /* Public Static Methods ************************************************************************/

    @JvmStatic
    lateinit private var sAppComponent: KAppComponent

    @JvmStatic
    fun getAppComponent(): KAppComponent {
      return sAppComponent
    }

    /**
     * Application context.
     */
    private var sContextReference: WeakReference<Context>? = null

    /**
     * Application resources.
     */
    @JvmStatic
    private var sResources: Resources? = null

    /**
     * Application assets.
     */
    private var sAssets: AssetManager? = null

    /**
     * Display metrics of primary display.
     */
    private var sDisplayMetrics: DisplayMetrics? = null

    /**
     * Indicates that the application is initializes.
     */
    /**
     * Indicates that the application is initializes.
     */
    /**
     * Marks the application as initialized.
     */
    var isInitialized: Boolean = false

    /**
     * Indicates that the application went to background.
     */
    /**
     * Indicates that the application went to background.
     */
    /**
     * Marks if the application went to background.
     */
    var isInBackground: Boolean = false

    /**
     * Indicates that the application is in dual-pane mode.
     */
    /**
     * Indicates that the application is in dual-pane mode.
     */
    var isDualPane: Boolean = false
      private set

    /**
     * Indicates that the application displays two columns in portrait.
     */
    private var sHasTwoColumnsInPortrait: Boolean = false

    /**
     * Returns application context.
     */
    val context: Context?
      get() = sContextReference!!.get()

    /**
     * Indicates the orientation is portrait.
     */
    val isPortrait: Boolean
      get() = sResources!!.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    /**
     * Indicates the orientation is landscape.
     */
    val isLandscape: Boolean
      get() = !isPortrait

    /**
     * Indicates that the application displays two columns in portrait.
     */
    fun hasTwoColumnsInPortrait(): Boolean {
      return sHasTwoColumnsInPortrait
    }

    /**
     * @see Resources.getString
     */
    fun getResString(id: Int): String {
      return sResources!!.getString(id)
    }

    /**
     * @see Resources.getString
     */
    fun getResString(resourceId: Int, vararg formatArgs: Any): String {
      return sResources!!.getString(resourceId, *formatArgs)
    }

    /**
     * @see Resources.getBoolean
     */
    fun getResBoolean(id: Int): Boolean {
      return sResources!!.getBoolean(id)
    }

    /**
     * @see Resources.getDimensionPixelSize
     */
    fun getResDimension(id: Int): Int {
      return sResources!!.getDimensionPixelSize(id)
    }

    /**
     * @see ContextCompat.getColor
     */
    fun getResColor(id: Int): Int {
      return ContextCompat.getColor(context, id)
    }

    /**
     * @see ContextCompat.getDrawable
     */
    fun getResDrawable(id: Int): Drawable {
      return ContextCompat.getDrawable(context, id)
    }

    /**
     * @see Resources.getBoolean
     */
    val locale: Locale
      get() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          return sResources!!.configuration.locales.get(0)
        } else {
          return sResources!!.configuration.locale
        }
      }

    /**
     * Opens stream of specified asset file.

     * @param assetFileName Asset file name.
     * *
     * *
     * @return Opened stream of asset file.
     */
    fun getAssetFileStream(assetFileName: String): InputStream? {
      var result: InputStream? = null
      try {
        result = sAssets!!.open(assetFileName)
      } catch (exception: IOException) {
        Timber.e(exception, exception.message)
      }

      return result
    }

    /**
     * Converts PX to DP on primary screen.

     * @param px PX to convert.
     * *
     * *
     * @return Resulting DP.
     */
    fun pxToDp(px: Int): Int {
      return (px / sDisplayMetrics!!.density + 0.5).toInt()
    }

    /**
     * Converts DP to PX on primary screen.

     * @param dp DP to convert.
     * *
     * *
     * @return Resulting PX.
     */
    fun dpToPx(dp: Int): Int {
      return (dp * sDisplayMetrics!!.density + 0.5).toInt()
    }
  }


  /* Private Methods ******************************************************************************/
  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/


}
