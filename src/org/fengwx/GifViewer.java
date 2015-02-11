package org.fengwx;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import org.fengwx.gif.GifDrawable;
import org.fengwx.gif.GifImageView;
import org.fengwx.gif.R;

import java.io.IOException;

/**
 * @author archko
 */
public class GifViewer extends Activity {

    public static final String IMAGE_URL="ak_gif";
    GifImageView mGifImageView;
    protected ActionBar mActionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mActionBar=getActionBar();

        mActionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setTitle("GifViewer");

        mGifImageView=(GifImageView) findViewById(R.id.gifview);
        View root=findViewById(R.id.root);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActionBar.isShowing()) {
                    mActionBar.hide();
                } else {
                    mActionBar.show();
                }
            }
        });

        if (null!=getIntent()) {
            Uri uri=getIntent().getData();
            if (null!=uri) {
                try {
                    System.out.println("uri:"+uri);
                    GifDrawable drawable=new GifDrawable(uri.toString());
                    mGifImageView.setImageDrawable(drawable);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                String url=getIntent().getStringExtra(IMAGE_URL);
                if (!TextUtils.isEmpty(url)) {
                    try {
                        GifDrawable drawable=new GifDrawable(uri.toString());
                        mGifImageView.setImageDrawable(drawable);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId=item.getItemId();
        if (itemId==android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation==Configuration.ORIENTATION_PORTRAIT) {
            mActionBar.show();
        } else {
            mActionBar.hide();
        }
    }
}
