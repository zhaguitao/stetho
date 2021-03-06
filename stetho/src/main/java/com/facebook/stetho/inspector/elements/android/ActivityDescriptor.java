// Copyright 2004-present Facebook. All Rights Reserved.

package com.facebook.stetho.inspector.elements.android;

import android.app.Activity;
import android.view.View;
import android.view.Window;

import com.facebook.stetho.common.StringUtil;
import com.facebook.stetho.inspector.elements.ChainedDescriptor;

final class ActivityDescriptor
    extends ChainedDescriptor<Activity> implements HighlightableDescriptor {
  @Override
  protected String onGetNodeName(Activity element) {
    String className = element.getClass().getName();
    return StringUtil.removePrefix(className, "android.app.");
  }

  @Override
  protected int onGetChildCount(Activity element) {
    Window window = element.getWindow();
    return (window != null) ? 1 : 0;
  }

  @Override
  protected Object onGetChildAt(Activity element, int index) {
    Window window = element.getWindow();
    if (window == null) {
      throw new IndexOutOfBoundsException();
    } else {
      return window;
    }
  }

  @Override
  public View getViewForHighlighting(Object element) {
    if (getHost() instanceof AndroidDescriptorHost) {
      final AndroidDescriptorHost host = (AndroidDescriptorHost)getHost();
      Activity activity = (Activity)element;
      Window window = activity.getWindow();
      return host.getHighlightingView(window);
    }

    return null;
  }
}
