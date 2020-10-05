package com.fastxs.simple.business.test.mvvm;

import com.fast.fastxs.mvvm.XsBaseViewRender;
import android.content.Context;

/**
 * Created  on 2020-10-05 21:08:17.
 */
public class XXXView extends XsBaseViewRender {

   public XXXView(Context context) {
      super(context);
   }
   public int getLayoutId(){
      return android.R.layout.list_content;
   }
}