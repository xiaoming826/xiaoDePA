package com.ericcode.xiaodepa.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.ericcode.xiaodepa.util.JsonUtil;
import com.ericcode.xiaodepa.util.Logger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhoushengming on 16/1/13.
 */
public class JsonRequest<T> extends Request<T>{


	private static final String HTTP_HEADER_COOKIE = "Cookie";
	private static final String TAG = JsonRequest.class.getSimpleName();

	private final Class<T> clazz;
	private final TypeReference<T> typeReference;
	private final Response.Listener<T> listener;
	private Map<String, String> _cookies = Maps.newHashMap();
	private Map<String, String> _postData = Maps.newHashMap();

	public JsonRequest(String url, Class<T> clazz,
	                   Response.Listener<T> listener, Response.ErrorListener errorListener) {
		this(Method.GET, url, clazz, listener, errorListener);
	}

	public JsonRequest(String url, TypeReference<T> typeReference,
	                   Response.Listener<T> listener, Response.ErrorListener errorListener) {
		this(Method.GET, url, typeReference, listener, errorListener);
	}

	public JsonRequest(int method, String url, Class<T> clazz,
	                   Response.Listener<T> listener, Response.ErrorListener errorListener) {
		super(method, url, errorListener);
		this.clazz = clazz;
		this.typeReference = null;
		this.listener = listener;
	}

	public JsonRequest(int method, String url, TypeReference<T> typeReference,
	                   Response.Listener<T> listener, Response.ErrorListener errorListener) {
		super(method, url, errorListener);
		this.clazz = null;
		this.typeReference = typeReference;
		this.listener = listener;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = new HashMap<>(super.getHeaders());



		if (_cookies == null || _cookies.size() == 0) {
			return headers;
		}

		StringBuilder builder = new StringBuilder();

		if (headers.containsKey(HTTP_HEADER_COOKIE)) {
			builder.append(headers.get(HTTP_HEADER_COOKIE)).append("; ");
		}
		for (Map.Entry<String, String> entry : _cookies.entrySet()) {
			builder.append(entry.getKey()).append('=').append(entry.getValue()).append("; ");
		}
		headers.put(HTTP_HEADER_COOKIE, builder.toString());

		return headers;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return this._postData;
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}

	@Override
	protected String getParamsEncoding() {
		return super.getParamsEncoding();
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(
					response.data,
					HttpHeaderParser.parseCharset(response.headers));
			Logger.d(TAG, "get url:%s , result:%s", getUrl(), json);
			if (this.clazz != null) {
				return Response.success(
						JsonUtil.deserialize(json, clazz),
						HttpHeaderParser.parseCacheHeaders(response));
			}
			return Response.success(
					JsonUtil.deserialize(json, typeReference),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (Exception e) {
			return Response.error(new ParseError(e));
		}
	}

	public void setCookie(Map<String, String> cookies) {
		if (cookies != null) {
			this._cookies.putAll(cookies);
		}
	}

	public void setPostData(Map<String, String> postData) {
		this._postData.putAll(postData);
	}

	public void addParam(String key, String value) {
		_postData.put(key, value);
	}
}
