package viewer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import pl.droidsonroids.gif.R;

import java.io.IOException;

/**
 * @author archko
 */
public class GifViewer extends Activity {

    public static final String EXTRA_URL="ak_gif";
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

        Intent intent=getIntent();
        String path=null;
        //System.out.println("intent:"+getIntent());
        if (null!=getIntent()&&null!=getIntent().getData()) {
            System.out.println("data:"+getIntent().getData());
            if (Intent.ACTION_VIEW.equals(intent.getAction())) {
                Uri uri=intent.getData();
                System.out.println("URI to open is: "+uri);
                if (uri.toString().startsWith("content://")) {
                    try {
                        Cursor cursor=getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
                        if (cursor.moveToFirst()) {
                            String str=cursor.getString(0);
                            if (str==null) {
                                System.out.println("Couldn't parse data in intent");
                            } else {
                                uri=Uri.parse(str);
                                path=Uri.decode(uri.getEncodedPath());
                            }
                        }
                    } catch (Exception e2) {
                        System.out.println("Exception in Transformer Prime file manager code: "+e2);
                    }
                } else if (uri.toString().startsWith("file")) {
                    path=uri.toString().substring(7);
                }
            } else {
                path=intent.getStringExtra(EXTRA_URL);
            }
        }

        System.out.println("path:"+path);
        if (!TextUtils.isEmpty(path)) {
            try {
                GifDrawable drawable=new GifDrawable(path);
                mGifImageView.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
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
