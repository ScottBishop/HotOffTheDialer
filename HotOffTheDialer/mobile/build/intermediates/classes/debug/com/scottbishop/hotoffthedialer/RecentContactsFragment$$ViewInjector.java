// Generated code from Butter Knife. Do not modify!
package com.scottbishop.HotOffTheDialer;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class RecentContactsFragment$$ViewInjector<T extends com.scottbishop.HotOffTheDialer.RecentContactsFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296340, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131296340, "field 'recyclerView'");
  }

  @Override public void reset(T target) {
    target.recyclerView = null;
  }
}
