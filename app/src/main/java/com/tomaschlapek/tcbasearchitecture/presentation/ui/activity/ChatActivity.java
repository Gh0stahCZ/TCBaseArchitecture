package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity;

import android.app.IntentService;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import android.support.annotation.Nullable;

import com.tomaschlapek.tcbasearchitecture.R;
import com.tomaschlapek.tcbasearchitecture.databinding.ActivityChatBinding;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.ChatPresenterImpl;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.IChatActivityView;
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base.DrawerActivity;

import timber.log.Timber;

/**
 * Activity for chatting with Firebase.
 * https://medium.com/@dekoservidoni/realtime-chats-with-firebase-in-android-a2a131f94e0c
 */
public class ChatActivity
  extends DrawerActivity<IChatActivityView, ChatPresenterImpl>
  implements IChatActivityView {

  /* Private Constants ****************************************************************************/
  /* Private Attributes ***************************************************************************/

  private ActivityChatBinding mViews;

  /* Public Methods *******************************************************************************/

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    int menuRes = R.menu.menu_chat;
    getMenuInflater().inflate(menuRes, menu);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int itemId = item.getItemId();
    if (itemId == R.id.share_item) {
      getPresenter().onShareDialog();
      return true;
    }

    return false;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mViews = DataBindingUtil
      .inflate(getLayoutInflater(), R.layout.activity_chat, getContentContainer(), true);

    Timber.i("Binding: " + mViews);

    onCreatePresenter(savedInstanceState);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    init();
  }

  /* Private Methods ******************************************************************************/

  private void init() {
    mViews.sampleButton.setOnClickListener(view -> getPresenter().onButtonClick());

    mViews.chatControlPanel.chatControlLike
      .setOnClickListener(view -> getPresenter().onLikeClick());
    mViews.chatControlPanel.chatControlSend
      .setOnClickListener(view -> sendMessage());
  }

  private void sendMessage() {
    String messageToSend = mViews.chatControlPanel.chatControlInput.getText() != null ?
      mViews.chatControlPanel.chatControlInput.getText().toString() : null;

    if (TextUtils.isEmpty(messageToSend)) {
      Toast.makeText(this, R.string.chat_message_empty, Toast.LENGTH_SHORT).show();
      return;
    }

    showLoading(true);

    getPresenter().onSendClick();
  }

  private void showLoading(boolean loading) {
    IntentService
    mViews.chatControlPanel.chatControlInput.setEnabled(!loading);
    mViews.chatControlPanel.chatControlSend.setVisibility(loading ? View.GONE : View.VISIBLE);
    mViews.chatControlPanel.chatControlProgress.setVisibility(loading ? View.VISIBLE : View.GONE);
  }

  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/

  @Override
  public Class getPresenterClass() {
    return ChatPresenterImpl.class;
  }

  @Override
  public void showToast(String text) {
    mViews.sampleTextView.setText(getPresenter().getSharingText());
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onMessageDelivered() {
    mViews.chatControlPanel.chatControlInput.setText(null);
    showLoading(false);
  }

  @Override
  public void onMessageFailed() {
    Toast.makeText(this, R.string.chat_message_connection_problem, Toast.LENGTH_SHORT).show();
    showLoading(false);
  }

  @Override
  public void onLikeDelivered() {
    // TODO
  }
}
