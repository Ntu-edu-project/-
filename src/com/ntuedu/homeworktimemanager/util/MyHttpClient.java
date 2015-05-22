package com.ntuedu.homeworktimemanager.util;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

@SuppressWarnings("deprecation")
public class MyHttpClient extends DefaultHttpClient {
	/**
	 * ��ȡ HttpClient,��Ҫ�Ƿ�װ�˳�ʱ����
	 * 
	 * @param rTimeOut
	 *            ����ʱ
	 * @param sTimeOut
	 *            �ȴ����ݳ�ʱ
	 * @return
	 */
	public DefaultHttpClient getHttpClient(int rTimeOut, int sTimeOut) {
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, rTimeOut);
		HttpConnectionParams.setSoTimeout(httpParams, sTimeOut);
		DefaultHttpClient client = new DefaultHttpClient(httpParams);
		return client;
	}

}
