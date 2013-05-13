package name.syndarin.loaderstutorial;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class PictureLoader extends AsyncTaskLoader<Bitmap[]> {

	private final String tag = getClass().getSimpleName();
	
	private String[] url;
	
	private Bitmap[] data;

	public PictureLoader(Context context, String[] url) {
		super(context);
		this.url = url;
	}

	@Override
	public Bitmap[] loadInBackground() {
		Log.i(tag, "loadInBackground invoked");
		data = new Bitmap[url.length];
		for(int i = 0, max = data.length; i < max; i++){
			Bitmap b = null;
			try {
				URL pictureUrl = new URL(url[i]);
				b = BitmapFactory.decodeStream(pictureUrl.openStream());
			} catch (MalformedURLException e) {
				e.printStackTrace();
				Log.e(tag, e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				Log.e(tag, e.getMessage());
			}
			data[i] = b;
		}
		return data;
	}

	@Override
	public void deliverResult(Bitmap[] data) {
		Log.i(tag, "deliverResult invoked");
		if(isReset()){
			releaseResources(data);
			return;
		}
		
		Bitmap[] oldData = this.data;
		this.data = data;
		
		if(isStarted()){
			super.deliverResult(data);
		}
		
		if(oldData != null && oldData != data){
			releaseResources(oldData);
		}
	}
	
	private void releaseResources(Bitmap[] data){
		if(data != null && data.length > 0){
			for(Bitmap b : data){
				b.recycle();
				b = null;
			}
		}
	}

	@Override
	public void onCanceled(Bitmap[] data) {
		super.onCanceled(data);
		Log.i(tag, "onCanceled invoked");
		releaseResources(data);
	}


	@Override
	protected void onReset() {
		Log.i(tag, "onReset invoked");
		onStopLoading();
		
		if(data != null){
			releaseResources(data);
			data = null;
		}
		
		// TODO unregister observer
	}

	@Override
	protected void onStartLoading() {
		Log.i(tag, "onStartLoading invoked");
		if(data != null){
			deliverResult(data);
		}
		
		// TODO register an observer
		
		if(takeContentChanged() || data == null){
			forceLoad();
		}
	}

	@Override
	protected void onStopLoading() {
		Log.i(tag, "onStopLoading invoked");
		cancelLoad();
	}
}
