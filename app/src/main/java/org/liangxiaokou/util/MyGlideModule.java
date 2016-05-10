package org.liangxiaokou.util;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.ViewTarget;

import org.liangxiaokou.config.Constants;
import org.liangxiaokou.module.R;

public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //builder.setMemoryCache(new LruResourceCache(yourSizeInBytes)); 设置内存
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context.getApplicationContext(), Constants.IMAGE_CACHE, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE));
        builder.setDecodeFormat(DecodeFormat.ALWAYS_ARGB_8888);
        ViewTarget.setTagId(R.id.glide_tag_id);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}