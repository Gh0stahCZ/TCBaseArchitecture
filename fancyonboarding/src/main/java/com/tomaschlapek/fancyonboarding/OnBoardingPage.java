package com.tomaschlapek.fancyonboarding;

import android.content.Context;
import android.text.TextUtils;

import com.tomaschlapek.fancyonboarding.interfaces.IBuilder;
import com.tomaschlapek.fancyonboarding.interfaces.OnPermissionRequestListener;

import java.util.UUID;

public class OnBoardingPage {

  /* Private Attributes ***************************************************************************/

  private final String id;
  private String titleText;
  private String messageText;
  private String nextButtonText;
  private String positiveButtonText;
  private String negativeButtonText;

  private int backgroundImage;
  private String permissionType;
  private OnPermissionRequestListener permissionRequestedListener;

  /* Public Static Methods ************************************************************************/

  public static class Builder implements IBuilder<OnBoardingPage> {

    private final String id;
    private String titleText;
    private String messageText;
    private String nextButtonText;
    private String positiveButtonText;
    private String negativeButtonText;

    private int backgroundImage;
    private String permissionType;
    private OnPermissionRequestListener permissionRequestedListener;

    public Builder() {
      this.id = UUID.randomUUID().toString();
    }

    public Builder(String titleText, String message) {
      this.id = UUID.randomUUID().toString();

      if (!TextUtils.isEmpty(titleText)) {
        this.titleText = titleText.trim();
      }
      if (!TextUtils.isEmpty(message)) {
        this.messageText = message.trim();
      }
    }

    public Builder title(String text) {
      if (!TextUtils.isEmpty(text)) {
        this.titleText = text.trim();
      }
      return this;
    }

    public Builder title(Context context, int resId) {
      this.titleText = context.getResources().getString(resId);
      return this;
    }

    public Builder message(String text) {
      if (!TextUtils.isEmpty(text)) {
        this.messageText = text.trim();
      }
      return this;
    }

    public Builder message(Context context, int resId) {
      this.messageText = context.getResources().getString(resId);
      return this;
    }

    public Builder positiveButtonText(String text) {
      if (!TextUtils.isEmpty(text)) {
        this.positiveButtonText = text.trim();
      }
      return this;
    }

    public Builder positiveButtonText(Context context, int resId) {
      this.positiveButtonText = context.getResources().getString(resId);
      return this;
    }

    public Builder negativeButtonText(String text) {
      if (!TextUtils.isEmpty(text)) {
        this.negativeButtonText = text.trim();
      }
      return this;
    }

    public Builder negativeButtonText(Context context, int resId) {
      this.negativeButtonText = context.getResources().getString(resId);
      return this;
    }

    public Builder nextButtonText(String text) {
      if (!TextUtils.isEmpty(text)) {
        this.nextButtonText = text.trim();
      }
      return this;
    }

    public Builder nextButtonText(Context context, int resId) {
      this.nextButtonText = context.getResources().getString(resId);
      return this;
    }

    public Builder permission(String permissionType,
      OnPermissionRequestListener onPermissionRequestedListener) {
      this.permissionType = permissionType;
      this.permissionRequestedListener = onPermissionRequestedListener;
      return this;
    }

    public Builder backgroundImage(int resId) {
      this.backgroundImage = resId;
      return this;
    }

    @Override
    public OnBoardingPage build() {
      return new OnBoardingPage(this);
    }
  }

  /* Constructor **********************************************************************************/

  private OnBoardingPage(OnBoardingPage.Builder builder) {
    this.id = builder.id;
    this.titleText = builder.titleText;
    this.messageText = builder.messageText;
    this.positiveButtonText = builder.positiveButtonText;
    this.negativeButtonText = builder.negativeButtonText;
    this.nextButtonText = builder.nextButtonText;
    this.permissionType = builder.permissionType;
    this.permissionRequestedListener = builder.permissionRequestedListener;
    this.backgroundImage = builder.backgroundImage;
  }

  /* Getters / Setters ****************************************************************************/

  public String getId() {
    return id;
  }

  public int getBackgroundImage() {
    return backgroundImage;
  }

  public String getMessageText() {
    return messageText;
  }

  public String getNegativeButtonText() {
    return negativeButtonText;
  }

  public String getNextButtonText() {
    return nextButtonText;
  }

  public String getPositiveButtonText() {
    return positiveButtonText;
  }

  public String getTitleText() {
    return titleText;
  }

  public String getPermissionType() {
    return permissionType;
  }

  public OnPermissionRequestListener getPermissionRequestedListener() {
    return permissionRequestedListener;
  }

  public boolean hasPermission() {
    return !TextUtils.isEmpty(getPermissionType());
  }
}
