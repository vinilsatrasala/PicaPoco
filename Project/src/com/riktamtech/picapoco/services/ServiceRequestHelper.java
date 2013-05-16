package com.riktamtech.picapoco.services;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.riktamtech.picapoco.application.MyApplication;
import com.riktamtech.picapoco.application.MyApplication.ServiceStatusListener;
import com.riktamtech.picapoco.ui.utils.CustomMultiPartEntity;
import com.riktamtech.picapoco.ui.utils.CustomMultiPartEntity.ProgressListener;

/**
 * This class is to request services like login, register etc.
 * 
 * @author Suneel
 * 
 */

public class ServiceRequestHelper {

	private static final String TAG = "ServiceRequestHelper";
	private int responseCode = 0;
	public static boolean shouldIKillTask = false;

	private static String baseUrlLogin = "http://api.picapoco.staging.pictureplix.net";
	public static String baseUrl = "http://pppixpremium.staging.pictureplix.net";

	private static String loginUrl = baseUrlLogin + "/api/token/create.json";
	private static String registerUrl = baseUrlLogin + "/api/public/user.json";
	private static String fogotPassword = baseUrlLogin
			+ "/api/public/user/forgotpassword.json";
	private static String createOrderUrl = baseUrl + "/de/order/createOrder";
	private static String pictureSelection = baseUrl
			+ "/ajax/update/pictureSelectionByDesigner";
	private static String uploadPicturesdataUrl = baseUrl
			+ "/api/picture/upload?orderId=";
	private static String uploadPicturesUrl = baseUrl
			+ "/de/order/updateUpload";
	private static String paymentOptionUrl = baseUrl + "/de/order/payment";
	private static String checkPaymentUrl = baseUrl + "/de/order/checkPayment";
	private static String finishOrderUrl = baseUrl + "/de/order/finish";

	private static String sessionCookie = null,
			Authorization = "WSSE profile=\"UsernameToken\"", xwsse = null;

	// Reviewr urls
	private static String picapocoreviewerBaseUrl = "http://picapoco.staging.pictureplix.net";
	public static String picapocoReviewerImageBaseUrl = "http://ppinterface.staging.pictureplix.net/app_staging.php/designPageImageLayer/read/";
	public static String picapocoReviewerPageBaseUrl = "http://ppinterface.staging.pictureplix.net/app_staging.php/designPage/read/";
	private static String reviewerLogin = picapocoreviewerBaseUrl
			+ "/de/review/login";
	private static String fetchDesignUrl = picapocoreviewerBaseUrl
			+ "/ajax/review/fetchDesign";
	private static String updateBookTitleUrl = picapocoreviewerBaseUrl
			+ "/ajax/review/updateBookTitle?";

	// Community URLs
	private static String commentsUrl = baseUrlLogin + "/api/designpages/";
	private static String likesUrl = baseUrlLogin + "/api/designpages/";

	private static Header[] headers;

	/**
	 * This will verify login and gives the response message
	 * 
	 * @param userName
	 * @param password
	 * @return Message
	 */
	public void login(String userName, String password,
			ServiceStatusListener listener) {
		HttpPost post = new HttpPost(loginUrl);
		try {
			// Value Pair input
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("_username", userName));
			nameValuePairs.add(new BasicNameValuePair("_password", password));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			Log.d(TAG, nameValuePairs.toString());
		} catch (Exception e) {
			listener.onFailure(e);
		}
		new ServiceRequestTask(listener).execute(post);

	}

	/**
	 * This method will register the user to the website.
	 * 
	 * @param userName
	 * @param emailId
	 * @param password
	 * @param location
	 * @param name
	 * @param listener
	 */
	public void register(String userName, String emailId, String password,
			String name, ServiceStatusListener listener) {
		HttpPost post = new HttpPost(registerUrl);
		try {

			// Value Pair input
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("name", userName));
			nameValuePairs.add(new BasicNameValuePair("email", emailId));
			nameValuePairs.add(new BasicNameValuePair("password", password));
			nameValuePairs.add(new BasicNameValuePair("rePassword", name));

			Log.d(TAG, nameValuePairs.toString());
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		} catch (Exception e) {
			listener.onFailure(e);
		}
		new ServiceRequestTask(listener).execute(post);

	}

	/**
	 * This method will help in forgot password.It will work with API to mail
	 * the user reg new password.
	 * 
	 * @param emailId
	 * @return
	 */
	public void forgotPassword(String emailId, ServiceStatusListener listener) {
		HttpPost post = new HttpPost(fogotPassword);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("email", emailId));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		} catch (Exception e) {
			listener.onFailure(e);
		}
		new ServiceRequestTask(listener).execute(post);

	}

	public void dummyRequestToMaintainSession(ServiceStatusListener dumyListener) {
		HttpPost post = new HttpPost(baseUrl + "/de/order/style");
		new ServiceRequestTask(dumyListener).execute(post);

	}

	/**
	 * This will create an order and will return oeder id to listener
	 * 
	 * @param styleId
	 *            = album category selected
	 * @param typeId
	 *            = album format selected
	 * @param pageAmount
	 *            = total number of pages selected
	 * @param fotosPerPageId
	 *            = grafiker or eigene option value
	 * @param listener
	 */
	public void createOrder(int styleId, int typeId, int pageAmount,
			int fotosPerPageId, ServiceStatusListener listener) {
		HttpPost post = new HttpPost(createOrderUrl);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(
					"order[fotos_per_page_id]", Integer
							.toString(fotosPerPageId)));
			nameValuePairs.add(new BasicNameValuePair("order[page_amount]",
					Integer.toString(pageAmount)));
			nameValuePairs.add(new BasicNameValuePair("order[style_id]",
					Integer.toString(styleId)));
			nameValuePairs.add(new BasicNameValuePair("order[type_id]", Integer
					.toString(typeId)));

			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			setDefalutHeaders(post);
			Log.d(TAG, "" + nameValuePairs);
		} catch (Exception e) {
			listener.onFailure(e);
		}
		new ServiceRequestTask(listener).execute(post);

	}

	/**
	 * This will enable to update designer type to server
	 * 
	 * @param picture_selection_by_designer
	 * @param listener
	 */
	public void pictureSelection(boolean picture_selection_by_designer,
			ServiceStatusListener listener) {
		HttpPost post = new HttpPost(pictureSelection);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(
					"picture_selection_by_designer", ""
							+ picture_selection_by_designer));

			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			setDefalutHeaders(post);
			Log.d(TAG, "" + nameValuePairs);

		} catch (Exception e) {
			listener.onFailure(e);
		}
		new ServiceRequestTask(listener).execute(post);

	}

	/**
	 * Uploading pictures data
	 * 
	 * @param orderId
	 * @param listener
	 */
	public void uploadPictures(String orderId, ServiceStatusListener listener) {

		new ServiceRequestUploadTask(listener).execute(new HttpPost(
				uploadPicturesdataUrl));

	}

	/**
	 * uploading picture details
	 * 
	 * @param listener
	 */
	public void uploadPictureNames(ServiceStatusListener listener) {

		HttpPost post = new HttpPost(uploadPicturesUrl);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			for (int i = 0; i < MyApplication.currentAlbum.selectedImagesPaths
					.size(); i++) {
				nameValuePairs.add(new BasicNameValuePair(
						MyApplication.currentAlbum.selectedImageNames.get(i)
								+ "[cluster]", "0"));
				nameValuePairs.add(new BasicNameValuePair(
						MyApplication.currentAlbum.selectedImageNames.get(i)
								+ "[marked]", "false"));
				nameValuePairs.add(new BasicNameValuePair(
						MyApplication.currentAlbum.selectedImageNames.get(i)
								+ "[name]", "0"));
				nameValuePairs.add(new BasicNameValuePair(
						MyApplication.currentAlbum.selectedImageNames.get(i)
								+ "[quality]", "0"));
			}
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			setDefalutHeaders(post);
			Log.d(TAG, "" + nameValuePairs);

		} catch (Exception e) {
			listener.onFailure(e);
		}
		new ServiceRequestTask(listener).execute(post);
	}

	/**
	 * this method gets payment details
	 * 
	 * @param listener
	 */
	public void getPaymentDetails(ServiceStatusListener listener) {

		HttpGet get = new HttpGet(paymentOptionUrl);
		setDefaulHeadersForGet(get);
		Log.d(TAG, "Requesting payment");
		new ServiceRequestGetTask(listener).execute(get);

	}

	/**
	 * this method gets payment details
	 * 
	 * @param listener
	 */
	public void getPaymentConfirmationDetails(ServiceStatusListener listener,
			String token, String payerId) {

		HttpGet get = new HttpGet(paymentOptionUrl + "?token=" + token
				+ "&PayerID=" + payerId);
		setDefaulHeadersForGet(get);
		Log.d(TAG, "Requesting confirm payment" + paymentOptionUrl + "?token="
				+ token + "&PayerID=" + payerId);
		new ServiceRequestGetTask(listener).execute(get);

	}

	/**
	 * This method will verify the payment
	 * 
	 * @param listener
	 * @param token
	 */
	public void checkPayment(ServiceStatusListener listener, String token,
			String payerId) {

		HttpGet get = new HttpGet(checkPaymentUrl + "?token=" + token);
		setDefaulHeadersForGet(get);
		Log.d(TAG, "Requesting check payment" + checkPaymentUrl + "?token="
				+ token);
		new ServiceRequestGetTask(listener).execute(get);

	}

	/**
	 * This method will complete the order
	 * 
	 * @param listener
	 */
	public void finishOrder(ServiceStatusListener listener) {

		HttpGet get = new HttpGet(finishOrderUrl);
		setDefaulHeadersForGet(get);
		new ServiceRequestGetTask(listener).execute(get);

	}

	/**
	 * This will set headers for the app
	 * 
	 * @param post
	 */

	public void setDefalutHeaders(HttpPost post) {

		post.addHeader("Authorization", Authorization);
		post.addHeader("X-wsse", xwsse);
		post.addHeader("Cookie", sessionCookie);
		post.addHeader("ACCEPT", "application/json");
		post.addHeader("User-Agent", "PP_API_ANDROID");
		Header[] tempheaders = post.getAllHeaders();
		for (int i = 0; i < tempheaders.length; i++) {
			Log.d(TAG,
					tempheaders[i].getName() + " = "
							+ tempheaders[i].getValue());
		}
	}

	public void setDefaulHeadersForGet(HttpGet get) {
		get.addHeader("Authorization", Authorization);
		get.addHeader("X-wsse", xwsse);
		get.addHeader("Cookie", sessionCookie);
		get.addHeader("ACCEPT", "application/json");
		get.addHeader("User-Agent", "PP_API_ANDROID");
		Header[] tempheaders = get.getAllHeaders();
		for (int i = 0; i < tempheaders.length; i++) {
			Log.d(TAG,
					tempheaders[i].getName() + " = "
							+ tempheaders[i].getValue());
		}
	}

	/**
	 * This method will get session required
	 * 
	 * @param externalId
	 * @param listener
	 */
	public void reviewerLogin(String externalId, ServiceStatusListener listener) {
		HttpGet getRequest = new HttpGet(reviewerLogin + "/" + externalId);
		setDefaulHeadersForGet(getRequest);
		new ServiceRequestGetTask(listener).execute(getRequest);

	}

	/**
	 * This method will fetch design xml file for Viewing album
	 * 
	 * @param listener
	 */
	public void fetchDesigns(ServiceStatusListener listener) {
		HttpGet getRequest = new HttpGet(fetchDesignUrl);
		setDefaulHeadersForGet(getRequest);
		new ServiceRequestGetTask(listener).execute(getRequest);
	}

	/**
	 * This method updates title of the album
	 * 
	 * @param bookTitle
	 * @param designId
	 * @param listener
	 * @throws UnsupportedEncodingException
	 */
	public void updateBookTitle(String bookTitle, String designId,
			ServiceStatusListener listener) throws UnsupportedEncodingException {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("bookTitle", bookTitle));
		// nameValuePairs.add(new BasicNameValuePair("designId", designId));
		HttpGet getRequest = new HttpGet(updateBookTitleUrl
				+ URLEncodedUtils.format(nameValuePairs, "utf-8"));
		setDefaulHeadersForGet(getRequest);
		new ServiceRequestGetTask(listener).execute(getRequest);
	}

	/**
	 * This method will get all comments of specified page.
	 * 
	 * @param designPageId
	 * @param listener
	 * @throws UnsupportedEncodingException
	 */
	public void getComments(String designPageId, String start, String end,
			ServiceStatusListener listener) throws UnsupportedEncodingException {
		HttpGet getRequest = new HttpGet(commentsUrl + designPageId
				+ "/comments.json");
		setDefaulHeadersForGet(getRequest);
		new ServiceRequestGetTask(listener).execute(getRequest);
	}

	/**
	 * This method will Post comments of specified page.
	 * 
	 * @param designPageId
	 * @param listener
	 * @throws UnsupportedEncodingException
	 */
	public void postComments(String designPageId, String comment,
			ServiceStatusListener listener) throws UnsupportedEncodingException {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("comment", comment));
		HttpPost post = new HttpPost(commentsUrl + designPageId
				+ "/comments.json");
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		setDefalutHeaders(post);
		new ServiceRequestTask(listener).execute(post);
	}

	/**
	 * This method will get all likes of specified page.
	 * 
	 * @param designPageId
	 * @param listener
	 * @throws UnsupportedEncodingException
	 */
	public void getLikes(String designPageId, String start, String end,
			ServiceStatusListener listener) throws UnsupportedEncodingException {
		HttpGet getRequest = new HttpGet(commentsUrl + designPageId
				+ "/likes.json");
		setDefaulHeadersForGet(getRequest);
		new ServiceRequestGetTask(listener).execute(getRequest);
	}

	/**
	 * This method will Post like for specified page.
	 * 
	 * @param designPageId
	 * @param listener
	 * @throws UnsupportedEncodingException
	 */
	public void postLike(String designPageId, ServiceStatusListener listener)
			throws UnsupportedEncodingException {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		HttpPost post = new HttpPost(commentsUrl + designPageId + "/likes.json");
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		setDefalutHeaders(post);
		new ServiceRequestTask(listener).execute(post);
	}

	class ServiceRequestTask extends AsyncTask<HttpPost, Object, Object> {
		WeakReference<ServiceStatusListener> listenerReference;

		public ServiceRequestTask(ServiceStatusListener listener) {
			super();
			this.listenerReference = new WeakReference<ServiceStatusListener>(
					listener);
		}

		@Override
		protected Object doInBackground(HttpPost... params) {
			try {
				System.out.println("Started service");
				HttpPost post = params[0];

				HttpParams httpParameters = new BasicHttpParams();

				int timeoutConnection = 5000;
				HttpConnectionParams.setConnectionTimeout(httpParameters,
						timeoutConnection);

				int timeoutSocket = 5000;
				HttpConnectionParams
						.setSoTimeout(httpParameters, timeoutSocket);
				HttpClient httpclient = new DefaultHttpClient(httpParameters);
				HttpResponse httpResponse = httpclient.execute(post);

				// Obtain and Set Headers when login
				headers = httpResponse.getAllHeaders();
				for (int i = 0; i < headers.length; i++) {
					if (headers[i].getName().equalsIgnoreCase("Set-Cookie")) {
						sessionCookie = headers[i].getValue();
						Log.d(TAG, "before split sessionCookie "
								+ sessionCookie);
						sessionCookie = sessionCookie.substring(0,
								sessionCookie.indexOf(";"));
						Log.d(TAG, "sessionCookie " + sessionCookie);
						break;
					}
				}
				responseCode = httpResponse.getStatusLine().getStatusCode();
				Log.d(TAG, "Response Code : "
						+ httpResponse.getStatusLine().getStatusCode());
				String responce = EntityUtils
						.toString(httpResponse.getEntity());

				Log.d(TAG, "Response:" + responce);
				return responce;
			} catch (Exception e) {
				return e;
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			if (listenerReference.get() != null) {
				Log.d(TAG, "result: " + result);
				if (result instanceof Exception)
					listenerReference.get().onFailure((Exception) result);
				else {
					try {

						if (responseCode == 200) {
							if (result.equals(""))
								listenerReference.get().onSuccess(result);
							else {
								JSONObject jsonObject = new JSONObject(
										result.toString());
								if (jsonObject.has("WSSE")) {
									xwsse = jsonObject.getString("WSSE");
									listenerReference.get()
											.onSuccess("Success");
								} else if (jsonObject.has("success")) {
									if (jsonObject.getString("success").equals(
											"error")
											|| jsonObject.getString("success")
													.equals("false")) {
										throw new Exception(
												jsonObject.toString());
									} else {
										listenerReference.get().onSuccess(
												result);
									}
								} else if (jsonObject.has("id")) {

									listenerReference.get().onSuccess(result);
								} else if (jsonObject.has("message")
										&& jsonObject
												.getString("message")
												.equals("Erfolgreich hochgeladen")) {
									listenerReference.get().onSuccess(result);
								} else {
									throw new Exception(jsonObject.toString());
								}
							}
						} else {
							throw new Exception((String) result);
						}

					} catch (Exception e) {
						e.printStackTrace();
						listenerReference.get().onFailure(e);

					}
				}
			}
		}
	}

	class ServiceRequestGetTask extends AsyncTask<HttpGet, Object, Object> {
		WeakReference<ServiceStatusListener> listenerReference;

		public ServiceRequestGetTask(ServiceStatusListener listener) {
			super();
			this.listenerReference = new WeakReference<ServiceStatusListener>(
					listener);
		}

		@Override
		protected Object doInBackground(HttpGet... params) {
			try {
				HttpGet post = params[0];
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse httpResponse = httpclient.execute(post);

				// Obtain and Set Headers when login
				headers = httpResponse.getAllHeaders();
				Log.d("alpha", headers + "");
				for (int i = 0; i < headers.length; i++) {
					if (headers[i].getName().equalsIgnoreCase("Set-Cookie")) {
						sessionCookie = headers[i].getValue();

					}
				}
				responseCode = httpResponse.getStatusLine().getStatusCode();
				Log.d(TAG, "Response Code here: "
						+ httpResponse.getStatusLine().getStatusCode());
				String responce = EntityUtils
						.toString(httpResponse.getEntity());

				Log.d(TAG, "Response:" + responce);
				return responce;
			} catch (Exception e) {
				return e;
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			Log.d(TAG, "result " + result.toString());
			if (listenerReference.get() != null) {
				Log.d(TAG, "result: " + result);
				if (result instanceof Exception)
					listenerReference.get().onFailure((Exception) result);
				else {
					try {
						// JSONObject jsonObject = new JSONObject(
						// result.toString());

						if (responseCode == 200) {
							if (result.equals(""))
								listenerReference.get().onSuccess(result);
							else {
								JSONObject jsonObject = null;
								try {
									jsonObject = new JSONObject(
											result.toString());
								} catch (Exception e) {
									listenerReference.get().onSuccess(result);
								}
								if (jsonObject.has("WSSE")) {
									xwsse = jsonObject.getString("WSSE");
									listenerReference.get()
											.onSuccess("Success");
								} else if (jsonObject.has("success")) {
									if (jsonObject.getString("success").equals(
											"error")
											|| jsonObject.getString("success")
													.equals("false")) {
										throw new Exception(
												jsonObject.toString());
									} else {
										listenerReference.get().onSuccess(
												result);
									}
								} else if (jsonObject.has("comments")) {
									listenerReference.get().onSuccess(result);
								} else {
									throw new Exception(jsonObject.toString());
								}
							}
						} else {
							throw new Exception();
						}

					} catch (Exception e) {
						e.printStackTrace();
						listenerReference.get().onFailure(e);

					}
				}
			}
		}
	}

	class ServiceRequestUploadTask extends AsyncTask<HttpPost, Object, Object> {
		WeakReference<ServiceStatusListener> listenerReference;
		private float totalSize = 0;
		private long previousMillisec, currentMilliSec, startedMilliSec;
		private long previousTransfered, currentTransfered;

		public ServiceRequestUploadTask(ServiceStatusListener listener) {
			super();
			this.listenerReference = new WeakReference<ServiceStatusListener>(
					listener);

			previousMillisec = System.currentTimeMillis();
			startedMilliSec = previousMillisec;
			previousTransfered = 0;
		}

		@Override
		protected Object doInBackground(HttpPost... params) {
			try {
				HttpPost post = params[0];
				HttpClient httpClient = new DefaultHttpClient();

				for (int i = 0; i < MyApplication.currentAlbum.selectedImagesPaths
						.size(); i++) {

					totalSize = totalSize
							+ new File(
									MyApplication.currentAlbum.selectedImagesPaths
											.get(i)).length();

				}
				CustomMultiPartEntity mentity = new CustomMultiPartEntity(
						new ProgressListener() {

							@Override
							public void transferred(long num) {
								currentTransfered = num;
								publishProgress((int) ((num / (float) totalSize) * 100));
							}
						});

				for (int i = 0; i < MyApplication.currentAlbum.selectedImagesPaths
						.size(); i++) {

					mentity.addPart(
							MyApplication.currentAlbum.selectedImageNames
									.get(i),
							new FileBody(
									new File(
											MyApplication.currentAlbum.selectedImagesPaths
													.get(i))));

				}
				post.setEntity(mentity);
				setDefalutHeaders(post);

				HttpResponse httpResponse = httpClient.execute(post);

				// Obtain and Set Headers when login
				headers = httpResponse.getAllHeaders();
				for (int i = 0; i < headers.length; i++) {
					if (headers[i].getName().equalsIgnoreCase("Set-Cookie")) {
						sessionCookie = headers[i].getValue();
						Log.d(TAG, "sessionCookie " + sessionCookie);
						break;
					}
				}
				responseCode = httpResponse.getStatusLine().getStatusCode();
				Log.d(TAG, "Response Code : "
						+ httpResponse.getStatusLine().getStatusCode());
				String responce = EntityUtils
						.toString(httpResponse.getEntity());

				Log.d(TAG, "Response:" + responce);
				return responce;
			} catch (Exception e) {
				return e;
			}
		}

		@Override
		protected void onProgressUpdate(Object... values) {
			currentMilliSec = System.currentTimeMillis();
			long intervel = ((currentMilliSec - previousMillisec) / 1000);
			if (intervel > 1) {
				long completedInterval = ((currentMilliSec - startedMilliSec) / 1000);
				if (Integer.parseInt(values[0].toString()) > 0) {
					long remainingTimeInSec = ((completedInterval * 100)
							/ Integer.parseInt(values[0].toString()) - completedInterval);

					// TODO update upload status
					// if (remainingTimeInSec < 60)
					// UploadImagesActivity.topTextView
					// .setText("Ihre Fotos werden hochgeladen.\n Verbleibende Zeit: "
					// + remainingTimeInSec + " Sekunden");
					// else
					// UploadImagesActivity.topTextView
					// .setText("Ihre Fotos werden hochgeladen.\n"
					// + "Verbleibende Zeit: "
					// + ((int) remainingTimeInSec / 60)
					// + " Minuten");
					previousMillisec = currentMilliSec;
				}

			}
			// TODO set upload progress
			// UploadImagesActivity.progressBar.setProgress(Integer
			// .parseInt(values[0].toString()));
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			if (listenerReference.get() != null) {
				Log.d(TAG, "result: " + result);
				if (result instanceof Exception)
					listenerReference.get().onFailure((Exception) result);
				else {
					try {
						// JSONObject jsonObject = new JSONObject(
						// result.toString());

						if (responseCode == 200) {
							if (result.equals(""))
								listenerReference.get().onSuccess(result);
							else {
								JSONObject jsonObject = new JSONObject(
										result.toString());
								if (jsonObject.has("WSSE")) {
									xwsse = jsonObject.getString("WSSE");
									listenerReference.get()
											.onSuccess("Success");
								} else if (jsonObject.has("success")) {
									if (jsonObject.getString("success").equals(
											"error")
											|| jsonObject.getString("success")
													.equals("false")) {
										throw new Exception(
												jsonObject.toString());
									} else {
										listenerReference.get().onSuccess(
												result);
									}
								} else if (jsonObject.has("id")) {

									listenerReference.get().onSuccess(result);
								} else {
									throw new Exception(jsonObject.toString());
								}
							}
						} else {
							throw new Exception((String) result);
						}

					} catch (Exception e) {
						e.printStackTrace();
						listenerReference.get().onFailure(e);

					}
				}
			}
		}

	}

}
