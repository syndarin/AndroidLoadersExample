package name.syndarin.loaderstutorial;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Bitmap[]>{
	
	private final static String tag = MainActivity.class.getSimpleName();
	
	private final int PICTURE_LOADER_ID = 0;

	private String[] url = new String[]{
		"http://sphotos-e.ak.fbcdn.net/hphotos-ak-ash3/601713_490200867700905_395731164_n.jpg",
		"http://sphotos-e.ak.fbcdn.net/hphotos-ak-prn1/30377_489664537754538_1651794900_n.jpg",
		"http://sphotos-d.ak.fbcdn.net/hphotos-ak-prn1/549044_486785518042440_1408393007_n.jpg",
		"http://sphotos-h.ak.fbcdn.net/hphotos-ak-ash4/480873_485610574826601_628415646_n.jpg",
		"http://sphotos-a.ak.fbcdn.net/hphotos-ak-prn1/894093_484316121622713_696347851_o.jpg",
		"http://sphotos-g.ak.fbcdn.net/hphotos-ak-prn1/734313_477726168948375_637557596_n.jpg",
		"http://sphotos-f.ak.fbcdn.net/hphotos-ak-ash4/484299_477478645639794_59632882_n.jpg"
	};
	
	LoaderCallbacks<Bitmap[]> loaderCallbacks;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loaderCallbacks = this;
		getLoaderManager().initLoader(PICTURE_LOADER_ID, null, loaderCallbacks);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public Loader<Bitmap[]> onCreateLoader(int arg0, Bundle arg1) {
		Log.i(tag, "onCreateLoader invoked");
		return new PictureLoader(MainActivity.this, url);
	}

	@Override
	public void onLoadFinished(Loader<Bitmap[]> arg0, Bitmap[] arg1) {
		Log.i(tag, "onLoadFinished invoked");
		LinearLayout root = (LinearLayout)findViewById(R.id.rootPane);
		for(Bitmap bm : arg1){
			if(bm!=null){
				ImageView v = new ImageView(this);
				v.setImageBitmap(bm);
				root.addView(v);
			}
		}
	}

	@Override
	public void onLoaderReset(Loader<Bitmap[]> arg0) {
		Log.i(tag, "onLoaderReset invoked");
	}
	


}
