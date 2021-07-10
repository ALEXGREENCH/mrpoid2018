package com.mrpoid.mrplist.moduls;

import java.lang.ref.WeakReference;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mrpoid.mrpliset.R;

/**
 * 
 * @author Yichou 
 *
 */
public enum FileType {
	FOLDER(R.drawable.ic_fos_folder2, null),	//文件夹 - folder
	
	TEXT(R.drawable.ic_fos_text,
			new String[]{".txt"}),		//文本文件 - text file
			
	ARCHIVE(R.drawable.ic_fos_zip,
			new String[]{".zip", ".7z", ".gz", ".tar"}),	//压缩文件 - compressed file
			
	AUDIO(R.drawable.ic_fos_music,
			new String[]{".mid", ".mp3", ".wav"}),		//声音文件 - sound file
			
	VIDEO(R.drawable.ic_fos_media,
			new String[]{".mp4", ".3gp", ".rm"}),		//文件 - file
			
	IMAGE(R.drawable.ic_fos_picture,
			new String[]{".png", ".jpg", ".bmp", ".gif"}),		//图像文件 - image file
	
	APK(R.drawable.ic_fos_apk,
			new String[]{".apk"}),		//图像文件 - image file
			
	MRP(R.drawable.ic_fos_mrp2,
			new String[]{".mrp"}),		//图像文件 - image file
			
	NOTYPE(R.drawable.ic_fos_unknow,
			null);		//无类型文件 - Untyped file
	
	private final int icon;
	private final String[] regExts;
	private WeakReference<Bitmap> mBitmapIcon;
	
	private FileType(int icon, String[] regExts) {
		this.icon = icon;
		this.regExts = regExts;
	}
	
	public int getIconRes() {
		return icon;
	}
	
	public String[] getRegExts() {
		return regExts;
	}
	
	public Bitmap getIconBitmap(Resources r) {
		Bitmap bmp = mBitmapIcon==null? null : mBitmapIcon.get();
		if(bmp == null || bmp.isRecycled()) {
			bmp = BitmapFactory.decodeResource(r, icon);
			mBitmapIcon = new WeakReference<>(bmp);
		}
		
		return bmp;
	}
	
	public static FileType getTypeByName(String name) {
		if(name == null || name.indexOf('.') == -1)
			return NOTYPE;
		
		int i = name.lastIndexOf('.'); //最后一个 . - the last one .

		for(FileType type : FileType.values()) {
			String[] exts = type.regExts;
			
			if(exts == null) continue;
			
			for(String ext : exts) {
				if(name.regionMatches(true, i, ext, 0, ext.length())) {
					return type;
				}
			}
		}
		
		return NOTYPE;
	}
	
	public static void loadIcons(Resources r) {
		for(FileType type : FileType.values()) {
			type.getIconBitmap(r);
		}
	}

	public static void unloadIcons() {
		for(FileType type : FileType.values()) {
			Bitmap bmp = type.mBitmapIcon==null? null : type.mBitmapIcon.get();
			if(bmp != null && !bmp.isRecycled()) {
				bmp.recycle();
			}
			
			type.mBitmapIcon = null;
		}
	}
}
