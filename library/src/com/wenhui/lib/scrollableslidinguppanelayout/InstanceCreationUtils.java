/*
 * Copyright 2013 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wenhui.lib.scrollableslidinguppanelayout;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;

import com.wenhui.lib.scrollableslidinguppanelayout.viewDelegate.AbsListViewDelegate;
import com.wenhui.lib.scrollableslidinguppanelayout.viewDelegate.ScrollYDelegate;

class InstanceCreationUtils {

    private static final String LOG_TAG = "InstanceCreationUtils";

    private static final Class<?>[] VIEW_DELEGATE_CONSTRUCTOR_SIGNATURE = new Class[]{};

    private static final HashMap<Class, Class> BUILT_IN_DELEGATES;
    static {
        BUILT_IN_DELEGATES = new HashMap<Class, Class>();
        BUILT_IN_DELEGATES.put(AbsListViewDelegate.SUPPORTED_VIEW_CLASS, AbsListViewDelegate.class);
    }

    static ScrollableSlidingUpPaneLayoutHelper.ViewDelegate getBuiltInViewDelegate(final View view) {
    	
    	Log.d("ScrollView", view.getClass().getSimpleName() );
    	if( view instanceof SlideableScrollView ){
    		return (SlideableScrollView)view;
    	}
    	
    	if( view instanceof AbsListView ){
    		return new AbsListViewDelegate();
    	}
    	
//        final Set<Map.Entry<Class, Class>> entries = BUILT_IN_DELEGATES.entrySet();
//        for (final Map.Entry<Class, Class> entry : entries) {
//            if (entry.getKey().isInstance(view)) {
//                return InstanceCreationUtils.newInstance(view.getContext(),
//                        entry.getValue(), VIEW_DELEGATE_CONSTRUCTOR_SIGNATURE, null);
//            }
//        }

        // Default is the ScrollYDelegate
        return new ScrollYDelegate();
    }

    private static <T> T newInstance(Context context, Class clazz, Class[] constructorSig,
            Object[] arguments) {
        try {
            Constructor<?> constructor = clazz.getConstructor(constructorSig);
            return (T) constructor.newInstance(arguments);
        } catch (Exception e) {
            Log.w(LOG_TAG, "Cannot instantiate class: " + clazz.getName(), e);
        }
        return null;
    }

}
