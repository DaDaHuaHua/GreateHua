package com.example.commonlibrary.util;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil {

	private static final double KB = 1024.0;
	private static final double MB = KB * KB;
	private static final double GB = KB * KB * KB;

	/**
	 * 存储文件到指定sdcard目录,此方法所有路径自动添加SD_PATH目录, 比如要存放位置为sdcard/p1/file.txt,path参数应为p1
	 * .如sdcard/p1/p2/file.txt,path参数应为p1/p2, 注意该方法不关闭参数中的输入流is,请调用后自行处理is的释放问题
	 * 
	 * @param path
	 *            文件路径
	 * @param is
	 *            要写入文件的输入流
	 * @return 是否已成功写入文件,如成功返回true,否则false
	 */
	public static String saveLocalFile(String path, InputStream is) {
		if (StringUtil.isBlank(path) || is == null) {
			return null;
		}

		FileOutputStream fos = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(path);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();

			byte[] data = convertInToByte(is);
			if (data == null || data.length == 0) {
				return null;
			}
			fos = new FileOutputStream(file);
			fos.write(data);
			fos.flush();
			return file.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fos = null;
			}
		}
	}

	/**
	 * 存储文件到指定sdcard目录 注意该方法不关闭参数中的输入流is,请调用后自行处理is的释放问题
	 * 
	 * @param path
	 *            文件路径,注意此处需要绝对路径,如:/mnt/sdcard/gaiay/marriage/hello.jpg
	 * @return 是否已成功写入文件,如成功返回true,否则false
	 */
	public static boolean saveLocalFile(String path, byte[] data) {
		if (StringUtil.isBlank(path) || data == null || data.length <= 0) {
			return false;
		}
		long t = System.currentTimeMillis();
		FileOutputStream fos = null;
		boolean res = true;
		String[] strs = path.split("/");
		if (strs == null || strs.length == 0) {
			return false;
		}
		String name = strs[strs.length - 1];
		try {
			path = path.substring(0, path.length() - name.length());
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(path + name);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();

			fos = new FileOutputStream(file);
			fos.write(data);
			fos.flush();
		} catch (Exception e) {
			res = false;
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fos = null;
			}
		}
		return res;
	}

	public static InputStream getInputStreamFromFile(String path) throws FileNotFoundException {
		if (StringUtil.isBlank(path)) {
			return null;
		}
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		return new FileInputStream(file);
	}

	/**
	 * 将is输入流转换为字符串,注意该方法不关闭参数中的输入流is,请调用后自行处理is的释放问题
	 * 
	 * @param is
	 *            输入流
	 * @return 转换成功后的字符串
	 * @throws Exception
	 *             错误信息
	 */
	public static String convertInToStr(InputStream is) throws Exception {

		if (is == null) {
			return null;
		}

		return new String(convertInToByte(is));
	}

	/**
	 * 将is输入流转换为字符串,注意该方法不关闭参数中的输入流is,请调用后自行处理is的释放问题
	 * 
	 * @return 转换成功后的字符串
	 * @throws Exception
	 *             错误信息
	 */
	public static InputStream convertStringToStream(String str) throws Exception {
		if (StringUtil.isBlank(str)) {
			return null;
		}
		return new ByteArrayInputStream(str.trim().getBytes());
	}

	/**
	 * 将输入流转换为byte数组,注意该方法不关闭参数中的输入流is,请调用后自行处理is的释放问题
	 * 
	 * @param is
	 *            输入流
	 * @return 转换陈功后的byte数组
	 * @throws Exception
	 *             错误信息
	 */
	public static byte[] convertInToByte(InputStream is) {

		if (is == null) {
			return null;
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len;
		try {
			while ((len = is.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = baos.toByteArray();
		try {
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		baos = null;
		return data;
	}

	/**
	 * 删除指定路径的文件,如果为目录,则删除该目录及下所有文件
	 * 
	 * @param path
	 *            指定删除的文件
	 * @return 返回是否删除成功,true成功,false失败
	 */
	public static boolean deleteFile(String path) {

		if (StringUtil.isBlank(path)) {
			return false;
		}

		File file = new File(path);
		boolean res = true;
		if (file.exists() && file.isDirectory()) {
			res = deleteFolder(path);
		} else if (file.exists()) {
			res = file.delete();
		}
		return res;
	}

	/**
	 * 删除指定目录的所有文件及此目录
	 * 
	 * @param path
	 *            指定的目录
	 * @return 是否删除成功,成功返回true,失败false;
	 */
	public static boolean deleteFolder(String path) {

		if (StringUtil.isBlank(path)) {
			return false;
		}

		boolean result = false;
		File folder = new File(path);
		try {
			String childs[] = folder.list();
			if (childs == null || childs.length <= 0) {
				if (folder.delete()) {
					result = true;
				}
			} else {
				for (int i = 0; i < childs.length; i++) {
					String childName = childs[i];
					String childPath = folder.getPath() + File.separator + childName;
					File filePath = new File(childPath);
					if (filePath.exists() && filePath.isFile()) {
						if (filePath.delete()) {
							result = true;
						} else {
							result = false;
							break;
						}
					} else if (filePath.exists() && filePath.isDirectory()) {
						if (deleteFolder(filePath.getAbsolutePath())) {
							result = true;
						} else {
							result = false;
							break;
						}
					}
				}
			}
			folder.delete();
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	/**
	 * 获取SD路径
	 * 
	 * @return 如果获取成功返回绝对路径,获取失败返回null
	 */
	public static String getSdcardPath() {
		String res = null;
		if (isSDCard()) {
			res = Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return res;
	}

	/**
	 * 判断SDcard是否可用
	 */
	public static boolean isSDCard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 判断SDcard是否有足够空间,以10兆为判断标准
	 * 
	 * @return SDcard不可用或者小于10兆返回false,否者返回true
	 */
	public static boolean isSDCardFree() {
		if (isSDCard()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs statfs = new StatFs(path.getPath());
			long blocSize = statfs.getBlockSize();
			long availaBlock = statfs.getAvailableBlocks();
			if (availaBlock * blocSize > 1024 * 1024 * 0.5) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取SDCard可用大小
	 * 
	 * @return 返回字节数
	 */
	static public long getAvailableSDCardSize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * 复制某文件到指定文件
	 * 
	 * @param from
	 *            源文件的绝对路径
	 * @param to
	 *            目标文件的绝对路径
	 * @return 是否复制成功
	 */
	public static boolean copyFileToFile(String from, String to) {
		if (StringUtil.isBlank(from) || StringUtil.isBlank(to)) {
			return false;
		}
		FileOutputStream fileOutputStream = null;
		FileInputStream fileInputStream = null;
		try {
			File imgFile = new File(from);
			if (!imgFile.exists()) {
				return false;
			}
			File temp = new File(to);
			if (!temp.exists()) {
				File dirFile = new File(to.split(to.split("/")[to.split("/").length - 1])[0]);
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}
			} else {
				temp.delete();
			}
			temp.createNewFile();
			fileOutputStream = new FileOutputStream(temp);
			fileInputStream = new FileInputStream(imgFile);
			fileOutputStream.write(convertInToByte(fileInputStream));
			fileOutputStream.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
					fileOutputStream = null;
				}
				if (fileInputStream != null) {
					fileInputStream.close();
					fileInputStream = null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 复制某文件到指定文件的结尾
	 * 
	 * @param from
	 *            源文件的绝对路径
	 * @param to
	 *            目标文件的绝对路径
	 * @return 是否复制成功
	 */
	public static boolean copyFileAddToFile(String from, String to) {
		if (StringUtil.isBlank(from) || StringUtil.isBlank(to)) {
			return false;
		}
		FileOutputStream fileOutputStream = null;
		FileInputStream fileInputStream = null;
		try {
			File imgFile = new File(from);
			if (!imgFile.exists()) {
				return false;
			}
			File temp = new File(to);
			if (!temp.exists()) {
				File dirFile = new File(to.split(to.split("/")[to.split("/").length - 1])[0]);
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}
			}
			fileOutputStream = new FileOutputStream(temp);
			fileInputStream = new FileInputStream(imgFile);
			FileInputStream in = new FileInputStream(temp);
			byte[] buff2 = convertInToByte(in);
			in.close();
			in = null;
			byte[] buff = convertInToByte(fileInputStream);
			byte[] buff3 = new byte[buff.length + buff2.length];
			for (int i = 0; i < buff3.length; i++) {
				if (i < buff2.length) {
					buff3[i] = buff2[i];
				} else {
					buff3[i] = buff[i - buff2.length];
				}
			}
			fileOutputStream.write(buff3);
			fileOutputStream.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
					fileOutputStream = null;
				}
				if (fileInputStream != null) {
					fileInputStream.close();
					fileInputStream = null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public static final int BUFSIZE = 1024 * 8;

	public static void mergeFiles(String outFile, String[] files) {
		FileChannel outChannel = null;
		try {
			outChannel = new FileOutputStream(outFile, true).getChannel();
			for (String f : files) {
				FileChannel fc = new FileInputStream(f).getChannel();
				ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
				while (fc.read(bb) != -1) {
					bb.flip();
					outChannel.write(bb);
					bb.clear();
				}
				fc.close();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (outChannel != null) {
					outChannel.close();
				}
			} catch (IOException ignore) {
				ignore.printStackTrace();
			}
		}
	}

	public static void mergeFile(String toFile, String fromFile) {
		FileChannel outChannel = null;
		try {
			outChannel = new FileOutputStream(toFile, true).getChannel();
			FileChannel fc = new FileInputStream(fromFile).getChannel();
			ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
			while (fc.read(bb) != -1) {
				bb.flip();
				outChannel.write(bb);
				bb.clear();
			}
			fc.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (outChannel != null) {
					outChannel.close();
				}
			} catch (IOException ignore) {
				ignore.printStackTrace();
			}
		}
	}

	/**
	 * 查看某个文件是否存在
	 * 
	 * @param path
	 *            指定的路径
	 * @return true存在
	 */
	public static boolean isFileExists(String path) {
		if (StringUtil.isBlank(path)) {
			return false;
		}
		File file = new File(path);
		return file.exists();
	}

	public static byte[] getByteFromFile(String path) throws IOException {
		if (StringUtil.isBlank(path)) {
			return null;
		}
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		return convertInToByte(new FileInputStream(file));
	}

	/**
	 * 计算文件大小
	 * 
	 * @param size
	 * @return
	 */
	public static String showFileSize(long size) {

		String fileSize;
		if (size < KB)
			fileSize = size + "B";
		else if (size < MB)
			fileSize = String.format("%.1f", size / KB) + "KB";
		else if (size < GB)
			fileSize = String.format("%.1f", size / MB) + "MB";
		else
			fileSize = String.format("%.1f", size / GB) + "GB";

		return fileSize;
	}

	/** 显示SD卡剩余空间 */
	public static String showFileAvailable() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
			long blockSize = sf.getBlockSize();
			long blockCount = sf.getBlockCount();
			long availCount = sf.getAvailableBlocks();
			return showFileSize((blockCount - availCount) * blockSize);
		}
		return "";
	}

	public static String showFileSurplus() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
			long blockSize = sf.getBlockSize();
			long availCount = sf.getAvailableBlocks();
			return showFileSize(availCount * blockSize);
		}
		return "";
	}

	/**
	 * 得到远程文件大小
	 * 
	 * @param path
	 * @return
	 */
	public static int getRemoteFileSize(String path) {
		int len = 0;
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setConnectTimeout(5000);
			len = conn.getContentLength();
			conn.disconnect();
			if (len == -1) {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return len;
	}

	/**
	 * 得到扩展名
	 * 
	 * @param url
	 * @return
	 */
	public static String getUrlExtension(String url) {
		int lastIndexOf = url.lastIndexOf('.');
		return url.substring(lastIndexOf, url.length());
	}

	/**
	 * 删除指定路径的文件
	 * 
	 * @param path
	 * @return
	 * @throws
	 */
	public static boolean delFile(String path) throws NullPointerException {
		if (path == null || "".equals(path)) {
			throw new NullPointerException( "path 不能为空");
		}
		File file = new File(path);
		if (file.canRead() && file.exists()) {
			file.delete();
			return true;
		}
		return false;
	}

	/**
	 * 获取指定路径文件的大小，如果文件不存在返回-1
	 * 
	 * @param path
	 * @param type
	 *            返回类型 K,M,G
	 * @return
	 */
	public static float getFileSize(String path, String type) {
		File file = new File(path);
		if (!isFileExists(path) || !file.isFile()) {
			return -1;
		}
		file.length();
		if (StringUtil.isBlank(type)) {
			return file.length() / (float) MB;
		} else if (type.equals("K")) {
			return file.length() / (float) KB;
		} else if (type.equals("M")) {
			return file.length() / (float) MB;
		} else if (type.equals("G")) {
			return file.length() / (float) GB;
		}
		return 0;
	}

	/**
	 * 比较剩余空间与指定大小空间
	 * 
	 * @param fileSize
	 *            要比较的空间大小
	 * @return true 表示没有足够的空间 false 表示还足够的空间大小
	 */
	public static boolean checkSpaceSize(long fileSize) {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
			long blockSize = sf.getBlockSize();
			long availCount = sf.getAvailableBlocks();
			if (fileSize > (availCount * blockSize)) {
				return true;
			}
		}
		return false;
	}

	@SuppressLint("NewApi")
	public static String getMediaPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for MediaStore Uris, and other file-based
	 * ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}

	public static void saveBitmap2Local(String path, Bitmap bmp) {
		if (path != null && bmp != null && !bmp.isRecycled()) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();


			bmp.compress(Bitmap.CompressFormat.JPEG, 80, baos);
			BitmapFactory.Options options= new BitmapFactory.Options();
			options.inSampleSize=2;
			System.out.println("bmp.getByteCount=========="+bmp.getByteCount());
			ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
			Bitmap bitmap = BitmapFactory.decodeStream(bis,  null ,  options );
			bitmap.compress(Bitmap.CompressFormat.JPEG,80,baos);
			System.out.println("bitmap.getByteCount=========="+bitmap.getByteCount());
			saveLocalFile(path, baos.toByteArray());
		}
	}

	/**
	 * lib压缩 图片(Bitmap) 默认压缩 40%
	 * 
	 * @param bit
	 * @param dest
	 *            目的路径
	 * @throws Exception
	 */
	public static void saveTodestAndCompress(Bitmap bit, String dest) throws Exception, UnsatisfiedLinkError {
		int quality = 40;
		NativeUtil.compressBitmap(bit, quality, dest, true);
		bit.recycle();
	}

	/**
	 * 复制文件到指定目录并且压缩保存
	 * 
	 * @param path
	 * @param dest
	 *            目标文件路径
	 * @throws Exception
	 */
	public static void copyPicTodestAndCompress(String path, String dest) throws Exception, UnsatisfiedLinkError {

		Bitmap loadBitmapFromFile = BitmapFactory.decodeFile(path);
		int degreen = readPictureDegree(path);
		if (degreen == 90 || degreen == 180 || degreen == 270) {
			try {
				Matrix matrix = new Matrix();
				matrix.setRotate(degreen);
				Bitmap bm2 = Bitmap.createBitmap(loadBitmapFromFile, 0, 0, loadBitmapFromFile.getWidth(),
						loadBitmapFromFile.getHeight(), matrix, true);
				saveTodestAndCompress(bm2, dest);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
				saveTodestAndCompress(loadBitmapFromFile, dest);
			}
		} else {
			saveTodestAndCompress(loadBitmapFromFile, dest);
		}

	}

	/**
	 * 复制图片到指定路径(解决三星拍照旋转问题)
	 * 
	 * @param path
	 * @param dest
	 */
	public static void copyPicTodest(String path, String dest) {
		int degreen = readPictureDegree(path);
		if (degreen == 90 || degreen == 180 || degreen == 270) {
			try {
				Bitmap loadBitmapFromFile = BitmapFactory.decodeFile(path);
				Matrix matrix = new Matrix();
				matrix.setRotate(degreen);
				Bitmap bm2 = Bitmap.createBitmap(loadBitmapFromFile, 0, 0, loadBitmapFromFile.getWidth(),
						loadBitmapFromFile.getHeight(), matrix, true);
				loadBitmapFromFile.recycle();
				FileUtil.saveBitmap2Local(dest, bm2);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
				FileUtil.copyFileToFile(path, dest);
			}
		} else {
			FileUtil.copyFileToFile(path, dest);
		}

	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 创建多个文件夹
	 * @param paths
	 */
	public static boolean mkdirs(String[] paths){
		if (!isSDCard()){
			ToastUtil.showMessage("sdCard不可用");
			return false;
		}
		if (!isSDCardFree()){
			ToastUtil.showMessage("没有足够的存储空间");
			return false;
		}
		if (null!=paths&&paths.length>0){
			boolean isCreated=false;
			for (String path : paths) {
				File file = new File(path);
				if (!file.exists()){
					isCreated=file.mkdirs();
				}
			}
			return isCreated;
		}
		return false;
	}

	public static boolean mkdir(String path){
		if (!isFileExists(path)){
			File file = new File(path);
			file.mkdirs();
		}
		return true;
	}
}
