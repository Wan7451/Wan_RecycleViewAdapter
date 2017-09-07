package com.wan7451.wanadapter.mylibrary;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 可以添加 Header,Footer的Adapter
 */
public abstract class SimpleWanAdapter<T> extends WanAdapter<T> {

    private int layoutRes;
    private Class<?> cls;

    protected SimpleWanAdapter(Context context, List<T> mDatas, @NonNull @LayoutRes int layoutRes) {
        super(context, mDatas);
        this.layoutRes = layoutRes;
    }

    protected SimpleWanAdapter(Context context, @NonNull @LayoutRes int layoutRes) {
        super(context);
        this.layoutRes = layoutRes;
    }

    protected SimpleWanAdapter(Context context, Class<? extends View> cls) {
        super(context);
        this.cls = cls;
    }


    @Override
    protected WanViewHolder onCreateWanViewHolder(ViewGroup parent, int viewType) {
        if (layoutRes != 0)
            return new SimpleHolder(getInflater().inflate(layoutRes, parent, false),
                    this);
        if (cls != null) {
            try {
                Constructor<?> constructor = cls.getConstructor(mContext.getClass());
                constructor.newInstance(mContext);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private static class SimpleHolder extends WanViewHolder {

        protected SimpleHolder(View itemView) {
            super(itemView);
        }

        public <T> SimpleHolder(View itemView, WanAdapter<T> adapter) {
            super(itemView, adapter);
        }
    }
}
